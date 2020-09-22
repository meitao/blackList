package com.kingstar.bw.facade.impl;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.util.ChineseUtil;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.facade.MatchManagerFacade;
import com.kingstar.bw.filter.MutilNumberMatchManager;
import com.kingstar.bw.filter.NameMatchManager;
import com.luhuiguo.chinese.ChineseUtils;
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
    NameMatchManager nameMatchManager;
    @Autowired
    MutilNumberMatchManager numberMatchManager;


    @Override
    public List<ChainContext> match(ChainContext chainContext) {

        Search search = chainContext.getSearch();
        //判断名称和证件号码是否有一个存在,有则继续匹配,没有则报错
        if (StringUtils.isEmpty(search.getNumber()) && StringUtils.isEmpty(search.getName())) {
            throw new PlatException("名称和证件号码都为空!");
        }
        if (search.getPercision() == 0) {
            //设置默认精确度为精准
            search.setPercision(0.9);
//            throw new PlatException("精确度不能为空!");
        }
        List<ChainContext> list;
        if (!StringUtils.isEmpty(search.getName())) {
            //中文
            if (ChineseUtil.isChinese(search.getName())) {
                //简体字的校验
                String name = ChineseUtils.toSimplified(search.getName());
                search.setName(name);
                list = nameMatchManager.match(chainContext);
                if (!list.isEmpty()){
                    return list;
                }
                //繁体字的校验
                 name = ChineseUtils.toTraditional(search.getName());
                search.setName(name);
                list = nameMatchManager.match(chainContext);
            }else{
                //非中文
                list = nameMatchManager.match(chainContext);
            }

        } else {
            list = numberMatchManager.match(chainContext);
        }
        return list;
    }
}
