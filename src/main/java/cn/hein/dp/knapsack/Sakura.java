package cn.hein.dp.knapsack;

import java.io.*;
import java.util.Arrays;

/**
 * <a href="https://www.luogu.com.cn/problem/P1833">樱花</a>
 *
 * @author hein
 */
public class Sakura {

    static int MAXT = 1001;
    static int MAXN = 100001;

    static int[] w = new int[MAXN];
    static int[] v = new int[MAXN];
    static int[] dp = new int[MAXN];

    static int n;
    static int t;
    static int m;

    static int startHour, startMinute, endHour, endMinute;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            startHour = (int) in.nval;
            // 跳过冒号
            in.nextToken();
            in.nextToken();
            startMinute = (int) in.nval;
            in.nextToken();
            endHour = (int) in.nval;
            in.nextToken();
            in.nextToken();
            endMinute = (int) in.nval;
            if (endMinute < startMinute) {
                endMinute += 60;
                endHour--;
            }
            t = (endHour - startHour) * 60 + (endMinute - startMinute);
            in.nextToken();
            n = (int) in.nval;
            m = 1;
            // 最多看的樱花的次数不超过 1001
            for (int i = 0, weight, value, count; i < n; i++) {
                in.nextToken();
                weight = (int) in.nval;
                in.nextToken();
                value = (int) in.nval;
                in.nextToken();
                count = (int) in.nval;
                count = count == 0 ? MAXT : count;
                for (int k = 1; k <= count; k <<= 1) {
                    w[m] = k * weight;
                    v[m++] = k * value;
                    count -= k;
                }
                if (count > 0) {
                    w[m] = count * weight;
                    v[m++] = count * value;
                }
            }
            out.println(compute());
        }
        out.flush();
        out.close();
    }

    private static int compute() {
        Arrays.fill(dp, 0, t + 1, 0);
        for (int i = 1; i <= m; i++) {
            for (int j = t; j >= w[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[t];
    }
}
