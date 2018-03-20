package others;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-03-04 21:22<p>
 * 染色问题
 */

public class Coloring {

    /**
     * 扇形的染色问题，一个圆分为n个扇形，用m种颜色对其染色，要求相邻的扇形颜色不同。
     *
     * @param n 一个圆分为n个扇形，n>0
     * @param m m种颜色，M>2
     * @return 可能的染色数
     */
    private static Integer sectorColoring(int n, int m) {
        if (n < 1 || m < 3) {
            throw new IllegalArgumentException("n>0, m>2");
        }
        if (n == 1) {//只有一个扇形时
            return m;
        } else if (n == 2) {//只有两个扇形时
            return m * (m - 1);
        }
        Integer[] number = new Integer[n + 1];//number[i]是扇形总数为i时，可能的涂色数
        number[1] = m;
        number[2] = m * (m - 1);
        for (int i = 3; i <= n; i++) {
            number[i] = m * (int) Math.pow(m - 1, i - 1) - number[i - 1];
        }
        //扇形数量大于2时，第一个扇形（扇形1）有m种颜色，与之相邻的扇形（扇形2）有m-1种颜色，
        //与扇形2相邻的扇形3有m-1种颜色...所以一共有m*(m-1)^(n-1)种颜色，
        // 但是扇形n与扇形1颜色相同的涂色数有number[i-1]种
        //这个和有解析解，可以求得：(m-1)[(m-1)^(n-1)+(-1)^n](n>=2)
        return number[n];
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println("扇形的染色问题，");
        int n = 5;
        int m = 5;
        System.out.print(n + "个扇形，" + m + "种颜色" + "可能的染色数：");
        System.out.println(sectorColoring(n, m));
    }
}

