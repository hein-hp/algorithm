package cn.hein.dp.knapsack;

import java.io.*;

/**
 * <a href="https://www.luogu.com.cn/problem/P1064">金明的预算方案</a>
 */
public class BudgetPlan {

    static int W, N;
    static int MAXN = 61;

    static int[] prices = new int[MAXN];
    static int[] values = new int[MAXN];
    static int[] slaveNum = new int[MAXN];
    static boolean[] master = new boolean[MAXN];
    static int[][] slave = new int[MAXN][2];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            W = (int) in.nval;
            in.nextToken();
            N = (int) in.nval;
            clear();
            for (int i = 1, price, importance, belong; i <= N; i++) {
                in.nextToken();
                price = (int) in.nval;
                in.nextToken();
                importance = (int) in.nval;
                in.nextToken();
                belong = (int) in.nval;
                prices[i] = price;
                values[i] = price * importance;
                master[i] = belong == 0;
                if (belong != 0) {
                    slave[belong][slaveNum[belong]++] = i;
                }
            }
            out.println(compute2());
        }
        out.close();
    }

    private static void clear() {
        for (int i = 1; i < N; i++) {
            slaveNum[i] = 0;
        }
    }

    private static int compute2() {
        int[] dp = new int[W + 1];
        for (int i = 1; i <= N; i++) {
            if (master[i]) {
                for (int j = W, s1, s2; j >= prices[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - prices[i]] + values[i]);
                    s1 = slaveNum[i] >= 1 ? slave[i][0] : -1;
                    s2 = slaveNum[i] >= 2 ? slave[i][1] : -1;
                    if (s1 != -1 && j >= prices[i] + prices[s1]) {
                        dp[j] = Math.max(dp[j], dp[j - prices[i] - prices[s1]] + values[i] + values[s1]);
                    }
                    if (s2 != -1 && j >= prices[i] + prices[s2]) {
                        dp[j] = Math.max(dp[j], dp[j - prices[i] - prices[s2]] + values[i] + values[s2]);
                    }
                    if (s1 != -1 && s2 != -1 && j >= prices[i] + prices[s1] + prices[s2]) {
                        dp[j] = Math.max(dp[j], dp[j - prices[i] - prices[s1] - prices[s2]] + values[i] + values[s1] + values[s2]);
                    }
                }
            }
        }
        return dp[W];
    }

    private static int compute1() {
        int[][] dp = new int[N + 1][W + 1];
        // 上一个主件
        int last = 0;
        for (int i = 1; i <= N; i++) {
            if (master[i]) {
                for (int j = 0, s1, s2; j <= W; j++) {
                    dp[i][j] = dp[last][j];
                    if (j >= prices[i]) {
                        dp[i][j] = Math.max(dp[i][j], dp[last][j - prices[i]] + values[i]);
                    }
                    s1 = slaveNum[i] >= 1 ? slave[i][0] : -1;
                    s2 = slaveNum[i] >= 2 ? slave[i][1] : -1;
                    if (s1 != -1 && j >= prices[i] + prices[s1]) {
                        dp[i][j] = Math.max(dp[i][j], dp[last][j - prices[i] - prices[s1]] + values[i] + values[s1]);
                    }
                    if (s2 != -1 && j >= prices[i] + prices[s2]) {
                        dp[i][j] = Math.max(dp[i][j], dp[last][j - prices[i] - prices[s2]] + values[i] + values[s2]);
                    }
                    if (s1 != -1 && s2 != -1 && j >= prices[i] + prices[s1] + prices[s2]) {
                        dp[i][j] = Math.max(dp[i][j], dp[last][j - prices[i] - prices[s1] - prices[s2]] + values[i] + values[s1] + values[s2]);
                    }
                }
                last = i;
            }
        }
        return dp[last][W];
    }
}
