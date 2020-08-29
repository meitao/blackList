package com.kingstar.bw.lucune;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 上午9:07
 * @Version: 1.0
 */
public class SearcherTest {
    Searcher searcher;
    @Before
    public void init() {
        String indexDir = "/home/meitao/test/lucene";

        //查询这个字符串
        try {
            searcher = new Searcher(indexDir);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search() {
        //查询这个字符串
        String q = "Purushottam";
        try {
            searcher.search(q);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                searcher.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void mutSearch() {
        //查询这个字符串
        String[] q = {"Purushottam","Ghimire"};
        try {
            searcher.multiSearch(q);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                searcher.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}