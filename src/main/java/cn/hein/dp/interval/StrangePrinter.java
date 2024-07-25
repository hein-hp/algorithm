package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/strange-printer/">奇怪的打印机</a>
 *
 * @author hein
 */
public class StrangePrinter {

    public int strangePrinter(String s) {
        int n = s.length();
        char[] ch = s.toCharArray();
        int[][] dp = new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = (ch[i] == ch[i + 1]) ? 1 : 2;
        }
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (ch[i] == ch[j]) {
                    // 注意不用 +1，相当于在搞定 [i...j - 1] 的顺便把 ch[j] 搞定了
                    dp[i][j] = dp[i][j - 1];
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                    // 枚举划分点
                    for (int p = i; p < j; p++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][p] + dp[p + 1][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    public int strangePrinter1(String s) {
        return process(s.toCharArray(), 0, s.length() - 1);
    }

    /**
     * 在 ch[i...j] 范围内打印需要的最少打印次数
     */
    public int process(char[] ch, int i, int j) {
        if (i == j) {
            // 只有一个目标字符，只需要打印一次
            return 1;
        }
        if (i + 1 == j) {
            // 有两个目标字符，看这两个字符是否相等
            return ch[i] == ch[j] ? 1 : 2;
        }
        // 有多个目标字符
        if (ch[i] == ch[j]) {
            return process(ch, i, j - 1);
        } else {
            int min = Integer.MAX_VALUE;
            // 枚举划分点
            for (int p = i; p < j; p++) {
                min = Math.min(min, process(ch, i, p) + process(ch, p + 1, j));
            }
            return min;
        }
    }
}