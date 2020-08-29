package com.kingstar.bw.tries;

import java.util.LinkedList;

/**
 * @ProjectName: demo
 * @Package: com.bw.tries
 * @ClassName: Test
 * @Author: meitao
 * @Description: 字典树节点,nodes为该节点的下一层节点的集合,isWordsEnd
 * @Date: 20-8-21 下午3:17
 * @Version: 1.0
 */
public class TrieLinkNode {

    LinkedList<TrieLinkNode> nodes ;

    boolean isWordsEnd = false;

    private String key ;


    public TrieLinkNode(String key) {

        this.key=key;
        // 当前是否已经结束
        isWordsEnd = false;
    }

    /**
     * 当前节点是否包含字符 ch
     */
    public boolean contains(String ch) {
        return nodes.contains(ch);
    }

    /**
     * 设置新的下一个节点
     */
    public TrieLinkNode setNode(String ch, TrieLinkNode node) {
        // 判断当前新的节点是否已经存在
//        TrieNode tempNode = nodes[ch - 'a'];
        //懒加载
        if(nodes ==null){
            nodes = new  LinkedList<TrieLinkNode>();
        }
        // 如果存在，就直接返回已经存在的节点
        if (nodes.contains(ch)) {
            return nodes.get(nodes.indexOf(ch));
        }

        // 否则就设置为新的节点，并返回
//        nodes[ch - 'a'] = node;
        nodes.add(node);
        return node;
    }

    /**
     * 获取 ch 字符
     */
    public TrieLinkNode getNextNode(String ch) {
        return nodes.get(nodes.indexOf(ch));
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