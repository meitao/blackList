package com.kingstar.bw.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kingstar.bw.exception.PlatException;
import com.luhuiguo.chinese.ChineseUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 中文字符处理
 */
public class ChineseUtil {
    /**
     * 判断该字符串是否为中文
     * @param string
     * @return
     */
    public static boolean isChinese(String string){
        if(string==null){
            throw new PlatException("字符串不能为空!");
        }
        int n = 0;
        for(int i = 0; i < string.length(); i++) {
            n = (int)string.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将中文字符装换为拼音
     * 字符串有多个字符是多音字，会产生笛卡尔积拼音组合
     * @param name
     * @return
     */
    public static  List<String> toPingyin(String name){
        //构建笛卡尔积
        List<ImmutableSet<String>> listImmutableSet = new ArrayList<ImmutableSet<String>>();
        char[] words =name.toCharArray();
        for (char word :words){
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            String[] pinyin;
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(word,format);
                ImmutableSet<String> charList  = ImmutableSet.copyOf(pinyin);
                listImmutableSet.add(charList);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                    throw new PlatException(e);
            }
        }
        //生成笛卡尔积
        Set<List<String>> set =   Sets.cartesianProduct(listImmutableSet);

        List<String> result = new ArrayList<String>();
        //拼接拼音字符串
        for(List<String> list :set){
            StringBuilder sb = new StringBuilder();
            for (String piny:list){
                sb.append(piny);
            }
            result.add(sb.toString());
        }
        return  result;
    }
}
