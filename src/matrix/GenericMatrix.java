package matrix;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-03 9:34<p>
 * 泛型矩阵类，是一个抽象类，实现矩阵的基本运算，矩阵是一个泛型的二维数组<p>
 * 子类只需指定具体的类型，重写矩阵元素之间的加法乘法和定义0元素三个方法，即可实现矩阵的基本运算<p>
 */
public abstract class GenericMatrix<T extends Number> {
    protected T[][] a;

    protected GenericMatrix(T[][] a) {
        this.a = a;
    }

    public T getEntry(int i, int j) {
        if (i < 0 || j < 0 || i >= a.length || j >= a[0].length) {
            throw new IllegalArgumentException("索引越界");
        }
        return a[i][j];
    }

    public void setEntry(int i, int j, T value) {
        if (i < 0 || j < 0 || i >= a.length || j >= a[0].length) {
            throw new IllegalArgumentException("索引越界");
        }
        a[i][j] = value;
    }

    protected abstract T add(T a, T b);//元素的加法

    protected abstract T multiply(T a, T b);//元素的乘法

    protected abstract T zeros();//定义0元素

    /**
     * 矩阵加法，注意输入是T类型的泛型数组
     */
    protected T[][] add2dArray(T[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("两个矩阵的大小必须相同");
        }
        @SuppressWarnings("unchecked")
        T[][] result = (T[][]) Array.newInstance(b[0][0].getClass(), a.length, a[0].length);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = add(a[i][j], b[i][j]);
            }
        }
        return result;
    }

    /**
     * 矩阵乘法，注意输入是T类型的泛型数组
     */
    protected T[][] multiply2dArray(T[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("第一个矩阵的列数必须等于第二个矩阵的行数");
        }
        @SuppressWarnings("unchecked")
        T[][] result = (T[][]) Array.newInstance(b[0][0].getClass(), a.length, b[0].length);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = zeros();
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = add(multiply(a[i][k], b[k][j]), result[i][j]);
                }
            }
        }
        return result;
    }

    /**
     * 打印矩阵
     */
    public void printMatrix() {
        System.out.println("矩阵：");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

}
