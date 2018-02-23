package dynamicprogramming;

import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2018-02-14
 * 0-1背包问题（算法导论，练习16.2-2）
 */
public class KnapsackProblem {

    /**
     * 求解0-1背包问题，自底向上的动态规划
     * 打印可装入背包的最大价值
     *
     * @param values      各个物品的价值，与下面的重量对应
     * @param weights     各个物品的重量，与上面的价值对应
     * @param bagCapacity 背包可容纳的总重量
     */
    public static void knapsack(Integer[] values, Integer[] weights, Integer bagCapacity) {
        int n = values.length;//物品个数
        if (weights.length != n || bagCapacity <= 0) {
            throw new IllegalArgumentException("输入参数错误");
        }
        for (int i = 0; i < n; i++) {
            if (values[i] <= 0 || weights[i] <= 0) {
                throw new IllegalArgumentException("输入参数错误");
            }
        }
        Integer[][] maxValues = new Integer[n + 1][bagCapacity + 1];
        //maxValues[i][j]：当背包容量是j时，前i个物品的最大价值，i,j可以为0；
        //原问题是maxValues[n][bagCapacity]
        for (int i = 0; i < n + 1; i++) {
            maxValues[i][0] = 0;
        }
        for (int j = 0; j < bagCapacity + 1; j++) {
            maxValues[0][j] = 0;
        }
        Boolean[][] bag = new Boolean[n + 1][bagCapacity + 1];
        //bag[i][j]：当背包容量是j时，物品i是否装入
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < bagCapacity + 1; j++) {
                if (j < weights[i - 1]) {
                    maxValues[i][j] = maxValues[i - 1][j];
                    bag[i][j] = false;
                } else {
                    Integer tmp = maxValues[i - 1][j - weights[i - 1]] + values[i - 1];
                    if (maxValues[i - 1][j] > tmp) {
                        maxValues[i][j] = maxValues[i - 1][j];
                        bag[i][j] = false;
                    } else {
                        maxValues[i][j] = tmp;
                        bag[i][j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < bag.length; i++) {
            for (int j = 0; j < bag[0].length; j++) {
                System.out.print(bag[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("最大价值：" + maxValues[n][bagCapacity]);
        System.out.print("装入的物品：");
        printBag(bag, weights, n, bagCapacity);
    }

    private static void printBag(Boolean[][] bag, Integer[] weights, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        while (!bag[i--][j]) {
        }
        printBag(bag, weights, i, j - weights[i]);
        System.out.print(i + 1 + " ");
    }

    public static void main(String[] args) {
        Integer[] values = new Integer[]{6, 3, 5, 4, 6};//物品价值
        Integer[] weights = new Integer[]{2, 2, 6, 5, 4};//物品重量
        Integer bagCapacity = 10;//背包容量
        knapsack(values, weights, bagCapacity);
    }
}
