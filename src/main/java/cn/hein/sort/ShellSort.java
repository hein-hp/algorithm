package cn.hein.sort;

/**
 * 希尔排序
 */
public class ShellSort {

    public static void shellSort(int[] nums) {
        int n = nums.length;
        for (int d = n / 2; d >= 1; d /= 2) {
            // i 是每个分组的未排序区域的左边界
            for (int i = d; i < n; i++) {
                int insert = nums[i];
                // j 是每个分组的排序区间的右边界
                int j = i - d;
                while (j >= 0 && nums[j] > insert) {
                    nums[j + d] = nums[j];
                    j -= d;
                }
                // j 发生变化表示需要将 insert 插入到合适的位置
                if (j != i - d) {
                    nums[j + d] = insert;
                }
            }
        }
    }
}
