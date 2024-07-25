package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/">让字符串成为回文串的最少插入次数</a>
 *
 * @author hein
 */
public class MinInsertions {

    public static void main(String[] args) {
        MinInsertions t = new MinInsertions();
        System.out.println(t.minInsertions("leetcode"));
    }

    public int minInsertions(String s) {
        int n = s.length();
        char[] chs = s.toCharArray();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = chs[i] == chs[i + 1] ? 0 : 1;
        }
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                if (chs[i] == chs[j]) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 递归（很好写，按照正常思路来就行）
     */
    public int minInsertions1(String s) {
        return process(s.toCharArray(), 0, s.length() - 1);
    }

    /**
     * 让 s[i...j] 成为回文串的最少插入次数
     */
    private int process(char[] s, int i, int j) {
        // i、j 位置是同一个字符，则本身就是回文串
        if (i == j) {
            return 0;
        }
        // 如果 i、j 位置相邻
        if (i == j + 1 || i + 1 == j) {
            return s[i] == s[j] ? 0 : 1;
        }
        // i、j 位置不相邻
        if (s[i] == s[j]) {
            return process(s, i + 1, j - 1);
        } else {
            int p1 = 1 + process(s, i + 1, j);
            int p2 = 1 + process(s, i, j - 1);
            return Math.min(p1, p2);
        }
    }
}
