# BPE (byte pair encoding) tokenization for Java

A toy project. Use this code on your own risk :)

Some points:
1. Basic implementation (no dropout or other enhancements)
2. Based on Stream API ([Collection.parallelStream](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--) operation)
3. Number of threads is controlled by JVM
4. Parallel processing is used only for learning
5. Encoding/decoding are not implemented

To do:
1. Add ability to set number of threads
2. Add encoding/decoding
3. Add serialization/deserialization
4. Add tests

Source:<br>
[Neural Machine Translation of Rare Words with Subword Units](https://arxiv.org/pdf/1508.07909.pdf)