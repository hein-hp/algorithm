package cn.hein.dp.knapsack;

import java.io.*;

/**
 * <a href="https://www.luogu.com.cn/problem/P1776">宝物筛选</a>
 * 多重背包
 *
 * @author hein
 */
public class BoundedKnapsack {

    /**
     * 朴素枚举物品个数
     */
    public static class Enumerate {

        static int n;
        static int W;

        static int MAXN = 101;

        static int[] w = new int[MAXN];
        static int[] v = new int[MAXN];
        static int[] c = new int[MAXN];

        public static void main(String[] args) throws IOException {
            StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
            while (in.nextToken() != StreamTokenizer.TT_EOF) {
                n = (int) in.nval;
                in.nextToken();
                W = (int) in.nval;
                for (int i = 1; i <= n; i++) {
                    in.nextToken();
                    v[i] = (int) in.nval;
                    in.nextToken();
                    w[i] = (int) in.nval;
                    in.nextToken();
                    c[i] = (int) in.nval;
                }
                out.println(compute());
                out.flush();
            }
            out.close();
        }

        private static int compute() {
            int[][] dp = new int[n + 1][W + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= W; j++) {
                    for (int k = 0; k * w[i] <= j && k <= c[i]; k++) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * w[i]] + k * v[i]);
                    }
                }
            }
            return dp[n][W];
        }
    }

    /**
     * 转化为 0-1 背包
     */
    public static class EnumerateToOneTwo {

        static int n;
        static int W;
        static int m;

        static int MAXN = 100001;

        static int[] w = new int[MAXN];
        static int[] v = new int[MAXN];

        public static void main(String[] args) throws IOException {
            StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
            while (in.nextToken() != StreamTokenizer.TT_EOF) {
                n = (int) in.nval;
                in.nextToken();
                W = (int) in.nval;
                m = 1;
                for (int i = 1, value, weight, count; i <= n; i++) {
                    in.nextToken();
                    value = (int) in.nval;
                    in.nextToken();
                    weight = (int) in.nval;
                    in.nextToken();
                    count = (int) in.nval;
                    for (int j = 0; j < count; j++) {
                        w[m] = weight;
                        v[m++] = value;
                    }
                }
                out.println(compute());
                out.flush();
            }
            out.close();
        }

        private static int compute() {
            int[][] dp = new int[m + 1][W + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 0; j <= W; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= w[i]) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - w[i]] + v[i]);
                    }
                }
            }
            return dp[m][W];
        }
    }

    /**
     * 二进制优化
     */
    public static class BinaryOptimize {

        static int n;
        static int W;
        static int m;

        static int MAXN = 1001;
        static int MAXW = 40001;

        static int[] dp = new int[MAXW];

        static int[] w = new int[MAXN];
        static int[] v = new int[MAXN];

        public static void main(String[] args) throws IOException {
            StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
            while (in.nextToken() != StreamTokenizer.TT_EOF) {
                n = (int) in.nval;
                in.nextToken();
                W = (int) in.nval;
                m = 1;
                for (int i = 1, value, weight, count; i <= n; i++) {
                    in.nextToken();
                    value = (int) in.nval;
                    in.nextToken();
                    weight = (int) in.nval;
                    in.nextToken();
                    count = (int) in.nval;
                    for (int k = 1; k <= count; k <<= 1) {
                        w[m] = k * weight;
                        v[m++] = k * value;
                        count -= k;
                    }
                    if (count > 0) {
                        w[m] = count * weight;
                        v[m++] = count * value;
                    }
                }
                out.println(compute());
            }
            out.flush();
            out.close();
        }

        private static int compute() {
            for (int i = 1; i <= m; i++) {
                for (int j = W; j >= w[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
                }
            }
            return dp[W];
        }
    }
}
