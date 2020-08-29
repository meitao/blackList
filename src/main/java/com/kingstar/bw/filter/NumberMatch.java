package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NumberMatch extends AbstractMatcher{

    /**
     * 匹配名称的字符串,返回大于等于精准度的
     * @param search
     * @return
     */
    @Override
    public List<String> match(Search search) {
        //获取内存数据库中的名称信息
        Map<String,String> param = LocalData.getCollection(Constant.KEY_NUMBER);
        //结果,保存数据的id
        List<String> result = new ArrayList<String>();

        //姓名最短编辑距离匹配
        Set<Map.Entry<String,String>> set = param.entrySet();
        for (Map.Entry<String,String> entry:set){
//           if (LevenshteinDistance.computeLevenshteinDistanceRate(search.getNumber(),entry.getValue()).compareTo(this.getRate())>-1){
//               result.add(entry.getKey());
//           }

        }

        return result;
    }
}
