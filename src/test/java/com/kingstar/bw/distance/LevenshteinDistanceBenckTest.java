package com.kingstar.bw.distance;

import com.kingstar.bw.ml.LevenshteinDistance;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class LevenshteinDistanceBenckTest {


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(LevenshteinDistanceBenckTest.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .forks(2)
                .build();

        new Runner(opt).run();


    }
    List<String> list = new ArrayList<String>(10000);

    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init() {

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
    }

    //关闭方法
//    @TearDown
//    public void finish(){
//        context.close();
//    }
    @Benchmark
    public void testSearch() {
        for (String str:list){
            LevenshteinDistance.computeLevenshteinDistance("张三",str);
        }

    }

    @Benchmark
    public void testMutilSearch() {
        list.stream().parallel().forEach(str -> {
            LevenshteinDistance.computeLevenshteinDistance("张三",str);});
//        for (String str:list){
//            LevenshteinDistance.computeLevenshteinDistance("张三",str);
//        }

    }
}