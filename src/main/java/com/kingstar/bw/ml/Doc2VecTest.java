package com.kingstar.bw.ml;


import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareFileSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class Doc2VecTest {
    private static Logger log = LoggerFactory.getLogger(Doc2VecTest.class);
    //文档向量输出路径
    private static String outputPath = "/home/meitao/test/addr.txt";
    private static String inputPath = "/home/meitao/test/addr.csv";

    public static void main(String[] args) throws Exception {
        //输入文本文件的目录
        File inputTxt = new File(inputPath);
        log.info("开始加载数据...." + inputTxt.getName());
        //加载数据
        LabelAwareFileSentenceIterator iter = new LabelAwareFileSentenceIterator(inputTxt);
        //切词操作
        TokenizerFactory token = new DefaultTokenizerFactory();
        //去除特殊符号及大小写转换操作
        token.setTokenPreProcessor(new CommonPreprocessor());
        AbstractCache<VocabWord> cache = new AbstractCache<>();
        //添加文档标签，这个一般从文件读取，为了方面我这里使用了数字
        //设置文档标签
        log.info("训练模型....");
        ParagraphVectors vec = new ParagraphVectors.Builder()
                .minWordFrequency(1)
                .iterations(5)
                .epochs(1)
                .layerSize(50)
                .learningRate(0.025)
                .windowSize(5)
                .iterate(iter)
                .trainWordVectors(false)
                .vocabCache(cache)
                .tokenizerFactory(token)
                .sampling(0)
                .build();

        vec.fit();


        System.out.println("获取文档的相似度以及向量");

        INDArray arr1 = vec.inferVector("Dongqu Boyuan asdasd");
        INDArray arr2 = vec.inferVector("asdasd Boyuan ");
        System.out.println("文档1和文档2的相似度是：" + Transforms.cosineSim(arr1, arr2));


    }
//    public static void writeDocVectors(ParagraphVectors vectors, String outpath) throws IOException {
//        //写操作
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outpath)) ));
//        //读操作
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputPath)) ));
//        String line = null;
//        int i = 1;
//        Map<String, String> keyToDoc = new HashMap<>();
//        while ((line = bufferedReader.readLine())!=null) {
//            keyToDoc.put("doc" + i, line);
//            i++;
//        }
//        VocabCache<VocabWord> vocabCache = vectors.getVocab();
//        for (VocabWord word : vocabCache.vocabWords()) {
//            StringBuilder builder = new StringBuilder();
//            //获取每个文档对应的标签
//            INDArray vector = vectors.getWordVectorMatrix(word.getLabel());
//            //向量添加
//            for (int j = 0; j < vector.length(); j++) {
//                builder.append(vector.getDouble(j));
//                if (j < vector.length() - 1) {
//                    builder.append(" ");
//                }
//            }
//            //写入指定文件
//            bufferedWriter.write(keyToDoc.get(word.getLabel()) + "\t" + builder.append("\n").toString());
//        }
//        bufferedWriter.close();
//        bufferedReader.close();
//    }
}
