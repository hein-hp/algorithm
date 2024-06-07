package cn.hein.dp.subarray;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 删掉一个数字后长度为 k 的子数组最大累加和
 * 给定一个数组 nums，要求必须删除一个数字后的新数组中长度为 k 的子数组的最大累加和
 */
public class DeleteOneNumberLengthKMaxSum {

    public static void main(String[] args) {
        int n = 200;
        int v = 2000;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = randomArray(len, v);
            int k = (int) (Math.random() * n) + 1;
            int ans1 = test(nums, k);
            int ans2 = deleteOneNumber(nums, k);
            if (ans1 != ans2) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

    public static int deleteOneNumber(int[] nums, int k) {
        int n = nums.length;
        if (n <= k) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int sum = 0;
        // 单调队列
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (i >= k + 1) {
                max = Math.max(max, sum - q.peekFirst());
                if (q.peekFirst() == nums[i - k - 1]) {
                    q.pollFirst();
                }
                sum -= nums[i - k - 1];
            }
            sum += nums[i];
            while (!q.isEmpty() && q.peekLast() > nums[i]) {
                q.pollLast();
            }
            q.offerLast(nums[i]);
        }
        if (!q.isEmpty()) {
            max = Math.max(max, sum - q.peekFirst());
        }
        return max;
    }

    public static int test(int[] nums, int k) {
        int n = nums.length;
        if (n <= k) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int[] rest = delete(nums, i);
            ans = Math.max(ans, lenKmaxSum(rest, k));
        }
        return ans;
    }

    public static int[] delete(int[] nums, int index) {
        int len = nums.length - 1;
        int[] ans = new int[len];
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (j != index) {
                ans[i++] = nums[j];
            }
        }
        return ans;
    }

    public static int lenKmaxSum(int[] nums, int k) {
        int n = nums.length;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i <= n - k; i++) {
            int cur = 0;
            for (int j = i, cnt = 0; cnt < k; j++, cnt++) {
                cur += nums[j];
            }
            ans = Math.max(ans, cur);
        }
        return ans;
    }

    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (2 * v + 1)) - v;
        }
        return ans;
    }
}
