package cn.hein.algo.difference;

import java.io.*;

/**
 * <a href="https://www.luogu.com.cn/problem/P3397">铺地毯</a>
 *
 * @author hein
 */
public class LayCarpet {

    static int MAX = 1002;
    static int[][] diff = new int[MAX][MAX];

    static int n; // n * n 矩阵
    static int m; // m 块地毯

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            for (int i = 0, a, b, c, d; i < m; i++) {
                in.nextToken();
                a = (int) in.nval;
                in.nextToken();
                b = (int) in.nval;
                in.nextToken();
                c = (int) in.nval;
                in.nextToken();
                d = (int) in.nval;
                incr(a, b, c, d, 1);
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                    out.print(diff[i][j] + " ");
                }
                out.println();
            }
            out.flush();
        }
        out.close();
    }

    private static void incr(int a, int b, int c, int d, int v) {
        diff[a][b] += v;
        diff[c + 1][b] -= v;
        diff[a][d + 1] -= v;
        diff[c + 1][d + 1] += v;
    }
}
