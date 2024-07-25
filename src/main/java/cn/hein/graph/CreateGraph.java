package cn.hein.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 建图
 *
 * @author hein
 */
public class CreateGraph {

    /**
     * 邻接矩阵建图
     */
    static class Graph1 {

        // 最大顶点数
        static int MAXV = 11;
        // 邻接矩阵
        static int[][] graph = new int[MAXV + 1][MAXV + 1];

        static int n; // 实际顶点数

        public static void directGraph(int n, int[][] edges) {
            build(n);
            for (int[] edge : edges) {
                graph[edge[0]][edge[1]] = edge[2];
            }
        }

        public static void print() {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    System.out.print((graph[i][j] == Integer.MAX_VALUE ? "∞" : graph[i][j]) + " ");
                }
                System.out.println();
            }
        }

        private static void build(int m) {
            n = m;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        public static void main(String[] args) {
            int n = 4;
            int[][] edges = {{1, 2, 5}, {2, 3, 14}, {1, 3, 30}, {2, 4, 26}};
            directGraph(n, edges);
            print();
        }
    }

    /**
     * 邻接表建图
     */
    static class Graph2 {

        // 动态建图
        static List<List<int[]>> graph = new ArrayList<>();

        public static void directGraph(int n, int[][] edges) {
            graph.clear();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int[] edge : edges) {
                // edge[0]: from 点
                // edge[1]: to 点
                // edge[2]: weight
                graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
            }
        }

        public static void print() {
            for (int i = 0; i < graph.size(); i++) {
                System.out.printf("[%d] -> ", i);
                for (int[] arr : graph.get(i)) {
                    System.out.printf("(%d, %d) -> ", arr[0], arr[1]);
                }
                System.out.println();
            }
        }

        public static void main(String[] args) {
            int n = 6;
            int[][] edges = {{1, 2, 5}, {1, 5, 6}, {3, 1, 2}, {2, 4, 7}, {4, 3, 9}, {6, 4, 3}, {5, 6, 8}};
            directGraph(n, edges);
            print();
        }
    }

    /**
     * 链式前向星建图
     */
    static class Graph3 {

        // 最大顶点数
        static int MAXV = 11;
        // 最大边数
        static int MAXE = 21;
        // head 数组
        static int[] head = new int[MAXV];
        // edges[i][0]: to
        // edges[i][1]: weight
        // edges[i][2]: next
        static int[][] edges = new int[MAXE][3];

        static int cnt; // 边计数
        static int n; // 实际顶点数
        static int m; // 实际边数

        public static void directGraph(int n, int[][] es) {
            build(n, es.length);
            for (int[] edge : es) {
                addEdge(edge[0], edge[1], edge[2]);
            }
        }

        public static void print() {
            for (int i = 1; i <= n; i++) {
                System.out.printf("[%d] -> ", i);
                // ei 表示边号
                // head[i] 就是顶点 i 的第一条边号
                // ei = edges[ei][2], 不断将 next 赋值给 ei，进行循环
                for (int ei = head[i]; ei > 0; ei = edges[ei][2]) {
                    System.out.printf("(%d, %d) -> ", edges[ei][0], edges[ei][1]);
                }
                System.out.println();
            }
        }

        private static void addEdge(int from, int to, int weight) {
            edges[cnt][2] = head[from];
            edges[cnt][1] = weight;
            edges[cnt][0] = to;
            head[from] = cnt++;
        }

        private static void build(int p, int q) {
            n = p;
            m = q;
            // 链式前向星清空
            cnt = 1;
            Arrays.fill(head, 1, n + 1, 0);
        }

        public static void main(String[] args) {
            int n = 6;
            int[][] edges = {{1, 2, 5}, {1, 5, 6}, {2, 4, 7}, {4, 3, 9}, {1, 3, 2}, {5, 6, 8}, {6, 4, 3}};
            directGraph(n, edges);
            print();
        }
    }
}