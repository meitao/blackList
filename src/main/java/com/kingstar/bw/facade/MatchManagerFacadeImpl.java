package com.kingstar.bw.facade;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.filter.MutilNumberMatchManager;
import com.kingstar.bw.filter.NameMatchManager;
import com.kingstar.bw.filter.NumberMatchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-29 下午12:55
 * @Version: 1.0
 */
@Service
public class MatchManagerFacadeImpl implements MatchManagerFacade {

    @Autowired
    NameMatchManager nameMatchManager ;
    @Autowired
    MutilNumberMatchManager numberMatchManager ;


    @Override
    public List<ChainContext> match(ChainContext chainContext) {

        Search search = chainContext.getSearch();
        //判断名称和证件号码是否有一个存在,有则继续匹配,没有则报错
        if (StringUtils.isEmpty(search.getNumber()) && StringUtils.isEmpty(search.getName())) {
            throw new PlatException("名称和证件号码都为空!");
        }
        List<ChainContext> list ;
        if (!StringUtils.isEmpty(search.getNumber())){
            list =   numberMatchManager.match(chainContext);

        }else{
            list = nameMatchManager.match(chainContext);
        }
        return list;
    }
}
