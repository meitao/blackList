package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: meitao
 * @Description: 匹配逻辑的的流程总控
 * @Date: 20-8-27 下午4:12
 * @Version: 1.0
 */
public interface MatchManager {
    /**
     * 匹配黑名单库返回匹配结果
     * @param chainContext
     */
    public List<ChainContext> match(ChainContext chainContext) ;

}
