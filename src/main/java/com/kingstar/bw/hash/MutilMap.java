package com.kingstar.bw.hash;

import java.util.HashMap;
import java.util.Map;

/**
 *  带有层级的hash
 */
public class MutilMap {
    //层级
    private  int level = 0 ;
    //下一层的映射
    private Map<String,MutilMap> para = new HashMap<String,MutilMap> ();
    //数据id
//    private String id ;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<String, MutilMap> getPara() {
        return para;
    }

    public void setPara(Map<String, MutilMap> para) {
        this.para = para;
    }


}
