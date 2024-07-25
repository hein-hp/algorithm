package cn.hein.algo.flood;

/**
 * <a href="https://leetcode.cn/problems/making-a-large-island/">最大人工岛</a>
 *
 * @author hein
 */
public class LargestIsland {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 1, 0, 0, 1, 1, 1, 0},
                {1, 1, 0, 0, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0}
        };
        LargestIsland t = new LargestIsland();
        System.out.println(t.largestIsland(grid));
    }

    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j, ++num);
                }
            }
        }
        int[] cnt = new int[num + 1];
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] > 1) {
                    max = Math.max(max, ++cnt[grid[i][j]]);
                }
            }
        }
        boolean[] visited = new boolean[num + 1];
        int u, d, l, r;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    u = i - 1 >= 0 ? grid[i - 1][j] : 0;
                    d = i + 1 < n ? grid[i + 1][j] : 0;
                    l = j - 1 >= 0 ? grid[i][j - 1] : 0;
                    r = j + 1 < m ? grid[i][j + 1] : 0;
                    int sum = 1;
                    if (!visited[u]) {
                        sum += cnt[u];
                        visited[u] = true;
                    }
                    if (!visited[d]) {
                        sum += cnt[d];
                        visited[d] = true;
                    }
                    if (!visited[l]) {
                        sum += cnt[l];
                        visited[l] = true;
                    }
                    if (!visited[r]) {
                        sum += cnt[r];
                        visited[r] = true;
                    }
                    max = Math.max(max, sum);
                    visited[u] = false;
                    visited[d] = false;
                    visited[l] = false;
                    visited[r] = false;
                }
            }
        }
        return max;
    }

    // 以 (i, j) 为起点，将 grid[i][j] = 1 的点染为 num
    private void dfs(int[][] grid, int i, int j, int num) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = num;
        for (int[] d : dir) {
            dfs(grid, i + d[0], j + d[1], num);
        }
    }
}
