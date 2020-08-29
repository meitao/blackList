package com.kingstar.bw.common;

import com.kingstar.bw.exception.PlatException;

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
public class NumberListEvent implements InitDataEvent {

    public static final String reg = ",";

    public static final String FILE_NAME = "/home/meitao/test/zhengjian.csv";


    @Override
    public void onFire() {
        Map<String, String> param = new HashMap<String, String>();
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
                String name =  words[1].replaceAll("·", "");
                param.put(words[0], name);
            }
            LocalData.setCollection(Constant.KEY_NUMBER,param);
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
