package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.AddrVecEvent;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.exception.PlatException;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: meitao
 * @Description: 地址匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class AddrMatchCommond extends MatchCommand {

    protected final Log logger = LogFactory.getLog(getClass());
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
//        Search tarSearch = this.getTarget(search.getId());
        List<String> values = this.getValue(search.getId(),Constant.KEY_ADDR);
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(0);
        String returnAdd = "";
        //当黑名单地址为空 50%
        if (values==null||values.isEmpty()){
            tarRate = BigDecimal.valueOf(0.5);
        }else{
            for(String value :values  ){
                //当为空,rate为0
                if (!StringUtils.isEmpty(search.getAddr())) {
                    //当黑名单地址为空 50%
                    if (StringUtils.isEmpty(value)) {
                        rate = BigDecimal.valueOf(0.5);
                    } else {
                        //输入地址和黑名单地址都不为空,做人工智能匹配
                        ParagraphVectors vectors = AddrVecEvent.vec;
                        try {
                            INDArray arr1 = vectors.inferVector(search.getAddr());
                            INDArray arr2 = vectors.inferVector(value);
                            //人工智能匹配地址
                            rate = BigDecimal.valueOf(Transforms.cosineSim(arr1, arr2));
                        } catch (Exception e) {
                            logger.info(e);
                            search.setAddr("");
                        }

                    }
                }

                //取id列表中最大匹配的值
                if (tarRate.compareTo(rate) < 0 || StringUtils.isEmpty(search.getAddr())) {
                    tarRate = rate;
                    //将匹配的地址返回
                    returnAdd = value;
                }
            }

        }
        search.setAddr(returnAdd);
        return this.isEnd(chainContext, tarRate);
    }
}
