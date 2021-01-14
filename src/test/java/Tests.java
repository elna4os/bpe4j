package test.java;

import junit.framework.TestCase;
import main.java.com.elna4os.BPE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tests extends TestCase {
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

    public void testLearning() {
        System.out.println("Reading test data..");
        List<String> data = loadData();
        BPE bpe = new BPE(data, 100);
    }

    public void testEncoding() {

    }

    public void testDecoding() {

    }
}
