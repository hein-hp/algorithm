package cn.hein.algo.flood;

/**
 * <a href="https://leetcode.cn/problems/surrounded-regions/description/">被围绕的区域</a>
 *
 * @author hein
 */
public class Solve {

    public static void main(String[] args) {
        char[][] board = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        Solve t = new Solve();
        t.solve(board);
    }

    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public void solve(char[][] board) {
        int n = board.length;
        int m = board[0].length;
        for (int c = 0; c < m; c++) {
            if (board[0][c] == 'O') {
                dfs(board, 0, c);
            }
            if (board[n - 1][c] == 'O') {
                dfs(board, n - 1, c);
            }
        }
        for (int r = 1; r < n - 1; r++) {
            if (board[r][0] == 'O') {
                dfs(board, r, 0);
            }
            if (board[r][m - 1] == 'O') {
                dfs(board, r, m - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    // 以 (i, j) 为起点，将所有的 O 变为 Y
    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'Y';
        for (int[] d : dir) {
            dfs(board, i + d[0], j + d[1]);
        }
    }
}
