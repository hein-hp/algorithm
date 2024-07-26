package cn.hein.algo.difference;

/**
 * <a href="https://leetcode.cn/problems/stamping-the-grid/">用邮票贴满网格图</a>
 *
 * @author hein
 */
public class PossibleToStamp {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
        };
        PossibleToStamp t = new PossibleToStamp();
        System.out.println(t.possibleToStamp(grid, 4, 3));
    }

    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] prefix = new int[n + 1][m + 1];
        build(grid, prefix);
        int[][] diff = new int[n + 2][m + 2];
        for (int i = 0; i <= n - stampHeight; i++) {
            for (int j = 0; j <= m - stampWidth; j++) {
                if (grid[i][j] == 0 && rangeSum(i, j, i + stampHeight - 1, j + stampWidth - 1, prefix) == 0) {
                    // 可以贴邮票
                    incr(i + 1, j + 1, i + stampHeight, j + stampWidth, diff);
                }
            }
        }
        for (int i = 1; i < diff.length - 1; i++) {
            for (int j = 1; j < diff[0].length - 1; j++) {
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                if (diff[i][j] == 0 && grid[i - 1][j - 1] == 0) {
                    return false;
                }
            }
        }
        return true;
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

    private void incr(int a, int b, int c, int d, int[][] diff) {
        diff[a][b] += 1;
        diff[c + 1][b] -= 1;
        diff[a][d + 1] -= 1;
        diff[c + 1][d + 1] += 1;
    }
}
