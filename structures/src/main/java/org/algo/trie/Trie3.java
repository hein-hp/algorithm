package org.algo.trie;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 静态数组实现
 * <br/><br/>
 * 测试链接：<a href="https://www.nowcoder.com/practice/7f8a8553ddbf4eaab749ec988726702b">字典树的实现</a>
 *
 * @author hein
 */
public class Trie3 {

    static int MAXN = 150001;
    static int m;

    static int cnt;
    static int[][] trie = new int[MAXN][26];
    static int[] e = new int[MAXN];
    static int[] p = new int[MAXN];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            build();
            m = in.nextInt();
            int op;
            String word;
            for (int i = 0; i < m; i++) {
                op = in.nextInt();
                word = in.next();
                if (op == 1) {
                    insert(word);
                } else if (op == 2) {
                    erase(word);
                } else if (op == 3) {
                    System.out.println(countWordsEqualTo(word) >= 1 ? "YES" : "NO");
                } else if (op == 4) {
                    System.out.println(countWordsStartWith(word));
                }
            }
            clear();
        }
    }

    /**
     * 将字符串 word 插入前缀树中
     */
    public static void insert(String word) {
        int cur = 1;
        ++p[cur];
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (trie[cur][path] == 0) {
                trie[cur][path] = ++cnt;
            }
            cur = trie[cur][path];
            ++p[cur];
        }
        ++e[cur];
    }

    /**
     * 返回前缀树中字符串 word 的个数
     */
    public static int countWordsEqualTo(String word) {
        int cur = 1;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (trie[cur][path] == 0) {
                return 0;
            }
            cur = trie[cur][path];
        }
        return e[cur];
    }

    /**
     * 返回前缀树中以 prefix 为前缀的字符串个数
     */
    public static int countWordsStartWith(String prefix) {
        int cur = 1;
        for (int i = 0, path; i < prefix.length(); i++) {
            path = prefix.charAt(i) - 'a';
            if (trie[cur][path] == 0) {
                return 0;
            }
            cur = trie[cur][path];
        }
        return p[cur];
    }

    /**
     * 从前缀树中移除字符串 word
     */
    public static void erase(String word) {
        if (countWordsEqualTo(word) == 0) {
            return;
        }
        int cur = 1;
        --p[cur];
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (--p[trie[cur][path]] == 0) {
                trie[cur][path] = 0;
                return;
            }
            cur = trie[cur][path];
        }
        --e[cur];
    }

    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(trie[i], 0);
            e[i] = 0;
            p[i] = 0;
        }
    }

    public static void build() {
        cnt = 1;
    }
}
