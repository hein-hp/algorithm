package cn.hein.sort;

/**
 * 计数排序
 */
public class CountSort {

    public static int[] countSort(int[] nums) {
        int min = 0;
        int max = 0;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int[] count = new int[max - min + 1];
        for (int num : nums) {
            count[num - min]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        int[] tmp = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            int rank = count[nums[i] - min];
            tmp[rank - 1] = nums[i];
            count[nums[i] - min]--;
        }
        return tmp;
    }
}
