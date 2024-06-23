package cn.hein.structure.skiplist;

/**
 * 跳表
 *
 * @author hein
 */
public class SkipList {

    private static final double SKIP_LIST = 0.5f;
    private static final int MAX_LEVEL = 5;
    private final SkipNode head = new SkipNode(-1);

    public boolean erase(int value) {
        SkipNode[] path = findLessSkipNode(value);
        SkipNode node = path[0].next[0];
        // 没有找到 value 节点
        if (node == null || node.value != value) {
            return false;
        }
        for (int i = 0; i < MAX_LEVEL; i++) {
            if (path[i].next[i] != node) {
                break;
            }
            path[i].next[i] = node.next[i];
        }
        return true;
    }

    public void add(int value) {
        SkipNode[] path = findLessSkipNode(value);
        SkipNode node = new SkipNode(value);
        int level = randomLevel();
        // 修改路径节点的 next 指针以及新增节点的 next 指针
        for (int i = 0; i < level; i++) {
            node.next[i] = path[i].next[i];
            path[i].next[i] = node;
        }
    }

    public boolean search(int value) {
        SkipNode[] path = findLessSkipNode(value);
        SkipNode node = path[0].next[0];
        return node != null && node.value == value;
    }

    private SkipNode[] findLessSkipNode(int value) {
        // 记录查找路径，方便后续增加或删除
        SkipNode[] path = new SkipNode[MAX_LEVEL];
        SkipNode cur = head;
        // 逐层往下
        for (int level = MAX_LEVEL - 1; level >= 0; level--) {
            while (cur.next[level] != null && cur.next[level].value < value) {
                cur = cur.next[level];
            }
            // 记录路径
            path[level] = cur;
        }
        return path;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < SKIP_LIST && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    static class SkipNode {
        // 节点值
        int value;
        // 该节点的所有 next 指针
        SkipNode[] next;

        public SkipNode(int value) {
            this.value = value;
            next = new SkipNode[MAX_LEVEL];
        }
    }
}
