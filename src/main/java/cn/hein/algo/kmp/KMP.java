package cn.hein.algo.kmp;

/**
 * KMP
 *
 * @author hein
 */
public class KMP {

    public static void main(String[] args) {
        KMP t = new KMP();
        System.out.println(t.kmp("bcdabcd".toCharArray(), "abc".toCharArray()));
    }

    public int strStr(String haystack, String needle) {
        return kmp(haystack.toCharArray(), needle.toCharArray());
    }

    public int kmp(char[] t, char[] p) {
        int n = t.length;
        int m = p.length;
        // x 表示 t 串当前比对的位置
        // y 表示 p 串当前比对的位置
        int x = 0, y = 0;
        // 生成 next 数组
        int[] next = next(p, p.length);
        while (x < n && y < m) {
            if (t[x] == p[y]) {
                // 对应位置字符相等，继续向下匹配
                ++x;
                ++y;
            } else {
                // 发生失配
                if (y == 0) {
                    // 匹配失败，但是 p 串还在开头
                    ++x;
                } else {
                    // 匹配失败，next[y] 为 p 串下一次应该和 t 串开始匹配的位置
                    // 核心
                    y = next[y];
                }
            }
        }
        return y == m ? x - y : -1;
    }

    private int[] next(char[] p, int len) {
        int[] next = new int[len];
        int i = 0, j = -1;
        next[0] = -1;
        while (i < len - 1) {
            if (j == -1 || p[i] == p[j]) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }
}
