package cn.hein.algo.flood;

/**
 * <a href="https://leetcode.cn/problems/max-area-of-island/">最大岛屿面积</a>
 *
 * @author hein
 */
public class MaxAreaOfIsland {

    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int maxAreaOfIsland1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j, ++id);
                }
            }
        }
        int max = 0;
        int[] cnt = new int[id + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] > 0) {
                    max = Math.max(max, ++cnt[grid[i][j]]);
                }
            }
        }
        return max;
    }

    private void dfs(int[][] grid, int i, int j, int id) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = id;
        for (int[] d : dir) {
            dfs(grid, i + d[0], j + d[1], id);
        }
    }

    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, dfs(grid, i, j));
            }
        }
        return max;
    }

    private int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 2;
        int cnt = 1;
        for (int[] d : dir) {
            cnt += dfs(grid, i + d[0], j + d[1]);
        }
        return cnt;
    }
}
