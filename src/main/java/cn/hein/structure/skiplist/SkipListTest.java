package cn.hein.structure.skiplist;

/**
 * SkipList Test
 * make sure add vm option "-ea" to enable assert
 *
 * @author hein
 */
public class SkipListTest {

    public static void main(String[] args) {
        // 创建一个跳表实例
        SkipList skipList = new SkipList();

        // 测试添加元素
        skipList.add(1);
        skipList.add(3);
        skipList.add(5);
        skipList.add(7);
        skipList.add(9);

        // 测试搜索存在的元素
        assert skipList.search(1) : "1 should be found in the skip list";
        assert skipList.search(3) : "3 should be found in the skip list";
        assert skipList.search(5) : "5 should be found in the skip list";
        assert skipList.search(7) : "7 should be found in the skip list";
        assert skipList.search(9) : "9 should be found in the skip list";

        // 测试搜索不存在的元素
        assert !skipList.search(2) : "2 should not be found in the skip list";
        assert !skipList.search(4) : "4 should not be found in the skip list";
        assert !skipList.search(6) : "6 should not be found in the skip list";
        assert !skipList.search(8) : "8 should not be found in the skip list";
        assert !skipList.search(10) : "10 should not be found in the skip list";

        // 测试删除存在的元素
        assert skipList.erase(3) : "3 should be deleted from the skip list";
        assert !skipList.search(3) : "3 should not be found in the skip list after deletion";

        // 测试删除不存在的元素
        assert !skipList.erase(4) : "4 should not be deleted from the skip list as it does not exist";

        // 测试边界情况
        skipList.add(Integer.MIN_VALUE);
        skipList.add(Integer.MAX_VALUE);
        assert skipList.search(Integer.MIN_VALUE) : "Integer.MIN_VALUE should be found in the skip list";
        assert skipList.search(Integer.MAX_VALUE) : "Integer.MAX_VALUE should be found in the skip list";
        assert skipList.erase(Integer.MIN_VALUE) : "Integer.MIN_VALUE should be deleted from the skip list";
        assert skipList.erase(Integer.MAX_VALUE) : "Integer.MAX_VALUE should be deleted from the skip list";

        // 打印测试结果
        System.out.println("All tests passed!");
    }
}
