package cn.hein.dp.subseq;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/russian-doll-envelopes/">俄罗斯套娃信封问题</a>
 */
public class MaxEnvelopes {

    public static void main(String[] args) {
        int[][] envelopes = {
                {46, 89},
                {50, 53},
                {52, 68},
                {72, 45},
                {77, 81}
        };
        MaxEnvelopes m = new MaxEnvelopes();
        System.out.println("m.maxEnvelopes(envelopes) = " + m.maxEnvelopes(envelopes));
    }

    public int maxEnvelopes(int[][] envelopes) {
        // 先排一维，再排二维
        Arrays.sort(envelopes, (o1, o2) -> (o1[0] == o2[0]) ? o2[1] - o1[1] : o1[0] - o2[0]);
        int n = envelopes.length;
        int[] tails = new int[n];
        int len = 0;
        for (int i = 0, x; i < n; i++) {
            x = binary(tails, len, envelopes[i][1]);
            if (x == -1) {
                tails[len++] = envelopes[i][1];
            } else {
                tails[x] = envelopes[i][1];
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
