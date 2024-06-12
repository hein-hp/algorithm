package cn.hein.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void bubbleSort(int[] nums) {
        for (int i = nums.length; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
