package sort;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-9-13<p>
 * 快速排序<p>
 */

public class QuickSort {
    private static final int CUTOFF = 5;

    public static <T extends Comparable<? super T>> void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] array, int left, int right) {
        if (left + CUTOFF <= right) {
            T pivot = median3(array, left, right);//主元是数组左中右三个元素的中位数
            int i = left, j = right - 1;
            for (; ; ) {
                while (array[++i].compareTo(pivot) < 0) ;
                while (array[--j].compareTo(pivot) > 0) ;
                if (i < j) swapReferences(array, i, j);
                else break;
            }
            swapReferences(array, i, right - 1);
            System.out.println();
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        } else InsertionSort.insertionSort(array, left, right);
    }

    /**
     * 三位数中值法选取主元，输出主元，重排了数组
     * 数组最左边的是三个中最小的，最右边是三个中最大的，主元在倒数第二个位置
     */
    private static <T extends Comparable<? super T>> T median3(T[] array, int left, int right) {
        int center = (left + right) / 2;
        if (array[center].compareTo(array[left]) < 0) swapReferences(array, left, center);
        if (array[right].compareTo(array[left]) < 0) swapReferences(array, left, right);
        if (array[right].compareTo(array[center]) < 0) swapReferences(array, center, right);
        swapReferences(array, center, right - 1);
        return array[right - 1];
    }

    protected static <T> void swapReferences(T[] array, int index1, int index2) {
        T tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

}
