package cn.hein.sort;

/**
 * 堆排序
 */
public class HeapSort {

    public static void heapSort(int[] nums) {
        // 初始化堆
        heapify(nums);
        for (int ri = nums.length - 1; ri > 0; ri--) {
            swap(nums, 0, ri);
            sink(nums, 0, ri);
        }
    }

    private static void sink(int[] nums, int p, int size) {
        int lc, rc;
        int max = p;
        while ((lc = p << 1 | 1) < size) {
            rc = lc + 1;
            max = nums[max] < nums[lc] ? lc : max;
            max = (rc < size && nums[max] < nums[rc]) ? rc : max;
            if (p == max) {
                return;
            }
            swap(nums, max, p);
            p = max;
        }
    }

    private static void heapify(int[] nums) {
        int n = nums.length;
        for (int i = (n >> 1) - 1; i >= 0; i--) {
            sink(nums, i, n);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
