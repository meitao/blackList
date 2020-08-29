package com.kingstar.bw.tries;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: demo
 * @Package: com.bw.tries
 * @ClassName: Test
 * @Author: meitao
 * @Description: 字典树节点,nodes为该节点的下一层节点的集合,isWordsEnd
 * @Date: 20-8-21 下午3:17
 * @Version: 1.0
 */
public class TrieNode {

    Map<String,TrieNode> nodes ;

    boolean isWordsEnd = false;


    public TrieNode() {

        // 当前是否已经结束
        isWordsEnd = false;
    }

    /**
     * 当前节点是否包含字符 ch
     */
    public boolean contains(String ch) {
        return nodes.containsKey(ch);
    }

    /**
     * 设置新的下一个节点
     */
    public TrieNode setNode(String ch, TrieNode node) {
        // 判断当前新的节点是否已经存在
//        TrieNode tempNode = nodes[ch - 'a'];
        //懒加载
        if(nodes ==null){
            nodes = new HashMap<String,TrieNode>();
        }
        // 如果存在，就直接返回已经存在的节点
        if (nodes.containsKey(ch)) {
            return nodes.get(ch);
        }

        // 否则就设置为新的节点，并返回
//        nodes[ch - 'a'] = node;
        nodes.put(ch,node);
        return node;
    }

    /**
     * 获取 ch 字符
     */
    public TrieNode getNextNode(String ch) {
        return nodes.get(ch);
    }

    /**
     * 设置当前节点为结束
     */
    public void setIsWordsEnd() {
        isWordsEnd = true;
    }

    /**
     * 当前节点是否已经结束
     */
    public boolean isWordsEnd() {
        return isWordsEnd;
    }
}