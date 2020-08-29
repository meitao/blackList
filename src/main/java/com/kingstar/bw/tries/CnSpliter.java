package com.kingstar.bw.tries;

/**
 * @ProjectName: demo
 * @Package: com.bw.tries
 * @ClassName: CnSpliter
 * @Author: meitao
 * @Description: 中文分词器
 * @Date: 20-8-21 下午5:06
 * @Version: 1.0
 */
public class CnSpliter implements Spliter{

    /**
     * 中文分词,将中文按照字符数组转换成字节数组
     * @param word
     * @return
     */
    @Override
    public String[] split(String word) {
        if (word==null){
            throw new RuntimeException("word 不能为空!");
        }
        char[]  words = word.toCharArray();
        String[] result = new String[words.length];
        for(int i=0;i<words.length;i++){
            result[i] = String.valueOf(words[i]);
        }
        return  result;
    }
}
