package com.kingstar.bw.exception;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午1:33
 * @Version: 1.0
 */
public class PlatException extends RuntimeException{
    public PlatException() {
        super();
    }
    public PlatException(String s) {
        super(s);
    }

    public PlatException(Exception e) {
        super(e);
    }

}
