package com.kingstar.bw.tries;

/**
 * @ProjectName: demo
 * @Author: meitao
 * @Description: 英文文分词器
 * @Date: 20-8-21 下午5:06
 * @Version: 1.0
 */
public class EnSpliter implements Spliter{

    /**
     * 英文分词,将英文按照空格分割
     * @param word
     * @return
     */
    @Override
    public String[] split(String word) {
        if (word==null){
            throw new RuntimeException("word 不能为空!");
        }
        String[] result = word.split(" ");
        return  result;
    }
}
