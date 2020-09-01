package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.common.MathUtil;
import com.kingstar.bw.exception.PlatException;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 匹配执行连的父类
 * @Date: 20-8-28 上午9:17
 * @Version: 1.0
 */
public abstract class MatchCommand implements Command {


    /**
     * 系统参数
     */
    private  Params params = new Params();


    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    /**
     * 将对象转换
     * @param context
     * @return
     */
    public ChainContext convert(Context context){

        if(!(context instanceof ChainContext)){
            throw  new PlatException("不支持非ChainContext对象!");
        }

        if(context==null){
            throw  new PlatException("context对象不能为空!");
        }

        ChainContext context1 =  (ChainContext)context ;

        if (context1.getSearch()==null){
            throw  new PlatException("search对象不能为空!");
        }
        return context1;
    }

    /**
     * 获取黑名单中的目标对象
     * @param id
     * @return
     */
    public Search getTarget(String id){
        //获取内存数据库中的所有的数据
        Map<String, Search> allParm = (Map<String, Search>) LocalData.getCollection(Constant.KEY_ALL);
        //黑名单中的目标对象
        Search tarSearch = allParm.get(id);

        if(tarSearch==null){
            throw new PlatException("黑名单中没有"+id+"对应的数据!");
        }

        return tarSearch;
    }

    /**
     * 是否中断执行链
     * @param chainContext
     * @param matchRate
     * @return
     */
    public boolean isEnd(ChainContext chainContext , BigDecimal matchRate){
        //将计算的匹配度和权重值存入结果明细
        Map<String, Params>  paramsMap = chainContext.getParamList();
        Params params = new Params();
        params.setWeight(this.getParams().getWeight());
        params.setRate(matchRate.multiply(this.getParams().getWeight()));

        paramsMap.put(this.getClass().getSimpleName(),params);
        chainContext.setParamList(paramsMap);
        //计算加权值
        MathUtil.rateSum(chainContext);
        if (chainContext.getSumRate().compareTo(this.getParams().getRate())<0){
            //中断执行链操作返回结果
            return true;
        }
        return false;
    }

}
