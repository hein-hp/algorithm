package cn.hein.dp.knapsack;

/**
 * <a href="https://leetcode.cn/problems/regular-expression-matching/">正则表达式匹配</a>
 *
 * @author hein
 */
public class RegularIsMatch {

    public static void main(String[] args) {
        String s = "abcc";
        String p = "aba*c.";
        RegularIsMatch t = new RegularIsMatch();
        System.out.println("t.isMatch(s, p) = " + t.isMatch(s, p));
    }

    /**
     * 严格位置依赖的动态规划（根据递归改写的）
     */
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;
        for (int i = m - 1; i >= 0; i--) {
            dp[n][i] = i + 1 < m && p.charAt(i + 1) == '*' && dp[n][i + 2];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (j + 1 == m || p.charAt(j + 1) != '*') {
                    dp[i][j] = (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') && dp[i + 1][j + 1];
                } else {
                    dp[i][j] = dp[i][j + 2] || (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') && dp[i + 1][j];
                }
            }
        }
        return dp[0][0];
    }

    /**
     * 记忆化搜索
     */
    public boolean isMatch2(String s, String p) {
        int n = s.length();
        int m = p.length();
        // dp[i][j] = 0 => 没算过
        // dp[i][j] = 1 => 结果是 true
        // dp[i][j] = 2 => 结果是 false
        int[][] dp = new int[n + 1][m + 1];
        process2(s.toCharArray(), p.toCharArray(), 0, 0, dp);
        return dp[n][m] == 1;
    }

    private boolean process2(char[] s, char[] p, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j] == 1;
        }
        boolean c;
        if (i == s.length) {
            if (j == p.length) {
                c = true;
            } else {
                c = j + 1 < p.length && p[j + 1] == '*' && process2(s, p, i, j + 2, dp);
            }
        } else if (j == p.length) {
            c = false;
        } else {
            if (j + 1 == p.length || p[j + 1] != '*') {
                c = (s[i] == p[j] || p[j] == '.') && process2(s, p, i + 1, j + 1, dp);
            } else {
                c = process2(s, p, i, j + 2, dp) || (s[i] == p[j] || p[j] == '.') && process2(s, p, i + 1, j, dp);
            }
        }
        dp[i][j] = c ? 1 : 2;
        return c;
    }

    /**
     * 递归
     */
    public boolean isMatch1(String s, String p) {
        return process1(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    /**
     * 返回在 s[i...] 能否被 p[j...] 完全匹配
     */
    private boolean process1(char[] s, char[] p, int i, int j) {
        if (i == s.length) {
            // s 没了
            if (j == p.length) {
                // p 也没了
                return true;
            } else {
                // p 还剩一些后缀
                // 如果 p[j + 1] == '*'，那么 p[j...j + 1] 可以被消掉，继续看 p[j + 2...] 能不能被消掉
                // 这里注意，* 匹配 0 个 * 前面的那个字符，也就是说 * 可以消去 * 前面的那个字符
                return j + 1 < p.length && p[j + 1] == '*' && process1(s, p, i, j + 2);
            }
        } else if (j == p.length) {
            // s 还有但是 p 没了
            return false;
        } else {
            // s 和 p 都还有
            // 分情况讨论，这里对 j + 1 位置是否是 * 做讨论
            if (j + 1 == p.length || p[j + 1] != '*') {
                // p 下一个字符不是 *，那么当前字符必须能匹配，匹配条件：s[i] == p[j] || p[j] == '.'，并且后续也要匹配上
                // 注意 p 下一个位置没有字符也算下一个字符不是 * 这种情况
                return (s[i] == p[j] || p[j] == '.') && process1(s, p, i + 1, j + 1);
            } else {
                // p[j + 1] 为 *
                // 这里就是完全背包的思想
                // 选择1：当前 p[j...j + 1] 是 x*，但是不让它匹配 s[i]，则继续 process(s, p, i, j + 2)
                boolean p1 = process1(s, p, i, j + 2);
                // 选择2：当前 p[j...j + 1] 是 x*，可以搞定 s[i]，也就是 (s[i] == p[j] || p[j] == '.') 同时继续匹配
                // process1(s, p, i + 1, j) 这里是 j 而不是 j + 1 是因为 j + 1 是可以生成任意数量的字符，说不定它可以继续匹配 s 后面的字符
                boolean p2 = (s[i] == p[j] || p[j] == '.') && process1(s, p, i + 1, j);
                return p1 || p2;
            }
        }
    }
}
