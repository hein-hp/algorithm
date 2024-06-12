package cn.hein.sort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 快速排序
 */
public class QuickSort {

    public static void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private static void quickSort(int[] nums, int le, int ri) {
        if (le >= ri) {
            return;
        }
        int pivot = partition(nums, le, ri);
        // 排序左边
        quickSort(nums, le, pivot - 1);
        // 排序右边
        quickSort(nums, pivot + 1, ri);
    }

    /**
     * 返回 pivot 的索引
     */
    private static int partition(int[] nums, int le, int ri) {
        // 随机 pivot 基准元素
        swap(nums, ThreadLocalRandom.current().nextInt(ri - le + 1) + le, ri);
        int pivot = nums[ri];
        int low = le, high = ri;
        while (low < high) {
            // 找到 ≥ pivot 的元素
            while (low < high && nums[low] < pivot) {
                low++;
            }
            // 找到 < pivot 的元素
            while (low < high && nums[high] >= pivot) {
                high--;
            }
            // 交换
            swap(nums, low, high);
        }
        // low 和 high 相遇, 将 pivot 和 nums[low] 或 nums[high] 交换
        swap(nums, high, ri);
        return high;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
