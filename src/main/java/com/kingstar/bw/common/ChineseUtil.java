package com.kingstar.bw.common;

import com.kingstar.bw.exception.PlatException;

/**
 * 中文字符处理
 */
public class ChineseUtil {
    /**
     * 判断该字符串是否为中文
     * @param string
     * @return
     */
    public static boolean isChinese(String string){
        if(string==null){
            throw new PlatException("字符串不能为空!");
        }
        int n = 0;
        for(int i = 0; i < string.length(); i++) {
            n = (int)string.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }
}
