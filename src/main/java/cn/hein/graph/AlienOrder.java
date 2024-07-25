package cn.hein.graph;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/Jf1JuT/">火星字典</a>
 *
 * @author hein
 */
public class AlienOrder {

    public static void main(String[] args) {
        String[] words = {"z", "x", "a", "zb", "zx"};
        AlienOrder t = new AlienOrder();
        System.out.println(t.alienOrder(words));
    }

    public String alienOrder(String[] words) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            graph.add(new ArrayList<>());
        }
        int[] indegree = new int[26];
        Arrays.fill(indegree, -1);
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                indegree[word.charAt(i) - 'a'] = 0;
            }
        }
        for (int i = 1; i < words.length; i++) {
            int cnt = Math.min(words[i - 1].length(), words[i].length());
            int p;
            for (p = 0; p < cnt; p++) {
                char c1 = words[i - 1].charAt(p);
                char c2 = words[i].charAt(p);
                if (c1 != c2) {
                    graph.get(c1 - 'a').add(c2 - 'a');
                    indegree[c2 - 'a']++;
                    break;
                }
            }
            if (p < words[i - 1].length() && p == words[i].length()) {
                return "";
            }
        }
        return topoSort(graph, indegree);
    }

    private String topoSort(List<List<Integer>> graph, int[] indegree) {
        StringBuilder builder = new StringBuilder();
        Deque<Integer> q = new ArrayDeque<>();
        int kinds = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (indegree[i] != -1) {
                kinds++;
            }
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            int cur = q.poll();
            builder.append((char) (cur + 'a'));
            for (int n : graph.get(cur)) {
                if (--indegree[n] == 0) {
                    q.offer(n);
                }
            }
        }
        return builder.length() == kinds ? builder.toString() : "";
    }
}
