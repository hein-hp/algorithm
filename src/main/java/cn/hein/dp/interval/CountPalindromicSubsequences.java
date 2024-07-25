package cn.hein.dp.interval;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/count-different-palindromic-subsequences/">统计不同回文子序列</a>
 *
 * @author hein
 */
public class CountPalindromicSubsequences {

    public static void main(String[] args) {
        CountPalindromicSubsequences t = new CountPalindromicSubsequences();
        System.out.println(t.countPalindromicSubsequences("abacada"));
    }

    static int mod = 1000000007;

    public int countPalindromicSubsequences(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        // helper[i][0]: 左边最接近 chars[i] 的与其相同的字符的位置
        // helper[i][1]: 右边最接近 chars[i] 的与其相同的字符的位置
        int[][] helper = new int[n][2];
        // 字符上次出现的位置
        int[] last = new int[4];
        Arrays.fill(last, -1);
        for (int i = 0; i < chars.length; i++) {
            helper[i][0] = last[chars[i] - 'a'];
            last[chars[i] - 'a'] = i;
        }
        Arrays.fill(last, n);
        for (int i = chars.length - 1; i >= 0; i--) {
            helper[i][1] = last[chars[i] - 'a'];
            last[chars[i] - 'a'] = i;
        }
        long[][] dp = new long[n][n];
        for (int i = 0; i < dp.length; i++) {
            dp[i][i] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (chars[i] != chars[j]) {
                    dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1] + mod) % mod;
                } else {
                    int r = helper[j][0], l = helper[i][1];
                    if (l > r) {
                        // (i...j) 内不存在 chars[i] 字符
                        dp[i][j] = (2 * dp[i + 1][j - 1] + 2) % mod;
                    } else if (l == r) {
                        // (i...j) 内存在一个 chars[i] 字符
                        dp[i][j] = (2 * dp[i + 1][j - 1] + 1) % mod;
                    } else {
                        // (i...j) 内存在多个 chars[i] 字符
                        dp[i][j] = (2 * dp[i + 1][j - 1] - dp[l + 1][r - 1] + mod) % mod;
                    }
                }
            }
        }
        return (int) (dp[0][n - 1] % mod);
    }
}
