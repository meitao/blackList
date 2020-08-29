package com.kingstar.bw.tries;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EnTrieTreeTest {
    TrieTree trieTree = new TrieTree();

    @Before
    public void insert() {

        trieTree.setSpliter(new CnSpliter());

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/meitao/test/py.csv"));
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


        Assert.assertNotNull(trieTree);

    }

    @Test
    public void search() {
        boolean search = trieTree.search("qiaozonghuai");
        Assert.assertTrue(search);
    }

    @Test
    public void startsWith() {
        boolean starts =  trieTree.startsWith("qiaozong");
        Assert.assertTrue(starts);
    }
}