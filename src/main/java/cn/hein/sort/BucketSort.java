package cn.hein.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶排序
 */
public class BucketSort {

    public static void bucketSort(int[] nums, int bucketSize) {
        int max = 0;
        int min = 0;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int bucketCount = (max - min) / bucketSize + 1;
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int num : nums) {
            int bucketIndex = (num - min) / bucketSize;
            buckets.get(bucketIndex).add(num);
        }
        int p = 0;
        for (List<Integer> bucket : buckets) {
            for (int i = 1; i < bucket.size(); i++) {
                int insert = bucket.get(i);
                int j;
                for (j = i - 1; j >= 0 && bucket.get(j) > insert; j--) {
                    bucket.set(j + 1, bucket.get(j));
                }
                bucket.set(j + 1, insert);
            }
            for (int num : bucket) {
                nums[p++] = num;
            }
        }
    }
}
