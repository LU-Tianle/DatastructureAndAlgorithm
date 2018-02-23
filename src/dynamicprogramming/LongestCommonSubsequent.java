package dynamicprogramming;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2018-02-05 16:38
 * 最长公共子序列问题（LCS）（算法导论15.4）
 */
public class LongestCommonSubsequent {

    /**
     * 求序列（字符数组类型）x和y的最长公共子序列
     * 自底向上的动态规划，循环不递归，
     * 输出最长子序列的长度，和一个最长公共子序列（最长公共子序列不唯一）
     *
     * @param x 序列x的字符数组
     * @param y 序列y的字符数组
     */
    public static void LCS(char[] x, char[] y) {
        int m = x.length;
        int n = y.length;
        int[][] c = new int[m + 1][n + 1];//c[i][j]表示x的前i个元素和y的前j个元素的最长公共子序列的长度
        String[][] b = new String[m + 1][n + 1];//用于构造最长公共子序列中的元素
        for (int i = 0; i < m + 1; i++) {
            c[i][0] = 0;
        }
        for (int i = 0; i < n + 1; i++) {
            c[0][i] = 0;
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "x[i](y[j])属于公共子序列";
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = "原序列的解与子序列x[i-1]相同";
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = "原序列的解与子序列y[i-1]相同";
                }
            }
        }
        System.out.println("最长公共子序列长：" + c[m][n]);
        System.out.print("一个公共最长序列是：");
        LCSPrint(b, x, m, n);
    }

    /**
     * 输出最优公共子序列的递归程序
     */
    public static void LCSPrint(String[][] b, char[] x, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (b[i][j].equals("x[i](y[j])属于公共子序列")) {
            LCSPrint(b, x, i - 1, j - 1);
            System.out.print(x[i - 1]);
        } else if (b[i][j].equals("原序列的解与子序列x[i-1]相同")) {
            LCSPrint(b, x, i - 1, j);
        } else {
            LCSPrint(b, x, i, j - 1);
        }
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        char[] x = new char[]{'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        char[] y = new char[]{'B', 'D', 'C', 'A', 'B', 'A'};
        LongestCommonSubsequent.LCS(x, y);
    }
}
