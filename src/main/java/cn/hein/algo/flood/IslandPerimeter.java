package cn.hein.algo.flood;

/**
 * <a href="https://leetcode.cn/problems/island-perimeter/">岛屿的周长</a>
 *
 * @author hein
 */
public class IslandPerimeter {

    public static void main(String[] args) {
        int[][] grid = {
                {1}
        };
        IslandPerimeter t = new IslandPerimeter();
        System.out.println(t.islandPerimeter(grid));
    }

    public int islandPerimeter(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = neighbor(grid, i, j);
                }
            }
        }
        int perimeter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] > 0) {
                    perimeter += (grid[i][j] == 5) ? 4 : (4 - grid[i][j]);
                }
            }
        }
        return perimeter;
    }

    public int neighbor(int[][] grid, int i, int j) {
        int n = grid.length;
        int m = grid[0].length;
        int cnt = 0;
        cnt += i - 1 >= 0 && grid[i - 1][j] != 0 ? 1 : 0;
        cnt += i + 1 < n && grid[i + 1][j] != 0 ? 1 : 0;
        cnt += j - 1 >= 0 && grid[i][j - 1] != 0 ? 1 : 0;
        cnt += j + 1 < m && grid[i][j + 1] != 0 ? 1 : 0;
        // 本身是岛但是周围没有岛则返回 -1
        return cnt == 0 ? 5 : cnt;
    }
}
