package com.kingstar.bw.distance;

import com.kingstar.bw.ml.LevenshteinDistance;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 下午5:15
 * @Version: 1.0
 */
public class LevenshteinDistanceTest {

    @Test
    public void test1() {
        BigDecimal big  = LevenshteinDistance.computeLevenshteinDistanceRate("工商银行","华泰商业银行");
        System.out.println(big);
    }

    @Test
    public void computeLevenshteinDistanceMil() {
        List<String> list = new ArrayList<String>(10000);

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/meitao/test/all.csv"));
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
                String name = words[2].replaceAll("\"","");
                name = name.replaceAll("·","");
                list.add(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        for (String str:list){
            LevenshteinDistance.computeLevenshteinDistance("张三",str);
        }

        long end = System.currentTimeMillis();
        System.out.println(" 600万 >>> "+(end-start));
    }

    @Test
    public void computeLevenshteinDistance() {
     List<String> list = new ArrayList<String>(10000);

     try
     {
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
            list.add(name);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
        long start = System.currentTimeMillis();
        for (String str:list){
            LevenshteinDistance.computeLevenshteinDistance("张三",str);
        }

        long end = System.currentTimeMillis();
        System.out.println(" shijian >>> "+(end-start));
    }
    @Test
    public void computeLevenshteinDistance_Optimized_mili() {
        List<String> list = new ArrayList<String>(1000000);

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/meitao/test/all.csv"));
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
                String name = words[2].replaceAll("\"","");
                name = name.replaceAll("·","");
                list.add(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        for (String str:list){
            LevenshteinDistance.computeLevenshteinDistance_Optimized("张三",str);
        }

        long end = System.currentTimeMillis();
        System.out.println("精简600万 >>> "+(end-start));
    }
    @Test
    public void computeLevenshteinDistance_Optimized() {


        Map<String,String> param = new HashMap<String,String>();

        try
        {
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
                param.put(words[0],name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();

        Set<Map.Entry<String,String>> set = param.entrySet();
        for (Map.Entry<String,String> entry:set){
            LevenshteinDistance.computeLevenshteinDistance_Optimized("张三",entry.getValue());
        }

        long end = System.currentTimeMillis();
        System.out.println("精简10万 >>> "+(end-start));
    }
}