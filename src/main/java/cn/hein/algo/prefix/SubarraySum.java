package cn.hein.algo.prefix;

import java.util.HashMap;

/**
 * <a href="https://leetcode.cn/problems/subarray-sum-equals-k/">和为 K 的子数组</a>
 *
 * @author hein
 */
public class SubarraySum {

    public int subarraySum(int[] nums, int k) {
        // 前缀和 -> 前缀和出现的次数
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int ans = 0;
        map.put(0, 1); // 不漏过 nums[i] = k 的解
        for (int num : nums) {
            sum += num;
            if (map.containsKey(sum - k)) {
                ans += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1); // 记录前缀和出现的次数
        }
        return ans;
    }
}
