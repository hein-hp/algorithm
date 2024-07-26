package cn.hein.algo.prefix;

/**
 * <a href="https://leetcode.cn/problems/range-sum-query-immutable/">区域和检索 - 数组不可变</a>
 *
 * @author hein
 */
public class NumArray {

    static int MAXN = 10001;
    static int[] prefix = new int[MAXN];
    static int n;

    public NumArray(int[] nums) {
        n = nums.length;
        prefix[1] = nums[0];
        for (int i = 1; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
