package cn.hein.dp.knapsack;

/**
 * <a href="https://leetcode.cn/problems/target-sum/">目标和</a>
 */
public class FindTargetSumWays {

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        target = Math.abs(target);
        // 若 sum > target 或者 sum 和 target 的奇偶性不同则不可能找到方案
        if (sum < target || ((target & 1) ^ (sum & 1)) == 1) {
            return 0;
        }
        int W = target + sum >> 1;
        int N = nums.length;
        int[] dp = new int[W + 1];
        dp[0] = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = W; j >= nums[i - 1]; j--) {
                dp[j] += dp[j - nums[i - 1]];
            }
        }
        return dp[W];
    }
}
