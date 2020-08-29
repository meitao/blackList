package com.kingstar.bw.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileLoadTest {


    @Test
    public void load() {
        Map<String, Map> param = new HashMap<String, Map>();
        param.put("id",new HashMap());

        Map value = param.get("id");
        value = param.get("id1");
        FileLoad fileLoad = new FileLoad();
        fileLoad.load("/home/meitao/test/xm.csv");

//        FileLoad fileLoad1 = new FileLoad();
//        fileLoad1.load("/home/meitao/test/xm.csv");
//
//        FileLoad fileLoad2 = new FileLoad();
//        fileLoad2.load("/home/meitao/test/xm.csv");


        long start = System.nanoTime();
        fileLoad.find("張弦");
        long end = System.nanoTime();
        System.out.println(end-start);


    }
}