package cn.hein.dp.interval;

/**
 * <a href="https://leetcode.cn/problems/minimum-score-triangulation-of-polygon/">多边形三角剖分的最低得分</a>
 *
 * @author hein
 */
public class MinScoreTriangulation {

    public static void main(String[] args) {
        MinScoreTriangulation t = new MinScoreTriangulation();
        System.out.println(t.minScoreTriangulation(new int[]{3, 7, 4, 5}));
    }

    // 画个 dp 表
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(values[i] * values[j] * values[k] + dp[i][k] + dp[k][j], dp[i][j]);
                }
            }
        }
        return dp[0][n - 1];
    }

    public int minScoreTriangulation1(int[] values) {
        return process(values, 0, values.length - 1);
    }

    /**
     * 在 [i...j] 范围上做拆分，得到的最低分
     */
    private int process(int[] values, int i, int j) {
        if (j - i < 2) {
            // 不能拆分三角形
            return 0;
        }
        int score = Integer.MAX_VALUE;
        // 拆分的三角形是 [i,k,j]
        for (int k = i + 1; k < j; k++) {
            score = Math.min(values[i] * values[k] * values[j] +
                    // 继续在 [i...k] 范围做拆分
                    process(values, i, k) +
                    // 继续在 [k...j] 范围做拆分
                    process(values, k, j), score);
        }
        return score;
    }
}
