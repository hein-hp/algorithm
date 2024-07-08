package cn.hein.dp.knapsack;

import java.io.*;
import java.util.Arrays;

/**
 * <a href="https://www.luogu.com.cn/problem/P2918">Buying Hay S</a>
 *
 * @author hein
 */
public class BuyingHayMinimumCost {

    static int N;
    static int H;

    static int MAXN = 101;

    static int[] P = new int[MAXN];
    static int[] C = new int[MAXN];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            N = (int) in.nval;
            in.nextToken();
            H = (int) in.nval;
            for (int i = 1; i <= N; i++) {
                in.nextToken();
                P[i] = (int) in.nval;
                in.nextToken();
                C[i] = (int) in.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
    }

    private static int compute() {
        // dp[i][j] 表示在前 i 个公司中选择，采购至少 j 磅干草的最小开销
        int[][] dp = new int[N + 1][H + 1];
        Arrays.fill(dp[0], 1, H + 1, Integer.MAX_VALUE);
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= H; j++) {
                dp[i][j] = dp[i - 1][j];
                // 已经满足采购 j 磅干草的需求
                if (j <= P[i]) {
                    dp[i][j] = Math.min(dp[i][j], C[i]);
                } else {
                    // 即使采购了当前的干草，还是不够
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - P[i]] + C[i]);
                }
            }
        }
        return dp[N][H];
    }
}
