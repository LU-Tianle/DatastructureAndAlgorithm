package sort;

import java.util.List;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-12-28 15:00<p>
 * 插入排序<p>
 */
public class InsertionSort {
    /**
     * 插入排序，输入是数组
     *
     * @param array 输入数组
     * @param <T>   数组类型
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] array) {
        insertionSort(array, 0, array.length - 1);
    }

    protected static <T extends Comparable<? super T>> void insertionSort(T[] array, int left, int right) {
        for (int p = left + 1; p <= right; p++) {
            T tmp = array[p];
            int j;
            for (j = p; j > left && tmp.compareTo(array[j - 1]) < 0; j--) array[j] = array[j - 1];
            array[j] = tmp;
        }
    }

    /**
     * 插入排序，输入是列表
     *
     * @param list 输入是列表
     * @param <T>  列表元素类型
     */
    public static <T extends Comparable<? super T>> void insertionSort(List<T> list) {
        insertionSort(list, 0, list.size() - 1);
    }

    protected static <T extends Comparable<? super T>> void insertionSort(List<T> list, int left, int right) {
        for (int p = left + 1; p <= right; p++) {
            T tmp = list.get(p);
            int j;
            for (j = p; j > left && tmp.compareTo(list.get(j - 1)) < 0; j--) list.set(j, list.get(j - 1));
            list.set(j, tmp);
        }
    }
}
