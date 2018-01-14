package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sort.QuickSort.swapReferences;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2018-01-02 9:32
 * Description: 选择问题的两种算法，即找第i个顺序统计量（该集合中第i小的元素）
 */
public class OrderStatistic {

    /**
     * 随机算法，期望运行时间是线性的，
     *
     * @param array 输入数组，元素必须是不同的
     * @param ith   第i个顺序统计量
     * @param <T>   数组类型
     * @return 数组的第i个顺序统计量
     */
    public static <T extends Comparable<? super T>> T randomSelect(T[] array, int ith) {
        if (ith < 1) {
            return randomSelect(array, 1, 0, array.length - 1);
        } else if (ith > array.length) {
            return randomSelect(array, array.length, 0, array.length - 1);
        } else {
            return randomSelect(array, ith, 0, array.length - 1);
        }
    }

    private static <T extends Comparable<? super T>> T randomSelect(T[] array, int ith, int left, int right) {
        if (left == right) return array[left];
        Random random = new Random();
        int pivotIndex = random.nextInt(right - left + 1) + left;
        T pivot = array[pivotIndex];
        swapReferences(array, pivotIndex, right);
        int i = left - 1, j = right;
        for (; ; ) {
            while (array[++i].compareTo(pivot) < 0) ;
            while (--j >= 0 && array[j].compareTo(pivot) > 0) ;
            if (i < j) swapReferences(array, i, j);
            else break;
        }
        swapReferences(array, i, right);
        int leftSubArrayNum = i - left; //左子数组元素个数
        if (ith == leftSubArrayNum + 1) return array[i];
        else if (ith < leftSubArrayNum + 1) {
            return randomSelect(array, ith, left, i - 1);
        } else {
            return randomSelect(array, ith - leftSubArrayNum - 1, i + 1, right);
        }
    }

    /**
     * 非随机算法，最坏运行时间是线性的，
     *
     * @param array 输入数组，元素必须是不同的
     * @param ith   第i个顺序统计量
     * @param <T>   数组类型
     * @return 数组的第i个顺序统计量
     */
    public static <T extends Comparable<? super T>> T select(T[] array, int ith) {
        if (ith < 1) {
            return select(array, 1, 0, array.length - 1);
        } else if (ith > array.length) {
            return select(array, array.length, 0, array.length - 1);
        } else {
            return select(array, ith, 0, array.length - 1);
        }
    }

    private static <T extends Comparable<? super T>> T select(T[] array, int ith, int left, int right) {
        if (left == right) return array[left];
        List<T> list = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            list.add(array[i]);
        }
        T pivot = median(list);
        int pivotIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (pivot.compareTo(array[i]) == 0) {
                pivotIndex = i;
                break;
            }
        }
        swapReferences(array, pivotIndex, right);
        int i = left - 1, j = right;
        for (; ; ) {
            while (array[++i].compareTo(pivot) < 0) ;
            while (--j >= 0 && array[j].compareTo(pivot) > 0) ;
            if (i < j) swapReferences(array, i, j);
            else break;
        }
        swapReferences(array, i, right);
        int leftSubArrayNum = i - left; //左子数组元素个数
        if (ith == leftSubArrayNum + 1) return array[i];
        else if (ith < leftSubArrayNum + 1) {
            return select(array, ith, left, i - 1);
        } else {
            return select(array, ith - leftSubArrayNum - 1, i + 1, right);
        }
    }

    /**
     * @return 中位数法找主元
     */
    private static <T extends Comparable<? super T>> T median(List<T> list) {
        int n = list.size();
        if (n <= 5) {
            return median5(list);
        } else {
            List<T> medianList = new ArrayList<>();
            for (int i = 0; i < n; i += 5) {
                List<T> subList;
                if (i == n - (n % 5)) {
                    subList = list.subList(i, i + n % 5);
                } else {
                    subList = list.subList(i, i + 5);
                }
                InsertionSort.insertionSort(subList);
                medianList.add(median5(subList));
            }
            return median(medianList);
        }
    }

    /**
     * @return 5个以内元素列表的中位数
     */
    private static <T extends Comparable<? super T>> T median5(List<T> list) {
        int n = list.size();
        if (1 == n || 3 == n || 5 == n) {
            InsertionSort.insertionSort(list);
            return list.get(n / 2);
        } else {
            InsertionSort.insertionSort(list);
            return list.get(n / 2 - 1);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{11, 3, 2, 14, 13, 1, 15, 8, 12, 17, 4, 20, 19, 9, 10, 5, 6, 7};
        Integer[] b = a.clone();
        int i = 20;
        System.out.print("数组：");
        for (Integer aInt : a) {
            System.out.print(aInt + ", ");
        }
        System.out.println();
        System.out.println("（随机算法）第" + i + "小的元素是" + randomSelect(a, i));
        System.out.println("（非随机算法）第" + i + "小的元素是" + select(b, i));
    }
}
