package sort;

import datastructures.BinaryHeap;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-12-29 14:40<p>
 * 二叉堆排序<p>
 */
public class HeapSort {
    public static <T extends Comparable<? super T>> void heapSort(T[] array) {
        BinaryHeap<T> binaryHeap = new BinaryHeap<>(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = binaryHeap.deleteMin();
        }
    }

}
