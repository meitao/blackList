package com.kingstar.bw.tries;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.SECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread)
public class TrieTreeBeckManTest {

    TrieTree trieTree = new TrieTree();

    public static void main( String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(TrieTreeBeckManTest.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .forks(2)
                .build();

        new Runner(opt).run();


    }
    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init(){
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
    //关闭方法
//    @TearDown
//    public void finish(){
//        context.close();
//    }
    @Benchmark
    public void testSearch()
    {
        boolean search = trieTree.search("買合木提·吾斯曼");
    }

}