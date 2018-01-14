package sort;

import datastructures.BinaryHeap;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2017-12-29 14:40
 * Description: 二叉堆排序
 */
public class HeapSort {
    public static <T extends Comparable<? super T>> void heapSort(T[] array) {
        BinaryHeap<T> binaryHeap = new BinaryHeap<>(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = binaryHeap.deleteMin();
        }
    }

}
