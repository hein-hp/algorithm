package cn.hein.dp.interval;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/minimum-cost-to-cut-a-stick/">切棍子的最小得分</a>
 *
 * @author hein
 */
public class MinCost {

    public static void main(String[] args) {
        MinCost t = new MinCost();
        System.out.println(t.minCost(2, new int[]{1}));
    }

    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts);
        int c = cuts.length;
        int[] newCuts = new int[c + 2];
        System.arraycopy(cuts, 0, newCuts, 1, c);
        newCuts[c + 1] = n;
        int[][] dp = new int[c + 2][c + 2];
        for (int i = c + 2 - 3; i >= 0; i--) {
            for (int j = i + 2; j < c + 2; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], newCuts[j] - newCuts[i] + dp[i][k] + dp[k][j]);
                }
            }
        }
        return dp[0][c + 1];
    }

    public int minCost1(int n, int[] cuts) {
        Arrays.sort(cuts);
        int c = cuts.length;
        int[] newCuts = new int[c + 2];
        System.arraycopy(cuts, 0, newCuts, 1, c);
        newCuts[c + 1] = n;
        return process(newCuts, 0, c + 1);
    }

    /**
     * 在 cuts[i...j] 上选择切割点做切割的最小成本
     */
    private int process(int[] cuts, int i, int j) {
        if (j - i < 2) {
            return 0;
        }
        // 枚举切割点
        int cost = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) {
            // cuts[j] - cuts[i] 为在 k 切割的成本
            cost = Math.min(cost, cuts[j] - cuts[i] + process(cuts, i, k) + process(cuts, k, j));
        }
        return cost;
    }
}
