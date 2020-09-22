package com.kingstar.bw.util;

import com.luhuiguo.chinese.ChineseUtils;
import org.junit.Test;

public class ChineseTest {



    @Test
    public void toSimple() {

        String a = "asdadsa";
        String b = "dadsa";

        b=b.substring(0,b.length());
        a=a.substring(0,b.length());

        if(ChineseUtil.isChinese("a的sd")){
            System.out.println("中文");
        }
        String trad = ChineseUtils.toTraditional("123123");
        System.out.println(trad);
        trad = ChineseUtils.toTraditional(trad);
        System.out.println(trad);
        trad = ChineseUtils.toSimplified(trad);
        System.out.println(trad);
        trad = ChineseUtils.toPinyin(trad);
        System.out.println(trad);
//         Pinyin.INSTANCE.toUnformattedPinyin(trad);
    }
}
