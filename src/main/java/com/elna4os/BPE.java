package main.java.com.elna4os;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Byte Pair Encoding implementation that uses parallelStream
 */
public class BPE implements Encoder, Decoder, Serializable {
    private final String BEGINNING_OF_WORD = "<bow>";
    private final int maxVocabSize;
    private final HashSet<String> vocab = new HashSet<>();
    private final List<List<String>> internalData = Collections.synchronizedList(new ArrayList<>());

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

    private void fillInitialVocab(List<String> data) {
        vocab.addAll(
                data.parallelStream().flatMap(text -> Arrays.stream(
                        text.split(""))
                ).collect(Collectors.toCollection(HashSet::new))
        );
    }

    private void fillInternalData(List<String> data) {
        data.parallelStream().forEach(text -> {
            List<String> textSplitted = Arrays.asList(text.split(""));
            textSplitted.replaceAll(x -> x.replaceAll("\\s+", BEGINNING_OF_WORD));
            internalData.add(textSplitted);
        });
    }

    private void learn() {
        while (vocab.size() < maxVocabSize || !internalData.isEmpty()) {
            internalData.parallelStream().map(parts -> {
                return null;
            }).reduce((x, y) -> {
                return null;
            });
        }
    }

    private void join(String first, String second) {

    }

    /**
     * @param text String to encode
     * @return Tokenized string
     */
    @Override
    public String[] encode(String text) {
        return null;
    }

    /**
     * @param tokens Tokenized string
     * @return Decoded string
     */
    @Override
    public String decode(String[] tokens) {
        return null;
    }

    /**
     * @param file
     */
    public void serializeToFile(File file) {
    }

    /**
     * @param file
     * @return
     */
    public static BPE deserializeFromFile(File file) {
        return null;
    }
}
