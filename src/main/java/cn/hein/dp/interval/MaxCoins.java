package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/burst-balloons/">戳气球</a>
 *
 * @author hein
 */
public class MaxCoins {

    public static void main(String[] args) {
        MaxCoins t = new MaxCoins();
        System.out.println(t.maxCoins(new int[]{3, 1, 5, 8}));
    }

    public int maxCoins(int[] nums) {
        int m = nums.length;
        int[] balloons = new int[m + 2];
        balloons[0] = 1;
        System.arraycopy(nums, 0, balloons, 1, m);
        balloons[m + 1] = 1;
        int n = balloons.length;
        int[][] dp = new int[n][n];
        for (int i = 1; i < n - 1; i++) {
            dp[i][i] = balloons[i] * balloons[i - 1] * balloons[i + 1];
        }
        for (int i = n - 2; i > 0; i--) {
            for (int j = i + 1; j < n - 1; j++) {
                dp[i][j] = Integer.MIN_VALUE;
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], balloons[k] * balloons[i - 1] * balloons[j + 1] + dp[i][k - 1] + dp[k + 1][j]);
                }
            }
        }
        return dp[1][n - 2];
    }

    public int maxCoins1(int[] nums) {
        int n = nums.length;
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        System.arraycopy(nums, 0, balloons, 1, n);
        balloons[n + 1] = 1;
        return process(balloons, 1, balloons.length - 2);
    }

    /**
     * 在 balloons[i...j] 范围上且 balloons[i - 1] 和 balloons[j + 1] 一定没被戳破的情况下去戳气球，能获得的最大硬币数量
     * 所以调 process 函数的前提条件：
     * 1. 在 balloons[i...j] 范围上
     * 2. 且 balloons[i - 1] 和 balloons[j + 1] 一定没被戳破
     * 并且在尝试时考虑 k 位置的气球是最后被戳破的
     */
    private int process(int[] balloons, int i, int j) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return balloons[i] * balloons[i - 1] * balloons[i + 1];
        }
        int coins = 0;
        for (int k = i; k <= j; k++) {
            // k 是最后被戳破的位置
            coins = Math.max(coins, balloons[k] * balloons[i - 1] * balloons[j + 1] + process(balloons, i, k - 1) + process(balloons, k + 1, j));
        }
        return coins;
    }
}
