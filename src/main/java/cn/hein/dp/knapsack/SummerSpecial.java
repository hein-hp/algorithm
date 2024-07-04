package cn.hein.dp.knapsack;

import java.io.*;

/**
 * <a href="https://leetcode.cn/problems/tJau2o/description/">夏季特惠</a>
 */
public class SummerSpecial {

    static int W = 0;
    static int N = 0;
    static int len = 0;
    static long ans = 0;
    static int MAXN = 501;

    static int[] weight = new int[MAXN];
    static long[] value = new long[MAXN];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            ans = 0;
            len = 0;
            N = (int) in.nval;
            in.nextToken();
            W = (int) in.nval;
            for (int i = 0, a, b, c; i < N; i++) {
                in.nextToken();
                // 原价
                a = (int) in.nval;
                in.nextToken();
                // 现价
                b = (int) in.nval;
                in.nextToken();
                // 快乐值
                c = (int) in.nval;
                if (a - b >= b) {
                    ans += c;
                    W += (a - b) - b;
                } else {
                    weight[len] = b - (a - b);
                    value[len++] = c;
                }
            }
            ans += compute();
            out.println(ans);
        }
        out.close();
    }

    private static long compute() {
        long[] dp = new long[W + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = W; j >= weight[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i - 1]] + value[i - 1]);
            }
        }
        return dp[W];
    }
}
