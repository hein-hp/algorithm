package cn.hein.dp.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * <a href="https://www.luogu.com.cn/problem/P1757">通天之分组背包</a>
 */
public class PartitionedKnapsack {

    static int MAX = 1001;
    static int n;
    static int m;

    // nums[i][0] i 号物品的重量
    // nums[i][1] i 号物品的价值
    // nums[i][2] i 号物品的组号
    static int[][] nums = new int[MAX][3];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            m = (int) in.nval;
            in.nextToken();
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                nums[i][0] = (int) in.nval;
                in.nextToken();
                nums[i][1] = (int) in.nval;
                in.nextToken();
                nums[i][2] = (int) in.nval;
            }
            // 按照组号升序，保证相同组号的商品在一起
            Arrays.sort(nums, 1, n + 1, Comparator.comparingInt(x -> x[2]));
            out.println(compute2());
        }
        out.flush();
        out.close();
    }

    private static int compute1() {
        // 统计组数
        int teams = 1;
        for (int i = 2; i <= n; i++) {
            if (nums[i - 1][2] != nums[i][2]) {
                teams++;
            }
        }
        int[][] dp = new int[teams + 1][m + 1];
        for (int start = 1, end = 2, i = 1; start <= n; i++) {
            while (end <= n && nums[start][2] == nums[end][2]) {
                end++;
            }
            // [start, end) 就是同一组商品
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int k = start; k < end; k++) {
                    if (j >= nums[k][0]) {
                        // k 是组内商品编号
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - nums[k][0]] + nums[k][1]);
                    }
                }
            }
            start = end++;
        }
        return dp[teams][m];
    }

    private static int compute2() {
        int[] dp = new int[m + 1];
        for (int start = 1, end = 2; start <= n; start = end++) {
            while (end <= n && nums[start][2] == nums[end][2]) {
                end++;
            }
            for (int j = m; j >= 0; j--) {
                for (int k = start; k < end; k++) {
                    if (j >= nums[k][0]) {
                        dp[j] = Math.max(dp[j], dp[j - nums[k][0]] + nums[k][1]);
                    }
                }
            }
        }
        return dp[m];
    }
}
