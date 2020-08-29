package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NationMatch extends AbstractMatcher{

    /**
     * 匹配国家,返回大于等于精准度的
     * @param search
     * @return
     */
    @Override
    public List<String> match(Search search) {
        //获取内存数据库中的名称信息
        Map<String, Search>  param = LocalData.getCollection(Constant.KEY_ALL);
        //结果,保存数据的id
        List<String> result = new ArrayList<String>();
        BigDecimal rate = new BigDecimal(0);
        //当为空,rate为0
        if(StringUtils.isEmpty(search.getNation())){
           return null;
        }

        Search search1 = param.get(search.getId());
        //自然人
        if(search.isPer()){
            //当输入项不为空,黑名单为空为50%
            if(StringUtils.isEmpty(search1.getNation())){
                rate=BigDecimal.valueOf(0.5);
            }

        }else{
            //机构
            //当输入项不等于黑名单0%
            if(search.getNation().equals(search1.getNation())){
                rate=BigDecimal.valueOf(0);
            }

        }

        //输入项等于黑名单数据100%
        if(search.getNation().equals(search1.getNation())){
            rate=BigDecimal.valueOf(1);
        }
        //当输入项不等于黑名单-1000%
        if(search.getNation().equals(search1.getNation())){
            rate=BigDecimal.valueOf(-10);
        }

        return result;
    }
}
