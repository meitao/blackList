package com.kingstar.bw.tries;

/**
 * @ProjectName: demo
 * @Package: com.bw.tries
 * @ClassName: Spliter
 * @Author: meitao
 * @Description: 分割字符器接口
 * @Date: 20-8-21 下午5:03
 * @Version: 1.0
 */
public interface Spliter {

    /**
     * 对传入的字符串进行分词
     * @param word
     * @return
     */
    public String[] split(String word);

}
