package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/minimum-cost-to-merge-stones/">合并石头的最低成本</a>
 *
 * @author hein
 */
public class MergeStones {

    public static void main(String[] args) {
        MergeStones t = new MergeStones();
        System.out.println(t.mergeStones(new int[]{3, 2, 4, 1}, 2));
    }

    public int mergeStones(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) {
            return -1;
        }
        int[] psum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            psum[i] = psum[i - 1] + stones[i - 1];
        }
        int[][] dp = new int[n][n];
        for (int i = n - 2, ans; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                ans = Integer.MAX_VALUE;
                for (int m = i; m < j; m += k - 1) {
                    ans = Math.min(ans, dp[i][m] + dp[m + 1][j]);
                }
                if ((j - i) % (k - 1) == 0) {
                    // 最终能合并为 1 份，加合并代价
                    ans += psum[j + 1] - psum[i];
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][n - 1];
    }
}
