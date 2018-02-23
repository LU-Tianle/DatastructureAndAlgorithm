package dynamicprogramming;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-02 9:30<p>
 * 钢条切割问题（算法导论15.1），给出三种算法并比较：<p>
 * 1.自顶向下（不使用动态规划，递归）<p>
 * 2.带备忘的自顶向下（动态规划，递归）<p>
 * 3.自底向上（动态规划，不递归），并且给出最优切割方案<p>
 */
public class RodCutting {
    Integer[] PRICE;

    /**
     * 自顶向下（不使用动态规划，递归）
     *
     * @param n 长度为n的钢条
     * @return 最优切割后的总价格
     */
    public static Integer topDownCutRod(Integer[] PRICE, int n) {
        if (n < 0 || n > PRICE.length) {
            throw new IllegalArgumentException("输入的钢条长度必须是非负整数且小于等于" + PRICE.length);
        } else if (n == 0) {
            return 0;
        } else if (n == 1) {
            return PRICE[0];
        }
        Integer maxValue = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            maxValue = Integer.max(maxValue, PRICE[i - 1] + topDownCutRod(PRICE, n - i));
        }
        return maxValue;
    }

    /**
     * 带备忘的自顶向下（动态规划，递归）
     *
     * @param n 长度为n的钢条
     * @return 最优切割后的总价格
     */
    public static Integer memoizedTopDownCutRod(Integer[] PRICE, int n) {
        if (n < 0 || n > PRICE.length) {
            throw new IllegalArgumentException("输入的钢条长度必须是非负整数且小于等于" + PRICE.length);
        } else if (n == 0) {
            return 0;
        } else if (n == 1) {
            return PRICE[0];
        }
        Integer[] maxValues = new Integer[n];
        Arrays.fill(maxValues, 0);
        maxValues[0] = PRICE[0];
        memoizedTopDownCutRodAuxiliary(PRICE, maxValues, n);
        return maxValues[n - 1];
    }

    private static Integer memoizedTopDownCutRodAuxiliary(Integer[] PRICE, Integer[] maxValues, int n) {
        if (n == 0) {
            return 0;
        }
        if (maxValues[n - 1] > 0) {
            return maxValues[n - 1];
        } else {
            Integer maxValue = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) {
                maxValue = Integer.max(maxValue, PRICE[i - 1] + memoizedTopDownCutRodAuxiliary(PRICE, maxValues, n - i));
            }
            maxValues[n - 1] = maxValue;
            return maxValue;
        }
    }

    /**
     * 自底向上（动态规划，不递归），并且给出最优切割方案
     *
     * @param n 长度为n的钢条
     * @return 最优切割后的总价格
     */
    public static Result bottomUpCutRod(Integer[] PRICE, int n) {
        if (n < 0 || n > PRICE.length) {
            throw new IllegalArgumentException("输入的钢条长度必须是非负整数且小于等于" + PRICE.length);
        } else if (n == 0) {
            return new Result(0, new Integer[]{0});
        } else if (n == 1) {
            return new Result(PRICE[0], new Integer[]{0});
        }
        Integer[] maxValues = new Integer[n];
        Integer[] segmentation = new Integer[n];
        Arrays.fill(maxValues, 0);
        Arrays.fill(segmentation, 0);
        maxValues[0] = PRICE[0];
        for (int j = 1; j < n; j++) {
            Integer maxValue = PRICE[j];
            for (int i = 1; i < j + 1; i++) {
                if (PRICE[i - 1] + maxValues[j - i] > maxValue) {
                    maxValue = PRICE[i - 1] + maxValues[j - i];
                    segmentation[j] = i;
                }
            }
            maxValues[j] = maxValue;
        }
        return new Result(maxValues[n - 1], segmentation);
    }

    private static class Result {
        Integer maxValue;
        Integer[] segmentation;

        public Result(Integer maxValue, Integer[] segmentation) {
            this.maxValue = maxValue;
            this.segmentation = segmentation;
        }
    }

    private static void printSegmentation(Integer[] segmentation, int n) {
        if (n == 0) {
            System.out.print(0);
        }
        while (n > 0) {
            if (segmentation[n - 1] == 0) {
                System.out.print(n + " ");
                return;
            } else {
                System.out.print(segmentation[n - 1] + " ");
                n -= segmentation[n - 1];
            }
        }
    }

    /**
     * 测试三种方法
     */
    public static void main(String[] args) {
        //钢条价格表，分别是长度1-30的价格
        Integer[] PRICE = new Integer[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30, 32, 33, 34, 36, 39, 40, 42, 45, 48, 61, 66, 69, 70, 71, 75, 79, 80, 82, 88, 89};
        int n = 30;//钢条长度

        long beginTimeTopDownCutRod = System.nanoTime();
        Integer resultTopDownCutRod = topDownCutRod(PRICE, n);
        long endTimeTopDownCutRod = System.nanoTime();
        System.out.print("自顶向下不使用动态规划，算法结果：" + resultTopDownCutRod);
        System.out.print(", 用时：" + (endTimeTopDownCutRod - beginTimeTopDownCutRod) * 1.0 / 1e6 + "ms\n");

        long beginTimeMemoizedTopDownCutRod = System.nanoTime();
        Integer resultMemoizedTopDownCutRod = memoizedTopDownCutRod(PRICE, n);
        long endTimeMemoizedTopDownCutRod = System.nanoTime();
        System.out.print("带备忘的自顶向下（动态规划），算法结果：" + resultMemoizedTopDownCutRod);
        System.out.print(", 用时：" + (endTimeMemoizedTopDownCutRod - beginTimeMemoizedTopDownCutRod) * 1.0 / 1e6 + "ms\n");

        long beginTimeBottomUpCutRod = System.nanoTime();
        Result resultBottomUpCutRod = bottomUpCutRod(PRICE, n);
        long endTimeBottomUpCutRod = System.nanoTime();
        System.out.print("带备忘的自顶向下（动态规划），算法结果：" + resultBottomUpCutRod.maxValue);
        System.out.print("分割成长度为：");
        printSegmentation(resultBottomUpCutRod.segmentation, n);
        System.out.print("的钢条");
        System.out.print(", 用时：" + (endTimeBottomUpCutRod - beginTimeBottomUpCutRod) * 1.0 / 1e6 + "ms\n");
    }

}
