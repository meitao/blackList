package com.kingstar.bw.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午1:21
 * @Version: 1.0
 */
@Service
public class DefaultInitDataListenerImpl implements InitDataListener {
    @Autowired
    private List<InitDataEvent> list = new ArrayList<>();

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void init() {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        //多线程处理匹配
        ExecutorService executorService =
                new ThreadPoolExecutor(6, 6,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(10));
        //触发事件
        for (InitDataEvent event : list) {
            executorService.execute(() -> {
                try{
                    event.onFire();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
            //关掉线程池
            executorService.shutdown();
        } catch (InterruptedException e) {
            logger.error("初始化问题!",e);
        }
        logger.info("数据初始化成功,耗时:" + (System.currentTimeMillis() - start));
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
