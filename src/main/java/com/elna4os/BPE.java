package main.java.com.elna4os;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Byte Pair Encoding implementation that uses parallelStream
 */
public class BPE implements Encoder, Decoder {
    private final int maxVocabSize;
    private final HashSet<String> vocab = new HashSet<>();

    /**
     * @param data         Texts
     * @param maxVocabSize Vocabulary max size
     */
    public BPE(List<String> data, int maxVocabSize) {
        this.maxVocabSize = maxVocabSize;

        System.out.println("Filling initial vocabulary..");
        fillInitialVocab(data);

        System.out.println();
    }

    private void fillInitialVocab(List<String> data) {
        vocab.addAll(
                data.parallelStream().flatMap(text -> Arrays.stream(
                        text.split(""))
                ).collect(Collectors.toCollection(HashSet::new))
        );
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
}
