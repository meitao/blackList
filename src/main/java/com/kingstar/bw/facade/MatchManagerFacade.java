package com.kingstar.bw.facade;

import com.kingstar.bw.bean.ChainContext;

import java.util.List;

/**
 * @Author: meitao
 * @Description:  门面类接口
 * @Date: 20-8-29 下午12:54
 * @Version: 1.0
 */
public interface MatchManagerFacade {
    /**
     *
     * @param chainContext
     * @return
     */
    public List<ChainContext> match(ChainContext chainContext)  ;
}
