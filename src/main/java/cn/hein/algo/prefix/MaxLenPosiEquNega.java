package cn.hein.algo.prefix;

import java.io.*;
import java.util.HashMap;


/**
 * <a href="https://www.nowcoder.com/practice/545544c060804eceaed0bb84fcd992fb">未排序数组中正数与负数个数相等的最长子数组长度</a>
 *
 * @author hein
 */
public class MaxLenPosiEquNega {

    static int MAXN = 100001;
    static int[] nums = new int[MAXN];

    static int n;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                int val = (int) in.nval;
                nums[i] = Integer.compare(val, 0);
            }
            out.println(calc());
        }
        out.flush();
        out.close();
    }

    private static int calc() {
        HashMap<Integer, Integer> map = new HashMap<>();
        int ans = Integer.MIN_VALUE;
        int sum = 0;
        map.put(0, 0);
        for (int i = 1; i <= n; i++) {
            sum += nums[i];
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            } else {
                ans = Math.max(ans, i - map.get(sum));
            }
        }
        return ans;
    }
}
