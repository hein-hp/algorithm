package cn.hein.sort;

/**
 * 直接插入排序
 */
public class InsertSort {

    public static void insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int insert = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > insert) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = insert;
        }
    }
}
