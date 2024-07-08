package cn.hein.dp.knapsack;

import java.io.*;
import java.util.Arrays;

/**
 * <a href="https://www.luogu.com.cn/problem/P1616">疯狂的采药</a>
 */
public class UnboundedKnapsack {

    static int MAXM = 10001;
    static int MAXT = 10000001;

    // 总时间
    static int t;
    // m 种草药
    static int m;

    // nums[i][0] 第 i 种草药采药的时间
    // nums[i][1] 第 i 种草药的价值
    static int[][] nums = new int[MAXM][2];

    static long[] dp = new long[MAXT];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            t = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            for (int i = 1; i <= m; i++) {
                in.nextToken();
                nums[i][0] = (int) in.nval;
                in.nextToken();
                nums[i][1] = (int) in.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
    }

    // 洛谷测评太严格了，只有优化空间才能 ac
    private static long compute() {
        Arrays.fill(dp, 0, t + 1, 0);
        for (int i = 1; i <= m; i++) {
            for (int j = nums[i][0]; j <= t; j++) {
                dp[j] = Math.max(dp[j], dp[j - nums[i][0]] + nums[i][1]);
            }
        }
        return dp[t];
    }

    // 严格位置依赖的动态规划
    // 空间不足导致无法通过全部测试用例
    private static int compute2() {
        int[][] dp = new int[m + 1][t + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= t; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i][0] >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - nums[i][0]] + nums[i][1]);
                }
            }
        }
        return dp[m][t];
    }

    // 枚举物品个数
    private static int compute1() {
        int[][] dp = new int[m + 1][t + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= t; j++) {
                for (int k = 0; k * nums[i][0] <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * nums[i][0]] + k * nums[i][1]);
                }
            }
        }
        return dp[m][t];
    }
}
