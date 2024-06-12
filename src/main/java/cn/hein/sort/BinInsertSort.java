package cn.hein.sort;

/**
 * 折半插入排序
 */
public class BinInsertSort {

    public static void binInsertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int insert = nums[i];
            int l = 0, r = i - 1, mid;
            int pos = -1;
            while (l <= r) {
                mid = l + r >> 1;
                if (nums[mid] > insert) {
                    r = mid - 1;
                    pos = mid;
                } else {
                    l = mid + 1;
                }
            }
            if (pos != -1) {
                System.arraycopy(nums, pos, nums, pos + 1, i - pos);
                nums[pos] = insert;
            }
        }
    }
}
