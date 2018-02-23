package divideandconquer;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-12-26 10:57<p>
 * 求解一个最大子数组的算法，给出暴力算法和分治算法，然后比较<p>
 * 比较结果：<p>
 * 实验表面采用递归实现的分治算法并不比暴力算法快多少，无论输入规模是多大<p>
 * 可能是由于递归实现开销大<p>
 */

public class MaximumSubarray {

    /**
     * 一个用于输出的内部类
     */
    private static class Output {
        int leftPosition;
        int rightPosition;
        long sum;

        public Output(int leftPosition, int rightPosition, long sum) {
            this.leftPosition = leftPosition;
            this.rightPosition = rightPosition;
            this.sum = sum;
        }
    }

    /**
     * 暴力算法
     *
     * @param array 输入数组
     * @return 一个最大子数组在原数组中的起始下标及其和
     */
    public static Output bruteForce(int[] array) {
        int leftPosition = 0;
        int rightPosition = 0;
        long sum = array[0];

        for (int i = 0; i < array.length; i++) {
            long sumI = 0;
            for (int j = i; j < array.length; j++) {
                sumI += array[j];
                if (sum < sumI) {
                    sum = sumI;
                    leftPosition = i;
                    rightPosition = j;
                }
            }
        }

        return new Output(leftPosition, rightPosition, sum);
    }

    /**
     * 分治算法
     *
     * @param array 输入数组
     * @return 一个最大子数组在原数组中的起始下标及其和
     */
    public static Output divideAndConquer(int[] array) {
        return divideAndConquer(array, 0, array.length - 1);
    }

    private static Output divideAndConquer(int[] array, int left, int right) {
        if (left == right) {
            return new Output(left, right, array[left]);
        } else {
            int medium = (right - left) / 2 + left; //注意这个递归中点
            int leftPosition = medium;
            int rightPosition = medium + 1;
            long sumLeft = array[medium];
            long sumRight = array[medium + 1];
            long sunLeftTemp = array[medium];
            long sunRightTemp = array[medium + 1];
            long sum;

            //分别计算左右两边的子数组的最大和
            Output outputLeft = divideAndConquer(array, left, medium);
            Output outputRight = divideAndConquer(array, medium + 1, right);

            //计算连接两部分的子数组的最大和
            for (int i = medium - 1; i >= 0; i--) {
                sunLeftTemp += array[i];
                if (sumLeft < sunLeftTemp) {
                    sumLeft = sunLeftTemp;
                    leftPosition = i;
                }
            }
            for (int i = medium + 2; i <= right; i++) {
                sunRightTemp += array[i];
                if (sumRight < sunRightTemp) {
                    sumRight = sunRightTemp;
                    rightPosition = i;
                }
            }
            sum = sumLeft + sumRight;

            //比较子数组的最大和在哪一部分
            if (outputLeft.sum >= sum && outputLeft.sum >= outputRight.sum) {
                sum = outputLeft.sum;
                leftPosition = outputLeft.leftPosition;
                rightPosition = outputLeft.rightPosition;
            } else if (outputRight.sum >= sum && outputRight.sum >= outputLeft.sum) {
                sum = outputRight.sum;
                leftPosition = outputRight.leftPosition;
                rightPosition = outputRight.rightPosition;
            }

            return new Output(leftPosition, rightPosition, sum);
        }
    }

    /**
     * 比较暴力算法和分治算法
     */
    public static void main(String[] args) {

        int[] a = new int[1000];
        Random random = new Random();
        int max = 20;
        int min = -20;
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(max - min) + min;
        }

        long beginTimeBruteForce = System.nanoTime();
        Output output1 = bruteForce(a);
        long endTimeBruteForce = System.nanoTime();
        System.out.println("暴力算法最大子数组和：" + output1.sum + ", 起始位置：" + output1.leftPosition + ", "
                + output1.rightPosition + ", 用时：" + (endTimeBruteForce - beginTimeBruteForce) * 1.0 / 1e6 + "ms");

        long beginTimeDivideAndConquer = System.nanoTime();
        Output output2 = divideAndConquer(a);
        long endTimeDivideAndConquer = System.nanoTime();
        System.out.println("分治算法最大子数组和：" + output2.sum + ", 起始位置：" + output2.leftPosition + ", "
                + output2.rightPosition + ", 用时：" + (endTimeDivideAndConquer - beginTimeDivideAndConquer) * 1.0 / 1e6 + "ms");
    }

}
