package cn.hein.algo.prefix;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/find-the-longest-substring-containing-vowels-in-even-counts/">每个元音包含偶数次的最长子字符串</a>
 *
 * @author hein
 */
public class FindTheLongestSubstring {

    public int findTheLongestSubstring(String s) {
        char[] chs = s.toCharArray();
        int ans = 0;
        int[] map = new int[32];
        Arrays.fill(map, -2);
        map[0] = -1;
        for (int i = 0, status = 0; i < chs.length; i++) {
            int p = calc(chs[i]);
            if (p != -1) {
                // 确实是元音字符，status 对应位上的状态反转
                status ^= 1 << p;
            }
            if (map[status] != -2) {
                ans = Math.max(ans, i - map[status]);
            } else {
                map[status] = i;
            }
        }
        return ans;
    }

    private int calc(char c) {
        return switch (c) {
            case 'a' -> 4;
            case 'e' -> 3;
            case 'i' -> 2;
            case 'o' -> 1;
            case 'u' -> 0;
            default -> -1;
        };
    }
}
