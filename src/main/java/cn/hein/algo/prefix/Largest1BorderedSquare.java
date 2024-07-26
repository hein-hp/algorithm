package cn.hein.algo.prefix;

/**
 * <a href="https://leetcode.cn/problems/largest-1-bordered-square/">最大的以 1 为边界的正方形</a>
 *
 * @author hein
 */
public class Largest1BorderedSquare {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1},
                {1, 1},
        };
        Largest1BorderedSquare t = new Largest1BorderedSquare();
        System.out.println(t.largest1BorderedSquare(grid));
    }

    public int largest1BorderedSquare(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] prefix = new int[n + 1][m + 1];
        build(grid, prefix);
        if (rangeSum(0, 0, n - 1, m - 1, prefix) == 0) {
            return 0;
        }
        int edge = 1;
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                // 枚举边长
                for (int k = edge, c = a + edge - 1, d = b + edge - 1; c < n && d < m; k++, c = a + k - 1, d = b + k - 1) {
                    if (rangeSum(a, b, c, d, prefix) - rangeSum(a + 1, b + 1, c - 1, d - 1, prefix) == (k - 1) * 4) {
                        edge = k;
                    }
                }
            }
        }
        return edge * edge;
    }

    private void build(int[][] grid, int[][] prefix) {
        for (int i = 1; i < prefix.length; i++) {
            for (int j = 1; j < prefix[0].length; j++) {
                prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + grid[i - 1][j - 1];
            }
        }
    }

    private int rangeSum(int a, int b, int c, int d, int[][] prefix) {
        return prefix[c + 1][d + 1] - prefix[c + 1][b] - prefix[a][d + 1] + prefix[a][b];
    }
}
