package cn.hein.dp.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/">从栈中取出 K 个硬币的最大面值和</a>
 */
public class MaxValueOfCoins {

    public static void main(String[] args) {
        List<List<Integer>> piles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            piles.add(new ArrayList<>());
        }
        piles.get(0).addAll(Arrays.asList(2, 5, 200));
        piles.get(1).addAll(Arrays.asList(70, 50));
        piles.get(2).addAll(Arrays.asList(1, 800, 2, 7, 4));
        MaxValueOfCoins t = new MaxValueOfCoins();
        System.out.println("t.maxValueOfCoins(piles, 5) = " + t.maxValueOfCoins(piles, 5));
    }

    // 常数时间不好，但是时间复杂度最优
    // 时间耗在 List 上
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        for (List<Integer> pile : piles) {
            for (int i = 1; i < pile.size(); i++) {
                pile.set(i, pile.get(i) + pile.get(i - 1));
            }
        }
        int n = piles.size();
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = dp[i - 1][j];
                for (int p = 1; p <= piles.get(i - 1).size() && p <= j; p++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - p] + piles.get(i - 1).get(p - 1));
                }
            }
        }
        return dp[n][k];
    }
}
