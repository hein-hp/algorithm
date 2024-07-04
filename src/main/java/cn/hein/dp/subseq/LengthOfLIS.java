package cn.hein.dp.subseq;

/**
 * <a href="https://leetcode.cn/problems/longest-increasing-subsequence/">最长递增子序列</a>
 */
public class LengthOfLIS {

    /**
     * O(n^2)
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int max = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * O(n * logn)
     */
    public int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n];
        int len = 0;
        for (int i = 0, x; i < n; i++) {
            x = binary(tails, len, nums[i]);
            if (x == -1) {
                tails[len++] = nums[i];
            } else {
                tails[x] = nums[i];
            }
        }
        return len;
    }

    /**
     * 在 tails 数组 [0, len] 范围内，二分查找 ≥ x 的最左（小）元素
     */
    public int binary(int[] tails, int len, int x) {
        int l = 0, r = len - 1, mid;
        int ans = -1;
        while (l <= r) {
            mid = l + r >> 1;
            if (x <= tails[mid]) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
