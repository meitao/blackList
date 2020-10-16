package com.kingstar.bw.util;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ChineseUtilTest {

    @Test
    public void toPingyin() {

        List<String>  list  =ChineseUtil.toPingyin("龟薄");
        for( String  word:list){
            System.out.println(word);
        }

    }
}