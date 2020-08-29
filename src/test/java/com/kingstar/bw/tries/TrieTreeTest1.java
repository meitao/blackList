package com.kingstar.bw.tries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TrieTreeTest1 {
    TrieTree trieTree = new TrieTree();

    public static void main(String[] args) {
        TrieTreeTest1 trieTreeTest1 = new TrieTreeTest1();
        trieTreeTest1.insert();

        try {
            while(true)
             Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("asd");
    }
    public void insert() {
//        trieTree.add("我是税");
//        trieTree.add("我是你");
//        trieTree.add("我们的");
//        trieTree.add("我是");
//        trieTree.add("我是你哥");


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/meitao/test/xm.csv"));
            String line ;
            int i = 0;
            while((line =bufferedReader.readLine())!=null) {
                //跳过第一行
                i++;
                if (i == 1) {
                    continue;
                }
                //将数据以逗号分割
                String[]  words =  line.split(",");
                String name = words[1].replaceAll("\"","");
                name = name.replaceAll("·","");
                trieTree.add(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}