package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2017-12-30 15:01
 * Description:
 */
public class LinearTimeSort {
    /**
     * 计数排序，数组元素是非负整数且最大值<=k
     *
     * @param array 要排序的数组
     * @param k     数组元素<=k
     * @return 排序后的数组
     */
    public static int[] countingSort(int[] array, int k) {
        int[] sortedArray = new int[array.length];
        int[] count = new int[k + 1];
        Arrays.fill(count, 0);
        for (int i = 0; i < array.length; i++) {
            count[array[i]]++;
        }
        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }
        for (int i = array.length - 1; i >= 0; i--) {
            sortedArray[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }
        return sortedArray;
    }

    /**
     * 基数排序，基数是10，对正整数排序，调用计数排序
     *
     * @param array 要排序的数组
     * @return 排序后的数组
     */
    public static Integer[] radixSort(Integer[] array) {
        Integer[] sortedArray = new Integer[array.length];
        int d = 0;//最大位数
        for (Integer integer : array) {
            d = d < integer.toString().length() ? integer.toString().length() : d;
        }
        //对各个位置上的数计数排序
        for (int k = 0; k < d; k++) {
            //下面是计数排序
            int[] count = new int[10];
            int tmp = (int) Math.pow(10, k);
            Arrays.fill(count, 0);
            for (int i = 0; i < array.length; i++) {
                count[array[i] / tmp % 10]++;
            }
            for (int j = 1; j <= 9; j++) {
                count[j] += count[j - 1];
            }
            for (int t = array.length - 1; t >= 0; t--) {
                sortedArray[count[array[t] / tmp % 10] - 1] = array[t];
                count[array[t] / tmp % 10]--;
            }
            array = sortedArray.clone();
        }
        return sortedArray;
    }

    /**
     * 桶排序，数组所有元素在[0,1)之间
     *
     * @param array 要排序的数组
     * @return 排序后的数组
     */
    public static Float[] bucketSort(Float[] array) {
        int n = array.length;
        Float[] output;
        ArrayList<Float> outList = new ArrayList<>();
        ArrayList<ArrayList<Float>> arrayLists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arrayLists.add(new ArrayList<>());
        }
        for (Float aFloat : array) {
            arrayLists.get((int) (n * aFloat)).add(aFloat);
        }
        for (int i = 0; i < n; i++) {
            Float[] tmp = arrayLists.get(i).toArray(new Float[arrayLists.get(i).size()]);
            InsertionSort.insertionSort(tmp);
            outList.addAll(Arrays.asList(tmp));
        }
        output = outList.toArray(new Float[outList.size()]);
        return output;
    }


    public static void main(String[] args) {
        //计数排序的测试
        int[] a = new int[10];
        Random random = new Random();
        int max = 15;
        int min = 0;
        for (int i = 0; i < a.length; i++) {
            int x = random.nextInt(max - min + 1) + min;
            a[i] = x;
        }
        System.out.print("原始数组，元素在0~15之内：");
        for (Object o : a) {
            System.out.print(o + ", ");
        }
        System.out.println();
        int[] b = countingSort(a, 15);
        System.out.print("计数排序的结果：");
        for (Object o : b) {
            System.out.print(o + ", ");
        }
        System.out.println();
        System.out.println();

        //基数排序的测试
        Integer[] e = new Integer[]{329, 457, 4236, 720, 355, 10000, 657, 839, 436, 720, 355, 3, 45, 6547, 839};
        Integer[] f;
        System.out.print("基数排序原始数组（正整数）：");
        for (Object o : e) {
            System.out.print(o + ", ");
        }
        System.out.println();
        f = radixSort(e);
        System.out.print("排序后的数组：");
        for (Object o : f) {
            System.out.print(o + ", ");
        }
        System.out.println();
        System.out.println();

        //桶排序的测试
        Float[] c = new Float[]{0.899f, 0.895f, 0.897f, 0.2113f, 0.123f, 0.451f, 0f, 0.653f, 0.435f, 0.765f, 0.657f, 0.231f};
        Float[] d;
        System.out.print("桶排序原始数组（在[0,1)之内）：");
        for (Object o : c) {
            System.out.print(o + ", ");
        }
        System.out.println();
        d = bucketSort(c);
        System.out.print("排序后的数组：");
        for (Object o : d) {
            System.out.print(o + ", ");
        }
        System.out.println();
        System.out.println();
    }
}
