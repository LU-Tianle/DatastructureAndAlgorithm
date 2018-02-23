package sort;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-12-25 16:55<p>
 * 几种排序的比较<p>
 */
public class ComparisionOfSorts {
    public static void main(String[] args) {

        Integer[] a = new Integer[100000];
        Random random = new Random();
        int max = 20;
        int min = -20;
        for (int i = 0; i < a.length; i++) {
            Integer x = random.nextInt(max - min) + min;
            a[i] = x;
        }
        Integer[] b = a.clone();
        Integer[] c = a.clone();
        Integer[] d = a.clone();

        long beginTimeInsertionSort = System.nanoTime();
        InsertionSort.insertionSort(a);
        long endTimeInsertionSort = System.nanoTime();
        System.out.println("Insertion Sort时间：" + (endTimeInsertionSort - beginTimeInsertionSort) * 1.0 / 1e6 + "ms");

        long beginTimeMergeSort = System.nanoTime();
        MergeSort.mergeSort(b);
        long endTimeMergeSort = System.nanoTime();
        System.out.println("Merge Sort时间：" + (endTimeMergeSort - beginTimeMergeSort) * 1.0 / 1e6 + "ms");

        long beginTimeQuickSort = System.nanoTime();
        QuickSort.quickSort(c);
        long endTimeQuickSort = System.nanoTime();
        System.out.println("Quick Sort时间：" + (endTimeQuickSort - beginTimeQuickSort) * 1.0 / 1e6 + "ms");

        long beginTimeHeapSort = System.nanoTime();
        HeapSort.heapSort(d);
        long endTimeHeapSort = System.nanoTime();
        System.out.println("Heap Sort时间：" + (endTimeHeapSort - beginTimeHeapSort) * 1.0 / 1e6 + "ms");
    }
}
