# BPE tokenization for Java

Java version: 1.8+

Some points:

1. Basic implementation (no dropout or other enhancements)
2. Based on Stream
   API ([Collection.parallelStream](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--))
3. Number of threads is controlled by JVM
4. Learned vocabulary is case-sensitive
5. Implemented features:
    - Dummy learning (controlled by max vocabulary size and number of iterations)
    - Single sentence encoding (subwords that belong to single word are separated by predefined tokens)
    - File based serialization/deserialization

Look at [Test.java](src/test/java/Test.java) for example

Source:<br>
[Neural Machine Translation of Rare Words with Subword Units](https://arxiv.org/pdf/1508.07909.pdf)