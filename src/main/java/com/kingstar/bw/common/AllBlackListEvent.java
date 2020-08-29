package com.kingstar.bw.common;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.exception.PlatException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
public class AllBlackListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    public static final String reg = ",";

    public static final String FILE_NAME = "/home/meitao/test/quan.csv";

    @Override
    public void onFire() {
        Map<String, Search> param = new HashMap<String, Search>();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                //跳过第一行
                i++;
                if (i == 1) {
                    continue;
                }
                //将数据以逗号分割
                String[] words = line.split(reg);
                Search search = new Search();
                String result = null;
                for (int j = 0; j < words.length; j++) {
                    result = null;
                    result = words[j].replaceAll("\"", "");
                    result = result.replaceAll("·", "");

                    //如果字符串为空,则进行后续的处理
                    if(StringUtils.isEmpty(result)){
                        continue;
                    }
                    if (j == 0) {
                        search.setId(result);
                    }
                    if (j == 1) {
                        search.setName(result);
                    }
                    if (j == 2) {
                        search.setNation(result);
                    }
                    if (j == 3) {
                        search.setGender(result);
                    }
                    if (j == 4) {
                        search.setBirthDay(result);
                    }
                    if (j == 6) {
                        search.setNumber(result);
                    }
                    if (j == 7) {
                        search.setAddr(result);
                    }

                }

                if (StringUtils.isEmpty(search.getId())){
                    logger.info(search);
                    continue;
                }
                param.put(search.getId(), search);
            }

            LocalData.setCollection(Constant.KEY_ALL, param);
        } catch (IOException e) {
            throw new PlatException(e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new PlatException(e);
                }
            }
        }

    }
}
