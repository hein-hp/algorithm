package cn.hein.dp.interval;

import java.io.*;

/**
 * <a href="https://www.nowcoder.com/practice/e391767d80d942d29e6095a935a5b96b">括号区间匹配</a>
 *
 * @author hein
 */
public class MinimumInsertionsToMatch {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String str = br.readLine();
        out.println(minimumInsertionsToMatch(str.toCharArray()));
        out.flush();
        out.close();
        br.close();
    }

    public static int minimumInsertionsToMatch(char[] ch) {
        int n = ch.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = (ch[i] == '(' && ch[i + 1] == ')') || (ch[i] == '[' && ch[i + 1] == ']') ? 0 : 2;
        }
        dp[n - 1][n - 1] = 1;
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if ((ch[i] == '[' && ch[j] == ']') || (ch[i] == '(' && ch[j] == ')')) {
                    // i、j 位置匹配，直接往中间找
                    dp[i][j] = dp[i + 1][j - 1];
                }
                // 枚举划分点
                for (int p = i; p < j; p++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][p] + dp[p + 1][j]);
                }
            }
        }
        return dp[0][n - 1];
    }

    public static int minimumInsertionsToMatch1(char[] str) {
        return process(str, 0, str.length - 1);
    }

    /**
     * 在 [i, j] 范围内最少插入多少个括号可以使得 [i, j] 成功配对
     */
    private static int process(char[] ch, int i, int j) {
        if (i == j) {
            // 只剩一个字符，必须插入一个字符
            return 1;
        }
        if (i + 1 == j) {
            // i、j 位置相邻
            if ((ch[i] == '(' && ch[j] == ')') || (ch[i] == '[' && ch[j] == ']')) {
                return 0;
            }
            return 2;
        }
        int min = Integer.MAX_VALUE;
        if ((ch[i] == '[' && ch[j] == ']') || (ch[i] == '(' && ch[j] == ')')) {
            // i、j 位置匹配，直接往中间找（两侧端点）
            min = process(ch, i + 1, j - 1);
        }
        // 枚举划分点（范围上划分点）
        for (int p = i; p < j; p++) {
            min = Math.min(min, process(ch, i, p) + process(ch, p + 1, j));
        }
        return min;
    }
}
