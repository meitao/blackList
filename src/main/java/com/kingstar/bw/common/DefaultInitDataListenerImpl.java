package com.kingstar.bw.common;

import java.util.ArrayList;
import java.util.List;

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
        //触发事件
        for(InitDataEvent event:list){
            event.onFire();
        }
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
