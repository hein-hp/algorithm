package cn.hein.algo.flood;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a href='https://leetcode.cn/problems/number-of-islands/'>岛屿问题</a>
 *
 * @author hein
 */
public class NumIslands {

    public static void main(String[] args) {
        String[] input = new String[]{
                "11111011111111101011",
                "01111111111110111110",
                "10111001101111111111",
                "11110111111111111111",
                "10011111111111111111",
                "10111111111110111011",
                "01111111111110110111",
                "11111111111110111111",
                "11111111111011111111",
                "11111111111111111111",
                "01111111011111111111",
                "11111111111111111111",
                "11111111111111111111",
                "11111011111111011111",
                "10111110111011110111",
                "11111111111110111110",
                "11111111111111111000",
                "11111111111111111111",
                "11111111111111111111",
                "11111111111111111111"
        };
        char[][] grid = new char[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length(); j++) {
                grid[i][j] = input[i].charAt(j);
            }
        }
        NumIslands t = new NumIslands();
        System.out.println(t.numIslands3(grid));
    }

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int numIslands1(char[][] grid) {
        int cnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    ++cnt;
                    dfs(grid, i, j);
                }
            }
        }
        return cnt;
    }

    private void dfs(char[][] grid, int r, int c) {
        if (!inArea(grid, r, c) || grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '2'; // 已经访问过
        for (int[] direction : directions) {
            dfs(grid, r + direction[0], c + direction[1]);
        }
    }

    private boolean inArea(char[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }

    // ================= 广度优先搜索 =================
    public int numIslands2(char[][] grid) {
        int cnt = 0;
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    ++cnt;
                    Queue<Integer> q = new LinkedList<>();
                    q.offer(i * m + j);
                    while (!q.isEmpty()) {
                        int rc = q.poll();
                        int r = rc / m;
                        int c = rc % m;
                        for (int[] direction : directions) {
                            int nr = r + direction[0];
                            int nc = c + direction[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == '1') {
                                q.offer(nr * m + nc);
                                grid[nr][nc] = '2'; // 访问过
                            }
                        }
                    }
                }
            }
        }
        return cnt;
    }


    // ================= 并查集 =================
    public static int MAXN = 300 * 300;
    public static int[] fa = new int[MAXN];
    public static int sets = 0;

    public int numIslands3(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        build(grid, n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    if (i > 0 && grid[i - 1][j] == '1') {
                        union(i * m + j, (i - 1) * m + j);
                    }
                    if (j > 0 && grid[i][j - 1] == '1') {
                        union(i * m + j, i * m + j - 1);
                    }
                }
            }
        }
        return sets;
    }

    public void build(char[][] grid, int n, int m) {
        sets = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    fa[i * m + j] = i * m + j;
                    sets++;
                }
            }
        }
    }

    public int find(int x) {
        return x == fa[x] ? x : (fa[x] = find(fa[x]));
    }

    public void union(int x, int y) {
        fa[find(x)] = find(y);
        --sets;
    }
}
