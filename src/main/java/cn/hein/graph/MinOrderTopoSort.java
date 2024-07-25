package cn.hein.graph;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <a href="https://www.luogu.com.cn/problem/U107394">字典序最小的拓扑排序</a>
 *
 * @author hein
 */
public class MinOrderTopoSort {

    static int MAXV = 100001;
    static int MAXE = 100001;

    static int n;
    static int m;

    static int cnt;
    static int[] head = new int[MAXV];
    // edges[i][0]: to
    // edges[i][1]: next
    static int[][] edges = new int[MAXE][2];

    static int[] res = new int[MAXV];
    static int[] indegree = new int[MAXV];

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(System.out)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;
            build();
            for (int i = 0, from, to; i < m; i++) {
                in.nextToken();
                from = (int) in.nval;
                in.nextToken();
                to = (int) in.nval;
                addEdge(from, to);
                indegree[to]++;
            }
            if (!topoSort()) {
                out.println(-1);
            } else {
                for (int i = 0; i < n - 1; i++) {
                    out.print(res[i] + " ");
                }
                out.print(res[n - 1]);
            }
        }
        out.flush();
        out.close();
    }

    private static boolean topoSort() {
        PriorityQueue<Integer> minQ = new PriorityQueue<>();
        // 将所有入度为 0 的节点入队
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                minQ.offer(i);
            }
        }
        int p = 0;
        while (!minQ.isEmpty()) {
            // poll 就是当前顶点
            int poll = minQ.poll();
            // 记录 拓扑序
            res[p++] = poll;
            // 遍历邻居节点
            for (int ei = head[poll]; ei > 0; ei = edges[ei][1]) {
                // 邻居节点
                int to = edges[ei][0];
                // 邻居节点的入度--
                // 若邻居节点的入度为 0 则入队
                if (--indegree[to] == 0) {
                    minQ.offer(to);
                }
            }
        }
        return p == n;
    }

    private static void build() {
        cnt = 1;
        Arrays.fill(head, 0, n + 1, 0);
    }

    private static void addEdge(int f, int t) {
        edges[cnt][1] = head[f];
        edges[cnt][0] = t;
        head[f] = cnt++;
    }
}
