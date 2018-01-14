package sort;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2017-9-13
 * Description: 归并排序
 */
public class MergeSort {

    public static <T extends Comparable<? super T>> void mergeSort(T[] array) {
        T[] temArray = (T[]) new Comparable[array.length];
        mergeSort(array, temArray, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] array, T[] temArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(array, temArray, left, center);
            mergeSort(array, temArray, center + 1, right);
            merge(array, temArray, left, center + 1, right);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] array, T[] temArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int numElements = rightEnd - leftPos + 1;
        int tmpPos = leftPos;

        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (array[leftPos].compareTo(array[rightPos]) <= 0) temArray[tmpPos++] = array[leftPos++];
            else temArray[tmpPos++] = array[rightPos++];
        }

        while (leftPos <= leftEnd) temArray[tmpPos++] = array[leftPos++];
        while (rightPos <= rightEnd) temArray[tmpPos++] = array[rightPos++];

        for (int i = 0; i < numElements; i++, rightEnd--) array[rightEnd] = temArray[rightEnd];
    }

}
