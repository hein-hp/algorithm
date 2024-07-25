package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/predict-the-winner/">预测赢家</a>
 *
 * @author hein
 */
public class PredictTheWinner {

    public static void main(String[] args) {
        PredictTheWinner t = new PredictTheWinner();
        System.out.println(t.predictTheWinner(new int[]{1, 5, 2}));
    }

    public boolean predictTheWinner(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = nums[i];
            dp[i][i + 1] = Math.max(nums[i], nums[i + 1]);
        }
        dp[n - 1][n - 1] = nums[n - 1];
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = Math.max(nums[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]),
                        nums[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]));
            }
        }
        return 2 * dp[0][n - 1] >= sum;
    }

    public boolean predictTheWinner1(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int one = process(nums, 0, nums.length - 1);
        int two = sum - one;
        return one >= two;
    }

    /**
     * nums[i...j] 范围中进行选择，同时玩家1作为先手，玩家1能获得的分数
     */
    private int process(int[] nums, int i, int j) {
        // 如果区间只剩一个数，同时玩家1作为先手，那么玩家1必定先拿，所以能获得 nums[i] 的分数
        if (i == j) {
            return nums[i];
        }
        // 如果区间还剩两个数，同时玩家1作为先手，那么玩家1必定挑最大的数拿，所以能获得 max(nums[i], nums[j]) 的分数
        if (i + 1 == j) {
            return Math.max(nums[i], nums[j]);
        }
        // 如果区间还剩多个数，那么玩家1有两种选择的方案
        // 玩家1如果选左边的数，即i位置的数，那么后续玩家2必定在[i+1...j]区间内做出最优的决策使得玩家1在下一次选数时获得的分数最小（这就是为什么取min而不是max）
        // 而后续玩家2也有两种选择，对应 process(nums, i + 2, j) （玩家2选择i+1位置）和 process(nums, i + 1, j - 1)（玩家2选择j位置）
        int max1 = nums[i] + Math.min(process(nums, i + 2, j), process(nums, i + 1, j - 1));
        // 玩家1如果选右边的数，即j位置的数，那么后续玩家2必定在[i...j-1]区间内做出最优的决策使得玩家1在下一次选数时获得的分数最小（这就是为什么取min而不是max）
        // 而后续玩家2也有两种选择，对应 process(nums, i + 1, j - 1) （玩家2选择i位置）和 process(nums, i, j - 2)（玩家2选择j-1位置）
        int max2 = nums[j] + Math.min(process(nums, i + 1, j - 1), process(nums, i, j - 2));
        return Math.max(max1, max2);
    }
}
