import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Byte Pair Encoding implementation that uses parallelStream
 */
public class BPE implements Serializable {
    private final int maxVocabSize;
    private final HashSet<String> vocab = new HashSet<>();
    private List<String> sortedVocab;
    private List<List<String>> internalData = Collections.synchronizedList(new ArrayList<>());

    /**
     * @param data         Texts
     * @param maxVocabSize Vocabulary max size
     */
    public BPE(List<String> data, int maxVocabSize) {
        this.maxVocabSize = maxVocabSize;

        System.out.println("Filling initial vocabulary..");
        fillInitialVocab(data);
        System.out.println("Preparing data..");
        fillInternalData(data);
        System.out.println("Learning..");
        learn();
    }

    public List<String> getVocab() {
        return sortedVocab;
    }

    /**
     * @param text String to encode
     * @return Tokenized string
     */
    public String[] tokenize(String text) {
        // TODO Implement operation
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @param tokens Tokenized string
     * @return Decoded string
     */
    public String detokenize(String[] tokens, String joiner) {
        return String.join(joiner, tokens);
    }

    /**
     * @param file File that will contain a serialized tokenizer
     */
    public void serializeToFile(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file File that contains a serialized tokenizer
     * @return BPE instance if success, else null
     */
    public static BPE deserializeFromFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file.getPath());
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (BPE) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void fillInitialVocab(List<String> data) {
        vocab.addAll(
                data.parallelStream().flatMap(text -> Arrays.stream(text.split(""))).collect(Collectors.toCollection(HashSet::new))
        );
        vocab.remove("\\s");
    }

    private void fillInternalData(List<String> data) {
        data.parallelStream().forEach(
                text -> Arrays.asList(text.split("\\s")).forEach(part -> internalData.add(Arrays.asList(part.split(""))))
        );
    }

    private void learn() {
        int round = 0;
        clear();
        while (vocab.size() < maxVocabSize && !internalData.isEmpty()) {
            Optional<HashMap<Pair<String, String>, Integer>> roundStats = internalData.parallelStream().map(parts -> {
                HashMap<Pair<String, String>, Integer> result = new HashMap<>();
                if (parts.size() == 1)
                    return result;
                for (int i = 0; i < parts.size() - 1; i++) {
                    String a = parts.get(i);
                    String b = parts.get(i + 1);
                    Pair<String, String> pair = new Pair<>(a, b);
                    result.putIfAbsent(pair, 1);
                    result.computeIfPresent(pair, (k, v) -> v + 1);
                }

                return result;
            }).reduce((x, y) -> {
                x.forEach((k, v) -> y.merge(k, v, Integer::sum));
                return y;
            });

            if (!roundStats.isPresent())
                break;
            if (roundStats.get().isEmpty())
                break;
            Pair<String, String> winner = Collections.max(roundStats.get().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            vocab.add(winner.getKey() + winner.getValue());

            merge(winner);
            clear();
            System.out.printf("round=%d, vocab_size=%d, internal_data_size=%d\n", round++, vocab.size(), internalData.size());
        }

        sortedVocab = new ArrayList<>(vocab);
        sortedVocab.sort(Comparator.comparing(String::length).reversed().thenComparing(String::compareTo));
    }

    private void merge(Pair<String, String> pair) {
        internalData = internalData.parallelStream().map(parts -> {
            Stack<String> stack = new Stack<>();
            parts.forEach(x -> {
                if (!stack.empty()) {
                    String top = stack.peek();
                    if (top.equals(pair.getKey()) && x.equals(pair.getValue())) {
                        stack.pop();
                        stack.add(pair.getKey() + pair.getValue());
                    } else
                        stack.add(x);
                } else
                    stack.add(x);
            });

            return new ArrayList<>(stack);
        }).collect(Collectors.toList());
    }

    private void clear() {
        internalData = internalData.parallelStream().filter(x -> x.size() > 1).collect(Collectors.toList());
    }
}
