package com.kingstar.bw.hash;

import it.unimi.dsi.fastutil.Hash;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 上午8:32
 * @Version: 1.0
 */
public class Memtest {

    @Test
    public void testMem(){

        //多线程处理匹配
        ExecutorService executorService =
                new ThreadPoolExecutor(4, 4,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(100000));
        Map hash = new HashMap();

        for (int i=0;i<100000;i++){
            hash.put(i,i);
        }
        Set set =hash.entrySet();
        CountDownLatch countDownLatch = new CountDownLatch(set.size());
//        AtomicInteger atomicInteger = new AtomicInteger();
        for (Object obj:set){
            executorService.execute(()->{
                try {
                    System.out.println(""+obj);
                }finally {
                    countDownLatch.countDown();
                }

            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ok");

//        HashMap hash = new HashMap();
//        hash.put(1,20);
//        List list3 = new ArrayList(10);
//        list3.set(9,"10");
//        Object obj =list3.get(0);
//
//        BigDecimal a = new BigDecimal("0");
//        BigDecimal b= new BigDecimal("5");
//        a.add(b);
//        System.out.println(a);
//        System.out.println(a.add(b));
//
//        List<List> list = new ArrayList<List>(1000000);
//        for (int i=0;i<1000000;i++){
//            list.add(new ArrayList(5));
//        }
//
//        List<LinkedList> list1 = new ArrayList<LinkedList>(1000000);
//        for (int i=0;i<1000000;i++){
//            list1.add(new LinkedList());
//        }
//
//
//        List<HashMap> list2 = new ArrayList<HashMap>(1000000);
//        for (int i=0;i<1000000;i++){
//            list2.add(new HashMap());
//        }
//
//        System.out.println("asd");
    }
}
