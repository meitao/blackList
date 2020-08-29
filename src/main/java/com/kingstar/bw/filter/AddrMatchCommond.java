package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.AddrVecEvent;
import com.kingstar.bw.exception.PlatException;
import org.apache.commons.chain.Context;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: 地址匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class AddrMatchCommond extends MatchCommand {


    /**
     * 地址匹配
     *
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        Search tarSearch = this.getTarget(search.getId());

        BigDecimal rate = new BigDecimal(0);
        //当为空,rate为0
        if (!StringUtils.isEmpty(search.getAddr())) {
            //当黑名单地址为空 50%
            if (StringUtils.isEmpty(tarSearch.getAddr())) {
                rate = BigDecimal.valueOf(0.5);
            } else {
                //输入地址和黑名单地址都不为空,做人工智能匹配
                ParagraphVectors vectors = AddrVecEvent.vec;
                try {
                    INDArray arr1 = vectors.inferVector(search.getAddr());
                    INDArray arr2 = vectors.inferVector(search.getAddr());
                    //人工智能匹配地址
                    rate = BigDecimal.valueOf(Transforms.cosineSim(arr1, arr2));
                } catch (Exception e) {
                    throw new PlatException(e);
                }

            }
        }
        //将匹配的地址返回
        search.setAddr(tarSearch.getAddr());

        return this.isEnd(chainContext, rate);
    }
}
