package dynamicprogramming;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2018-02-02 19:29
 * Description: 矩阵链乘法问题（算法导论15.2，15，3）
 * 采用动态规划的思想，给出带备忘的自顶向下和自底向上两种算法
 */
public class MatrixChainMultiplication {

    /**
     * 自底向上的方法，不递归
     *
     * @param matrixChainSize 矩阵链的大小数组，长度是矩阵个数+1
     */
    public static void matrixChainOrder(Integer[] matrixChainSize) {
        int n = matrixChainSize.length - 1;//矩阵数量
        Integer[][] minMultiplyTimes = new Integer[n][n];//m[i][j]：矩阵链i-j相乘所需的最少乘法次数
        Integer[][] segmentationPoint = new Integer[n][n];//s[i][j]：矩阵链i-j相乘所需的最少乘法的分割点
        for (int i = 0; i < n; i++) {
            minMultiplyTimes[i][i] = 0;//子问题长度为1时，
        }
        for (int l = 2; l < n + 1; l++) {//子问题长度为l时（l个矩阵相乘）
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                minMultiplyTimes[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int tmp = minMultiplyTimes[i][k] + minMultiplyTimes[k + 1][j]
                            + matrixChainSize[i] * matrixChainSize[k + 1] * matrixChainSize[j + 1];
                    if (tmp < minMultiplyTimes[i][j]) {
                        minMultiplyTimes[i][j] = tmp;
                        segmentationPoint[i][j] = k;
                    }
                }
            }
        }
        System.out.println("矩阵链乘法问题，自底向上的方法，不递归");
        System.out.println("最少乘法次数：" + minMultiplyTimes[0][n - 1]);
        System.out.print("括号化方案：");
        printOptimalParens(segmentationPoint, 0, n - 1);
        System.out.println();
    }

    /**
     * 用于输出括号化方案
     *
     * @param segmentationPoint s[i][j]：矩阵链i-j相乘所需的最少乘法的分割点
     */
    private static void printOptimalParens(Integer[][] segmentationPoint, Integer i, Integer j) {
        if (i.equals(j)) {
            System.out.print(i + 1);
        } else {
            System.out.print("(");
            printOptimalParens(segmentationPoint, i, segmentationPoint[i][j]);
            printOptimalParens(segmentationPoint, segmentationPoint[i][j] + 1, j);
            System.out.print(")");
        }
    }

    /**
     * 带备忘的自顶向下算法，递归实现的主程序
     *
     * @param matrixChainSize 矩阵链的大小数组，长度是矩阵个数+1
     */
    public static void memoizedMatrixChain(Integer[] matrixChainSize) {
        int n = matrixChainSize.length - 1;//矩阵数量
        Integer[][] minMultiplyTimes = new Integer[n][n];//m[i][j]：矩阵链i-j相乘所需的最少乘法次数
        Integer[][] segmentationPoint = new Integer[n][n];//s[i][j]：矩阵链i-j相乘所需的最少乘法的分割点
        lookupChain(matrixChainSize, minMultiplyTimes, segmentationPoint, 0, n - 1);
        System.out.println("矩阵链乘法问题，带备忘的自顶向下算法，递归");
        System.out.println("最少乘法次数：" + minMultiplyTimes[0][n - 1]);
        System.out.print("括号化方案：");
        printOptimalParens(segmentationPoint, 0, n - 1);
        System.out.println();
    }

    /**
     * 带备忘的自顶向下算法，递归部分
     */
    private static Integer lookupChain(Integer[] matrixChainSize, Integer[][] minMultiplyTimes, Integer[][] segmentationPoint, int i, int j) {
        if (i == j) {
            minMultiplyTimes[i][j] = 0;
        } else if (minMultiplyTimes[i][j] == null) {
            minMultiplyTimes[i][j] = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                int tmp = lookupChain(matrixChainSize, minMultiplyTimes, segmentationPoint, i, k)
                        + lookupChain(matrixChainSize, minMultiplyTimes, segmentationPoint, k + 1, j)
                        + matrixChainSize[i] * matrixChainSize[k + 1] * matrixChainSize[j + 1];
                if (tmp < minMultiplyTimes[i][j]) {
                    minMultiplyTimes[i][j] = tmp;
                    segmentationPoint[i][j] = k;
                }
            }
        }
        return minMultiplyTimes[i][j];
    }

    /**
     * 测试两种方法
     */
    public static void main(String[] args) {
        //矩阵链的尺寸表，长度是矩阵个数+1
        Integer[] matrixChainSize = new Integer[]{30, 35, 15, 5, 10, 20, 25};
        MatrixChainMultiplication.matrixChainOrder(matrixChainSize);
        MatrixChainMultiplication.memoizedMatrixChain(matrixChainSize);
    }
}