package cn.hein.dp.subseq;

/**
 * <a href="https://leetcode.cn/problems/minimum-operations-to-make-the-array-k-increasing/">使数组 K 递增的最少操作次数</a>
 */
public class KIncreasing {

    static int[] nums = new int[100001];
    static int[] tails = new int[100001];

    /**
     * n * k * log(k)
     */
    public static int kIncreasing(int[] arr, int k) {
        int ans = 0;
        int n = arr.length;
        for (int i = 0, size; i < k; i++) {
            // 当前组的长度
            size = 0;
            for (int j = i; j < n; j += k) {
                nums[size++] = arr[j];
            }
            // 当前组的长度 - 当前组最长不下降子序列长度 = 当前组需要修改的数字个数
            ans += (size - lengthOfLIS(size));
        }
        return ans;
    }

    public static int lengthOfLIS(int size) {
        tails[0] = nums[0];
        int len = 1;
        for (int i = 1, x; i < size; i++) {
            x = binary(len, nums[i]);
            if (x == -1) {
                tails[len++] = nums[i];
            } else {
                tails[x] = nums[i];
            }
        }
        return len;
    }

    public static int binary(int len, int x) {
        int l = 0, r = len - 1, mid;
        int ans = -1;
        while (l <= r) {
            mid = l + r >> 1;
            // 若为求最长递增子序列: x <= tails[mid]
            if (x < tails[mid]) {
                r = mid - 1;
                ans = mid;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
