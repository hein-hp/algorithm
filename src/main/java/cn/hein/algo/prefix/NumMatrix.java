package cn.hein.algo.prefix;

/**
 * <a href="https://leetcode.cn/problems/range-sum-query-2d-immutable/">二维区域和检索 - 数组不可变</a>
 *
 * @author hein
 */
public class NumMatrix {

    static int MAX = 201;
    static int[][] prefix = new int[MAX][MAX];

    static int n;
    static int m;

    public static void main(String[] args) {
        int[][] matrix = {{1, 2}, {3, 4}};
        NumMatrix t = new NumMatrix(matrix);
        System.out.println(t.sumRegion(0, 0, 1, 1));
    }

    public NumMatrix(int[][] matrix) {
        n = matrix.length;
        m = matrix[0].length;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        row2++;
        col2++;
        return prefix[row2][col2] - prefix[row2][col1] - prefix[row1][col2] + prefix[row1][col1];
    }
}
