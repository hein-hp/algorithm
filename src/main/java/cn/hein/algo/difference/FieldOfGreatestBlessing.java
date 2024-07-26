package cn.hein.algo.difference;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/xepqZ5/">最强祝福力场</a>
 *
 * @author hein
 */
public class FieldOfGreatestBlessing {

    public static void main(String[] args) {
        FieldOfGreatestBlessing t = new FieldOfGreatestBlessing();
        int[][] forceField = {
                {0, 0, 1},
                {1, 0, 1}
        };
        System.out.println(t.fieldOfGreatestBlessing(forceField));
    }

    public int fieldOfGreatestBlessing(int[][] forceField) {
        int n = forceField.length;
        // 点的 x、y 坐标，开 2 倍是因为一个力场叠加需要左下坐标和右上坐标
        long[] x = new long[n << 1];
        long[] y = new long[n << 1];
        for (int p = 0; p < n; p++) {
            long i = forceField[p][0];
            long j = forceField[p][1];
            long r = forceField[p][2];
            // 计算左下坐标
            x[2 * p] = (i << 1) - r;
            y[2 * p] = (j << 1) - r;
            // 计算右上坐标
            x[2 * p + 1] = (i << 1) + r;
            y[2 * p + 1] = (j << 1) + r;
        }
        int sizex = sortAndDistinct(x);
        int sizey = sortAndDistinct(y);
        int[][] diff = new int[sizex + 2][sizey + 2];
        for (int[] point : forceField) {
            long i = point[0];
            long j = point[1];
            long r = point[2];
            incr(rank(x, (i << 1) - r, sizex), rank(y, (j << 1) - r, sizey),
                    rank(x, (i << 1) + r, sizex), rank(y, (j << 1) + r, sizey), diff);
        }
        int ans = 0;
        for (int i = 1; i < sizex + 1; i++) {
            for (int j = 1; j < sizey + 1; j++) {
                diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
                ans = Math.max(ans, diff[i][j]);
            }
        }
        return ans;
    }

    public int rank(long[] nums, long num, int size) {
        int l = 0, r = size - 1, mid;
        int ans = 0;
        while (l <= r) {
            mid = l + r >> 1;
            if (nums[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans + 1;
    }

    public int sortAndDistinct(long[] nums) {
        Arrays.sort(nums);
        int r = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[r - 1] != nums[i]) {
                nums[r++] = nums[i];
            }
        }
        return r;
    }

    public void incr(int a, int b, int c, int d, int[][] diff) {
        diff[a][b] += 1;
        diff[c + 1][b] -= 1;
        diff[a][d + 1] -= 1;
        diff[c + 1][d + 1] += 1;
    }
}
