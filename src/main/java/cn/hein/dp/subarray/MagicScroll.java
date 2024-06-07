package cn.hein.dp.subarray;

/**
 * 魔法卷轴
 * 题意：
 * 给定一个数组 nums，可能包含正数、负数、0，一共有两个魔法卷轴，每个魔法卷轴可以将 nums 中连续的一段全部变为 0
 * 魔法卷轴使不使用、使用多少随意，求出数组尽可能大的累加和
 */
public class MagicScroll {

    public static void main(String[] args) {
        int n = 50;
        int v = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] nums = randomArray(len, v);
            int ans1 = test(nums);
            int ans2 = magicScroll(nums);
            if (ans1 != ans2) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

    public static int magicScroll(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // 情况一: 完全不使用魔法卷轴
        int ans1 = 0;
        for (int num : nums) {
            ans1 += num;
        }
        // 情况二: 使用且仅使用一次魔法卷轴
        int[] dp = new int[n];
        // prefix: 每一步的前缀和, maxPrefix: [0, i - 1] 的最大前缀和
        // 考虑到 nums[0] 可能为负数, 所以 maxPrefix 为 0 与 nums[0] 的较大者
        int prefix = nums[0], maxPrefix = Math.max(0, prefix);
        for (int i = 1; i < n; i++) {
            // i 位置使用/不使用卷轴的最大值
            dp[i] = Math.max(dp[i - 1] + nums[i], maxPrefix);
            // 更新 prefix 和 maxPrefix
            prefix += nums[i];
            maxPrefix = Math.max(maxPrefix, prefix);
        }
        int ans2 = dp[n - 1];
        // 情况三: 使用且仅使用两次魔法卷轴
        int[] sp = new int[n];
        int suffix = nums[n - 1], maxSuffix = Math.max(0, suffix);
        for (int i = n - 2; i >= 0; i--) {
            sp[i] = Math.max(sp[i + 1] + nums[i], maxSuffix);
            suffix += nums[i];
            maxSuffix = Math.max(maxSuffix, suffix);
        }
        int ans3 = sp[0];
        for (int m = 1; m < n; m++) {
            ans3 = Math.max(ans3, dp[m - 1] + sp[m]);
        }
        return Math.max(ans1, Math.max(ans2, ans3));
    }

    // 测试
    public static int test(int[] nums) {
        int p1 = 0;
        for (int num : nums) {
            p1 += num;
        }
        int n = nums.length;
        int p2 = test(nums, 0, n - 1);
        int p3 = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            p3 = Math.max(p3, test(nums, 0, i - 1) + test(nums, i, n - 1));
        }
        return Math.max(p1, Math.max(p2, p3));
    }

    public static int test(int[] nums, int l, int r) {
        int ans = Integer.MIN_VALUE;
        for (int a = l; a <= r; a++) {
            for (int b = a; b <= r; b++) {
                int curAns = 0;
                for (int i = l; i < a; i++) {
                    curAns += nums[i];
                }
                for (int i = b + 1; i <= r; i++) {
                    curAns += nums[i];
                }
                ans = Math.max(ans, curAns);
            }
        }
        return ans;
    }

    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (v * 2 + 1)) - v;
        }
        return ans;
    }
}
