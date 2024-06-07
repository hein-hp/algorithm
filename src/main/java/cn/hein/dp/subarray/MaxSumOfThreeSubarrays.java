package cn.hein.dp.subarray;

/**
 * <a href="https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/">三个无重叠子数组的最大和</a>
 */
public class MaxSumOfThreeSubarrays {

    public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] sums = new int[n];
        for (int i = 0, sum = 0, m = 0; i < n; i++) {
            sum += nums[i];
            if (i >= k - 1) {
                sums[m++] = sum;
                sum -= nums[i + 1 - k];
            }
        }
        int[] prefix = new int[n];
        for (int i = k - 1; i < n; i++) {
            if (i >= 1) {
                if (sums[prefix[i - 1]] < sums[i - k + 1]) {
                    prefix[i] = i - k + 1;
                } else {
                    prefix[i] = prefix[i - 1];
                }
            }
        }
        int[] suffix = new int[n];
        suffix[n - k] = n - k;
        for (int i = n - k - 1; i >= 0; i--) {
            if (sums[suffix[i + 1]] <= sums[i]) {
                suffix[i] = i;
            } else {
                suffix[i] = suffix[i + 1];
            }
        }
        int max = 0;
        int a = 0, b = 0, c = 0;
        for (int i = k; i < n - 2 * k + 1; i++) {
            int sum = sums[prefix[i - 1]] + sums[i] + sums[suffix[i + k]];
            if (sum > max) {
                max = sum;
                a = prefix[i - 1];
                b = i;
                c = suffix[i + k];
            }
        }
        return new int[]{a, b, c};
    }
}
