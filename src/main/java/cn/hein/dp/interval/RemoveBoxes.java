package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/remove-boxes/">移除盒子</a>
 *
 * @author hein
 */
public class RemoveBoxes {

    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return p(boxes, 0, n - 1, 0, dp);
    }

    /**
     * 在 boxes[i...j] 范围内移除盒子，并且 boxes[i] 前存在连续 k 个与其相等的盒子，获得的最大的积分和
     * 比如 [5, 5, 5, 5, 6, 7]，在 [3...5] 递归 => p(boxes, 3, 5, 3)
     */
    public int p(int[] boxes, int i, int j, int k, int[][][] dp) {
        if (i > j) {
            return 0;
        }
        if (dp[i][j][k] > 0) {
            return dp[i][j][k];
        }
        // 2 2 2 2 2 2 2 2 2 2 2 3
        //               i       j
        //                     s
        int s = i;
        while (s + 1 <= j && boxes[i] == boxes[s + 1]) {
            ++s;
        }
        // 此时 boxes[i...s] 都是一种盒子，boxes[s + 1] 是另外一种盒子
        // cnt 表示总前缀数量
        int cnt = k + s - i + 1;
        // 可能性一：联合前面的前缀移除 boxes[i]
        int ans = cnt * cnt + p(boxes, s + 1, j, 0, dp);
        // 可能性二：暂时先不移除 boxes[i]，将 boxes[i] 与后面相同的盒子结合一起移除
        // [2, 2, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 4, 2, 2, 2, 2]
        //              i  s     q                          j
        for (int q = s + 2; q <= j; q++) {
            // boxes[q - 1] != boxes[q] 剪枝，避免连续的相同的 boxes[q]
            if (boxes[i] == boxes[q] && boxes[q - 1] != boxes[q]) {
                //                  移除中间的                                结合后面的盒子移除
                ans = Math.max(ans, p(boxes, s + 1, q - 1, 0, dp) + p(boxes, q, j, cnt, dp));
            }
        }
        dp[i][j][k] = ans;
        return ans;
    }
}
