package com.kingstar.bw.hash;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 上午8:32
 * @Version: 1.0
 */
public class Memtest {

    @Test
    public void testMem(){

        BigDecimal a = new BigDecimal("0");
        BigDecimal b= new BigDecimal("5");
        a.add(b);
        System.out.println(a);
        System.out.println(a.add(b));

        List<List> list = new ArrayList<List>(1000000);
        for (int i=0;i<1000000;i++){
            list.add(new ArrayList(5));
        }

        List<LinkedList> list1 = new ArrayList<LinkedList>(1000000);
        for (int i=0;i<1000000;i++){
            list1.add(new LinkedList());
        }


        List<HashMap> list2 = new ArrayList<HashMap>(1000000);
        for (int i=0;i<1000000;i++){
            list2.add(new HashMap());
        }

        System.out.println("asd");
    }
}
