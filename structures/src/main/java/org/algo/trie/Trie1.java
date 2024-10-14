package org.algo.trie;

/**
 * 类描述实现，小写字母作为字符集的前缀树
 *
 * @author hein
 */
public class Trie1 {

    Node root = new Node();

    /**
     * 将字符串 word 插入前缀树中
     */
    public void insert(String word) {
        Node cur = root;
        ++cur.p;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (cur.next[path] == null) {
                // 没有就新建
                cur.next[path] = new Node();
            }
            cur = cur.next[path];
            ++cur.p;
        }
        ++cur.e;
    }

    /**
     * 返回前缀树中字符串 word 的个数
     */
    public int countWordsEqualTo(String word) {
        Node cur = root;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (cur.next[path] == null) {
                return 0;
            }
            cur = cur.next[path];
        }
        return cur.e;
    }

    /**
     * 返回前缀树中以 prefix 为前缀的字符串个数
     */
    public int countWordsStartWith(String prefix) {
        Node cur = root;
        for (int i = 0, path; i < prefix.length(); i++) {
            path = prefix.charAt(i) - 'a';
            if (cur.next[path] == null) {
                return 0;
            }
            cur = cur.next[path];
        }
        return cur.p;
    }

    /**
     * 从前缀树中移除字符串 word
     */
    public void erase(String word) {
        if (countWordsEqualTo(word) == 0) {
            return;
        }
        Node cur = root;
        --cur.p;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (--cur.next[path].p == 0) {
                cur.next[path] = null;
                return;
            }
            cur = cur.next[path];
        }
        --cur.e;
    }

    /**
     * 前缀树节点
     */
    static class Node {
        int p; // 经过该节点的字符串数量
        int e; // 以该节点结尾的字符串数量
        Node[] next;

        public Node() {
            p = 0;
            e = 0;
            next = new Node[26];
        }
    }
}
