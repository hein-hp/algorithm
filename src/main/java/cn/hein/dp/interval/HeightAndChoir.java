package cn.hein.dp.interval;

import java.io.*;

/**
 * <a href="https://www.luogu.com.cn/problem/P3205">合唱队</a>
 *
 * @author hein
 */
public class HeightAndChoir {

    static int MAX = 1001;
    static int n;
    static int[] perfect = new int[MAX];
    static int MOD = 19650827;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            for (int i = 0; i < n; i++) {
                in.nextToken();
                perfect[i] = (int) in.nval;
            }
            out.println(heightAndChoir());
        }
        out.flush();
        out.close();
    }

    public static int heightAndChoir() {
        int[][][] dp = new int[n][n][2];
        dp[n - 1][n - 1][0] = 1;
        dp[n - 1][n - 1][1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i][0] = 1;
            dp[i][i][1] = 1;
            if (perfect[i] < perfect[i + 1]) {
                dp[i][i + 1][0] = 1;
                dp[i][i + 1][1] = 1;
            }
        }
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                if (perfect[i] < perfect[i + 1]) {
                    dp[i][j][0] = (dp[i + 1][j][0] + dp[i][j][0]) % MOD;
                }
                if (perfect[i] < perfect[j]) {
                    dp[i][j][0] = (dp[i + 1][j][1] + dp[i][j][0]) % MOD;
                }
                if (perfect[j] > perfect[i]) {
                    dp[i][j][1] = (dp[i][j - 1][0] + dp[i][j][1]) % MOD;
                }
                if (perfect[j] > perfect[j - 1]) {
                    dp[i][j][1] = (dp[i][j - 1][1] + dp[i][j][1]) % MOD;
                }
            }
        }
        return (dp[0][n - 1][0] + dp[0][n - 1][1]) % MOD;
    }

    public static int heightAndChoir1() {
        return p(0, n - 1, 0) + p(0, n - 1, 1);
    }

    /**
     * 构成区间 [i..j] 初始队列的种数
     * last = 0 表示 [i] 位置的人最后进队列
     * last = 1 表示 [j] 位置的人最后进队列
     */
    public static int p(int i, int j, int last) {
        if (i == j) {
            return 1;
        }
        if (i + 1 == j) {
            return (perfect[i] > perfect[j]) ? 0 : 1;
        }
        int ways = 0;
        if (last == 0) {
            if (perfect[i] < perfect[i + 1]) {
                ways += p(i + 1, j, 0);
            }
            if (perfect[i] < perfect[j]) {
                ways += p(i + 1, j, 1);
            }
        } else {
            if (perfect[j] > perfect[i]) {
                ways += p(i, j - 1, 0);
            }
            if (perfect[j] > perfect[j - 1]) {
                ways += p(i, j - 1, 1);
            }
        }
        return ways;
    }
}
