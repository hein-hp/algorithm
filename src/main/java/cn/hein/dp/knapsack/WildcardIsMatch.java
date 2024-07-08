package cn.hein.dp.knapsack;

/**
 * <a href="https://leetcode.cn/problems/wildcard-matching/description/">通配符匹配</a>
 *
 * @author hein
 */
public class WildcardIsMatch {

    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;
        for (int i = m - 1; i >= 0; i--) {
            dp[n][i] = p.charAt(i) == '*' && dp[n][i + 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[i][j] = dp[i + 1][j + 1];
                } else if (p.charAt(j) == '*') {
                    dp[i][j] = dp[i + 1][j] || dp[i][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public boolean isMatch1(String s, String p) {
        return process(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    /**
     * s[i...] 能否由 p[j...] 完全匹配
     */
    private boolean process(char[] s, char[] p, int i, int j) {
        if (i == s.length) {
            if (j == p.length) {
                return true;
            } else {
                // s 完了，p 没完
                // 只有当 p[j] == '*' 并且还要看后续能否处理
                return p[j] == '*' && process(s, p, i, j + 1);
            }
        } else if (j == p.length) {
            // s 没完，p 完了
            return false;
        } else {
            // s 没完，p 没完
            // i 和 j 位置能够匹配上
            if (s[i] == p[j] || p[j] == '?') {
                return process(s, p, i + 1, j + 1);
            } else {
                // i 和 j 不能匹配，又分为两种情况，p[j] == '*' 以及 p[j] != '*'
                if (p[j] == '*') {
                    // p[j] 进行匹配，可以匹配多个字符
                    boolean p1 = process(s, p, i + 1, j);
                    // p[j] 不进行匹配
                    boolean p2 = process(s, p, i, j + 1);
                    return p1 || p2;
                } else {
                    return false;
                }
            }
        }
    }
}
