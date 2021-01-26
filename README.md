# BPE tokenization for Java

Java version: 1.8+

Some points:
1. Basic implementation (no dropout or other enhancements)
2. Based on Stream API ([Collection.parallelStream](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--))
3. Number of threads is controlled by JVM
4. Implemented features: 
   - Learning
   - Tokenization
   - Serialization/deserialization

Example (src/test/java/Test.java)
```
List<String> data = loadData().subList(0, lineLimit);
BPE bpe = new BPE(data, vocabSize, "<w>", "</w>");
String[] tokens = bpe.tokenize("hello world");

System.out.println(Arrays.toString(tokens));
```
Output:
```
[<w>, he, ll, o, </w>, <w>, w, or, l, d, </w>]
```

Source:<br>
[Neural Machine Translation of Rare Words with Subword Units](https://arxiv.org/pdf/1508.07909.pdf)