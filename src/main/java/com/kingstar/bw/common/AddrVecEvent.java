package com.kingstar.bw.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareFileSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class AddrVecEvent implements InitDataEvent {

    public static final String FILE_DIR = "/home/tmp/";

    public static final String FILE_NAME = "test.csv";

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static ParagraphVectors vec;

    @Override
    public void onFire() {
        BufferedWriter bufferedWriter = null;
        File fileDir = null;
        try {
            fileDir = new File(FILE_DIR);
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(FILE_DIR +FILE_NAME));

            BufferedWriter finalBufferedWriter = bufferedWriter;
            jdbcTemplate.query("SELECT   BIRTHPLACE FROM  AMLCONFIG.T_EXPOSED_PEOPLE_NEW c WHERE c.BIRTHPLACE IS NOT null  group by BIRTHPLACE  ", new RowMapper<String>() {

                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {

                        finalBufferedWriter.write(rs.getString("BIRTHPLACE") + "\n");
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                    return null;
                }
            });
        } catch (IOException e) {
            logger.error("", e);
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        //输入文本文件的目录
        File inputTxt = new File(FILE_DIR);
        logger.info("开始加载数据...." + inputTxt.getName());
        //加载数据
        LabelAwareFileSentenceIterator iter = new LabelAwareFileSentenceIterator(inputTxt);
        //切词操作
        TokenizerFactory token = new DefaultTokenizerFactory();
        //去除特殊符号及大小写转换操作
        token.setTokenPreProcessor(new CommonPreprocessor());
        AbstractCache<VocabWord> cache = new AbstractCache<>();
        //添加文档标签，这个一般从文件读取，为了方面我这里使用了数字
        //设置文档标签
        logger.info("训练模型....");
        vec = new ParagraphVectors.Builder()
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
        //删除临时文件
        if(fileDir!=null){
            File[] files = fileDir.listFiles();
            for (File file: files){
                file.delete();
            }
        }
    }
}
