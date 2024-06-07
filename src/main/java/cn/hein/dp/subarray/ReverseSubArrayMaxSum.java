package cn.hein.dp.subarray;

/**
 * 翻转子数组后的最大累加和
 * 题意：
 * 给定一个整数数组 nums，包含正数、负数、0，允许选择任一连续的一段进行翻转（逆序），返回能且仅能翻转一次之后，子数组最大累加和
 * 比如翻转 [1, 2, 3, 4] 的 [1, 2] 范围，得到 [1, 3, 2, 4]
 */
public class ReverseSubArrayMaxSum {

    public static void main(String[] args) {
        int n = 50;
        int v = 200;
        int testTime = 20000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] arr = randomArray(len, v);
            int ans1 = test(arr);
            int ans2 = reverseSubArrayMaxSum(arr);
            if (ans1 != ans2) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

    public static int reverseSubArrayMaxSum(int[] nums) {
        int n = nums.length;
        int[] start = new int[n];
        start[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            start[i] = Math.max(nums[i], nums[i] + start[i + 1]);
        }
        int max = start[0];
        int end = nums[0], maxEnd = end;
        for (int i = 1; i < n; i++) {
            max = Math.max(max, start[i] + maxEnd);
            end = Math.max(nums[i], end + nums[i]);
            maxEnd = Math.max(maxEnd, end);
        }
        max = Math.max(max, maxEnd);
        return max;
    }

    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (v * 2 + 1)) - v;
        }
        return ans;
    }

    public static int test(int[] nums) {
        int ans = Integer.MIN_VALUE;
        for (int l = 0; l < nums.length; l++) {
            for (int r = l; r < nums.length; r++) {
                reverse(nums, l, r);
                ans = Math.max(ans, maxSum(nums));
                reverse(nums, l, r);
            }
        }
        return ans;
    }

    public static void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int tmp = nums[l];
            nums[l++] = nums[r];
            nums[r--] = tmp;
        }
    }

    public static int maxSum(int[] nums) {
        int n = nums.length;
        int ans = nums[0];
        for (int i = 1, pre = nums[0]; i < n; i++) {
            pre = Math.max(nums[i], pre + nums[i]);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
