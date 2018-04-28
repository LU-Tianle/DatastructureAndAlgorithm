package divideandconquer;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-04-24 13:59<p>
 * 二分查找
 */
public class BinarySearch {
    public static <T extends Comparable<? super T>> boolean binarySearch(T[] array, T x) {
        return binarySearch(array, x, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> boolean binarySearch(T[] array, T x, int left, int right) {
        if (left == right) return x.equals(array[left]);
        int n = right - left + 1;
        if (x.equals(array[left + n / 2])) return true;
        else if (x.compareTo(array[left + n / 2]) > 0) return binarySearch(array, x, left + n / 2 + 1, right);
        else return binarySearch(array, x, left, left + n / 2 - 1);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1, 3, 5, 6, 8, 10, 14, 15, 18, 20};
        System.out.println(binarySearch(a, 11));
    }
}
