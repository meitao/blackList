package com.kingstar.bw.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午1:21
 * @Version: 1.0
 */
public class DefaultInitDataListenerImpl implements InitDataListener {

    List<InitDataEvent> list = new ArrayList<>();

    @Override
    public void init() {
        //多线程处理匹配
        ExecutorService executorService =
                new ThreadPoolExecutor(4, 4,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(1000000));
        //触发事件
        for(InitDataEvent event:list){
            executorService.execute(()->{
                event.onFire();
            });
        }
        //关掉线程池
        executorService.shutdown();
    }

    @Override
    public void add(InitDataEvent event) {
        list.add(event);
    }

    @Override
    public void remove(InitDataEvent event) {
        list.remove(event);
    }
}
