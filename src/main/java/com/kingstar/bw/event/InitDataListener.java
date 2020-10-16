package com.kingstar.bw.event;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 上午11:21
 * @Version: 1.0
 */
public interface InitDataListener {
    /**
     * 初始化数据
     */
    public void init();

    /**
     * 添加
     * @param event
     */
    public void add(InitDataEvent event);

    /**
     * 删除
     * @param event
     */
    public void remove(InitDataEvent event);

}
