package cn.hein.algo.difference;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/corporate-flight-bookings/">航班预定统计</a>
 *
 * @author hein
 */
public class CorpFlightBookings {

    static int MAXN = 20002;
    static int[] diff = new int[MAXN];

    public static void main(String[] args) {
        CorpFlightBookings t = new CorpFlightBookings();
        int[][] bookings = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        System.out.println(Arrays.toString(t.corpFlightBookings(bookings, 5)));
    }

    public int[] corpFlightBookings(int[][] bookings, int n) {
        Arrays.fill(diff, 0);
        for (int[] booking : bookings) {
            diff[booking[0]] += booking[2];
            diff[booking[1] + 1] -= booking[2];
        }
        for (int i = 1; i <= n; i++) {
            diff[i] += diff[i - 1];
        }
        int[] ans = new int[n];
        System.arraycopy(diff, 1, ans, 0, n);
        return ans;
    }
}
