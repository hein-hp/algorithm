package cn.hein.dp.knapsack;

/**
 * <a href="https://leetcode.cn/problems/last-stone-weight-ii/">最后一块石头的重量 II</a>
 */
public class LastStoneWeightII {

    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int w = sum / 2;
        int[] dp = new int[w + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = w; j >= stones[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i - 1]] + stones[i - 1]);
            }
        }
        return sum - 2 * dp[w];
    }
}
