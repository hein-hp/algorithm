package cn.hein.dp.knapsack;

import java.io.*;

/**
 * <a href="https://www.luogu.com.cn/problem/P1048">01背包</a>
 */
public class Zero1Knapsack {

    static int W = 0;
    static int N = 0;
    static int MAXN = 101;

    static int[] weight = new int[MAXN];
    static int[] value = new int[MAXN];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            W = (int) in.nval;
            in.nextToken();
            N = (int) in.nval;
            for (int i = 0; i < N; i++) {
                in.nextToken();
                weight[i] = (int) in.nval;
                in.nextToken();
                value[i] = (int) in.nval;
            }
            out.println(zero1Knapsack1());
            out.close();
        }
    }

    private static int zero1Knapsack1() {
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= W; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= weight[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        return dp[N][W];
    }

    private static int zero1Knapsack2() {
        int[] dp = new int[W + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = W; j >= weight[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i - 1]] + value[i - 1]);
            }
        }
        return dp[W];
    }
}
