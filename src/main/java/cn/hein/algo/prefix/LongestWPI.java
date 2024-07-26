package cn.hein.algo.prefix;

import java.util.HashMap;

/**
 * <a href="https://leetcode.cn/problems/longest-well-performing-interval/">表现良好的时间段</a>
 *
 * @author hein
 */
public class LongestWPI {

    public int longestWPI(int[] hours) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int ans = 0;
        map.put(0, -1);
        for (int i = 0; i < hours.length; i++) {
            sum += hours[i] > 8 ? 1 : -1;
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
            if (sum >= 1) {
                ans = Math.max(ans, i + 1);
            } else {
                if (map.containsKey(sum - 1)) {
                    ans = Math.max(ans, i - map.get(sum - 1));
                }
            }
        }
        return ans;
    }
}
