package cn.hein.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 基数排序
 */
public class RadixSort {

    public static void radixSort(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int bit = 0;
        while (max > 0) {
            max /= 10;
            bit++;
        }
        for (int i = 0; i < bit; i++) {
            List<List<Integer>> buckets = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                buckets.add(new ArrayList<>());
            }
            for (int num : nums) {
                buckets.get((int) (num / Math.pow(10, i) % 10)).add(num);
            }
            int k = 0;
            for (List<Integer> bucket : buckets) {
                for (int num : bucket) {
                    nums[k++] = num;
                }
            }
        }
    }
}
