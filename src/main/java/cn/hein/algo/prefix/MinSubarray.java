package cn.hein.algo.prefix;

import java.util.HashMap;

/**
 * <a href="https://leetcode.cn/problems/make-sum-divisible-by-p/">使数组和能被 P 整除</a>
 *
 * @author hein
 */
public class MinSubarray {

    public static void main(String[] args) {
        MinSubarray t = new MinSubarray();
        System.out.println(t.minSubarray(new int[]{6, 3, 5, 2, 5, 2}, 9));
    }

    public int minSubarray(int[] nums, int p) {
        int mod = 0;
        for (int num : nums) {
            mod = (mod + num) % p;
        }
        if (mod == 0) {
            // 无需移除任何元素
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        int pmod = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0, find; i < nums.length; i++) {
            // i 位置前缀和 % p
            pmod = (pmod + nums[i]) % p;
            find = (pmod + p - mod) % p;
            if (map.containsKey(find)) {
                ans = Math.min(ans, i - map.get(find));
            }
            map.put(pmod, i);
        }
        return ans == nums.length ? -1 : ans;
    }
}
