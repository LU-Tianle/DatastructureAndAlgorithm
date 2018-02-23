package matrix;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-20<p>
 * 继承GenericMatrix类，指定具体的泛型类型，实现一个元素为Double的矩阵类与基本元素<p>
 */
public class DoubleMatrix extends GenericMatrix<Double> {
    public DoubleMatrix(Double[][] a) {
        super(a);
    }

    @Override
    protected Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    protected Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    protected Double zeros() {
        return 0.0;
    }

    /**
     * 对父类的加法封装，输入输出都是DoubleMatrix类型
     */
    public DoubleMatrix addMatrix(DoubleMatrix matrix2) {
        Double[][] doubles = add2dArray(matrix2.a);
        return new DoubleMatrix(doubles);
    }

    /**
     * 对父类的乘法封装，输入输出都是DoubleMatrix类型
     */
    public DoubleMatrix multiplyMatrix(DoubleMatrix matrix2) {
        return new DoubleMatrix(multiply2dArray(matrix2.a));
    }
}
