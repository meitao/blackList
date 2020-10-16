package com.kingstar.bw.util;

import com.luhuiguo.chinese.ChineseUtils;
import com.luhuiguo.chinese.pinyin.PinyinFormat;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

import java.util.List;

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
        String trad = ChineseUtils.toTraditional("'龟'");
        System.out.println(trad);
        trad = ChineseUtils.toTraditional(trad);
        System.out.println(trad);
        trad = ChineseUtils.toSimplified(trad);
        System.out.println(trad);

        trad = ChineseUtils.toPinyin(trad,PinyinFormat.TONELESS_PINYIN_FORMAT);
        System.out.println(trad);
//         Pinyin.INSTANCE.toUnformattedPinyin(trad);
    }

    @Test
    public void topinyin() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] pinyin = new String[0]; // str.charAt(0) 第一个汉字
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray('龟',format);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        for (String py : pinyin) {
            System.out.println(py);
        }
    }

    @Test
    public void hanPinyin() {

//        List<Pinyin>  list = HanLP.convertToPinyinList("薄");
//        System.out.println(list);
    }
}
