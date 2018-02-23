package matrix;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-04 14:10<p>
 * 继承GenericMatrix类，指定具体的泛型类型，实现一个元素为Integer的矩阵类与基本元素<p>
 */
public class IntegerMatrix extends GenericMatrix<Integer> {
    public IntegerMatrix(Integer[][] a) {
        super(a);
    }

    @Override
    protected Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    protected Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    protected Integer zeros() {
        return 0;
    }

    /**
     * 对父类的加法封装，输入输出都是IntegerMatrix类型
     */
    public IntegerMatrix addMatrix(IntegerMatrix matrix2) {
        Integer[][] integers = add2dArray(matrix2.a);
        return new IntegerMatrix(integers);
    }

    /**
     * 对父类的乘法封装，输入输出都是IntegerMatrix类型
     */
    public IntegerMatrix multiplyMatrix(IntegerMatrix matrix2) {
        return new IntegerMatrix(multiply2dArray(matrix2.a));
    }

}
