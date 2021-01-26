import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test extends TestCase {
    private List<String> loadData() {
        List<String> result = new ArrayList<>();
        String path = this.getClass().getResource("/book.txt").getPath();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            int lineCnt = 0;
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                if (line.length() > 0) {
                    result.add(line.trim());
                    lineCnt++;
                }
            }
            System.out.printf("Read %d lines..\n", lineCnt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private BPE getBPE(int lineLimit, int vocabSize) {
        System.out.println("Reading test data..");
        List<String> data = loadData().subList(0, lineLimit);

        return new BPE(data, vocabSize, "<w>", "</w>");
    }

    public void testLearning() {
        BPE bpe = getBPE(1000, 100);
        System.out.println("Vocabulary: " + bpe.getVocab());
    }

    public void testEncoding() {
        BPE bpe = getBPE(1000, 100);
        String[] tokens = bpe.tokenize("hello world");
        System.out.println("hello, world!");
        System.out.println(Arrays.toString(tokens));
    }

    public void testSerialization() {
        BPE bpe = getBPE(1000, 100);
        File root = new File(System.getProperty("user.home"), "/bpe4j");
        root.mkdirs();
        File file = new File(root, "bpe.txt");
        bpe.serializeToFile(file);
    }

    public void testDeserialization() {
        testSerialization();
        File root = new File(System.getProperty("user.home"), "/bpe4j");
        File file = new File(root, "bpe.txt");
        BPE bpe = BPE.deserializeFromFile(file);
        assert bpe != null;
        System.out.println(bpe.getVocab());
    }
}
