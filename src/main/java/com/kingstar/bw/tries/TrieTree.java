package com.kingstar.bw.tries;

/**
 * @ProjectName: demo
 * @Package: com.bw.tries
 * @ClassName: Test
 * @Author: meitao
 * @Description:  字典树,用来做字符串匹配查询
 * @Date: 20-8-21 下午3:17
 * @Version: 1.0
 */
public class TrieTree {

    /**
     * 根节点
     */
    private TrieNode root;

    private Spliter spliter ;

    /** Initialize your data structure here. */
    public TrieTree() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void add(String word) {
        TrieNode before = root;
        TrieNode node;
        String[] words = spliter.split(word);
        // 遍历插入单词中的每一个字母
        for (int i = 0; i < words.length; i++) {
            node = new TrieNode();
            node = before.setNode(words[i], node);
            before = node;
        }
        // 设置当前为终点
        before.setIsWordsEnd();
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode before = root;
        TrieNode temp;
        String[] words = spliter.split(word);
        // 遍历查找
        for (int i = 0; i < words.length; i++) {
            temp = before.getNextNode(words[i]);
            if (temp == null) {
                return false;
            }
            before = temp;
        }
        // 且最后一个节点也是终点
        return before.isWordsEnd();
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode before = root;
        TrieNode temp;
        String[] words = spliter.split(prefix);
        // 遍历查找
        for (int i = 0; i < words.length; i++) {
            temp = before.getNextNode(words[i]);
            if (temp == null) {
                return false;
            }
            before = temp;
        }
        return true;
    }

    public Spliter getSpliter() {
        return spliter;
    }

    public void setSpliter(Spliter spliter) {
        this.spliter = spliter;
    }
}