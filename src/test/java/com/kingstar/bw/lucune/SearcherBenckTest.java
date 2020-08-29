package com.kingstar.bw.lucune;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 上午9:07
 * @Version: 1.0
 */
@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.SECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread)
public class SearcherBenckTest {


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(SearcherBenckTest.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .forks(2)
                .build();

        new Runner(opt).run();


    }
    Searcher searcher;

    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init() {
        String indexDir = "/home/meitao/test/lucene";

        //查询这个字符串
        try {
            searcher = new Searcher(indexDir);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭方法
//    @TearDown
//    public void finish(){
//        context.close();
//    }
    @Benchmark
    public void testSearch() {
        //查询这个字符串
        String q = "Purushottam";
        try {
            searcher.search(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}