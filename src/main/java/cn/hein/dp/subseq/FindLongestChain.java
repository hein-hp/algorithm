package cn.hein.dp.subseq;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <a href="https://leetcode.cn/problems/maximum-length-of-pair-chain/">最长数对链</a>
 */
public class FindLongestChain {

    public int findLongestChain(int[][] pairs) {
        // 只要求根据 pairs[0] 升序即可
        Arrays.sort(pairs, Comparator.comparingInt(p -> p[0]));
        int n = pairs.length;
        int[] tails = new int[n];
        int len = 1;
        tails[0] = pairs[0][1];
        for (int i = 1, x; i < n; i++) {
            x = binary(tails, len, pairs[i][0]);
            if (x == -1) {
                tails[len++] = pairs[i][1];
            } else {
                tails[x] = Math.min(tails[x], pairs[i][1]);
            }
        }
        return len;
    }

    public int binary(int[] tails, int len, int x) {
        int l = 0, r = len - 1, mid;
        int ans = -1;
        while (l <= r) {
            mid = l + r >> 1;
            if (x <= tails[mid]) {
                r = mid - 1;
                ans = mid;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
