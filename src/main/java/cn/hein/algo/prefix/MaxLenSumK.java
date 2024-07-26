package cn.hein.algo.prefix;

import java.io.*;
import java.util.HashMap;

/**
 * <a href="https://www.nowcoder.com/practice/36fb0fd3c656480c92b569258a1223d5">未排序数组中累加和为给定值的最长子数组长度</a>
 *
 * @author hein
 */
public class MaxLenSumK {

    static int MAXN = 100001;
    static int[] nums = new int[MAXN];

    static int n; // 数组长度
    static int k; // 累加和为 k

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            k = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                nums[i] = (int) in.nval;
            }
            out.println(calc());
        }
        out.flush();
        out.close();
    }

    private static int calc() {
        // 前缀和 -> 前缀和出现的最早位置
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int ans = Integer.MIN_VALUE;
        map.put(0, 0);
        for (int i = 1; i <= n; i++) {
            sum += nums[i];
            // 如果 map 存在前缀和（key）就不 put，为了保证是前缀和出现的最早的位置
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
            if (map.containsKey(sum - k)) {
                ans = Math.max(ans, i - map.get(sum - k));
            }
        }
        return ans;
    }
}
