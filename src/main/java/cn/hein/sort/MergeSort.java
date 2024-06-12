package cn.hein.sort;

/**
 * 归并排序
 */
public class MergeSort {

    public static void mergerSort(int[] nums) {
        split(nums, 0, nums.length - 1);
    }

    private static void split(int[] nums, int le, int ri) {
        if (le >= ri) {
            return;
        }
        int mid = le + ri >> 1;
        split(nums, le, mid);
        split(nums, mid + 1, ri);
        merge(nums, le, mid, ri);
    }

    private static void merge(int[] nums, int le, int mid, int ri) {
        int[] temp = new int[ri - le + 1];
        int i = le, j = mid + 1, k = 0;
        while (i <= mid && j <= ri) {
            temp[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
        }
        if (i <= mid) {
            System.arraycopy(nums, i, temp, k, mid - i + 1);
        }
        if (j <= ri) {
            System.arraycopy(nums, j, temp, k, ri - j + 1);
        }
        System.arraycopy(temp, 0, nums, le, temp.length);
    }
}
