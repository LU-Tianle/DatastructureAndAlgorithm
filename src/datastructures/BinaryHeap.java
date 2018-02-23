package datastructures;

/**
 * Created with IntelliJ IDEA.<p>
 * User: Lu Tainle<p>
 * Date: 2017-09-10<p>
 * 优先队列的二叉堆实现<p>
 * // ******************PUBLIC OPERATIONS*********************<p>
 * // void insert( x )       --> Insert x<p>
 * // Comparable deleteMin( )--> Return and remove smallest item<p>
 * // Comparable findMin( )  --> Return smallest item<p>
 * // boolean isEmpty( )     --> Return true if empty; else false<p>
 * // void makeEmpty( )      --> Remove all items<p>
 * // ******************ERRORS********************************<p>
 * // Throws UnderflowException as appropriate<p>
 */
public class BinaryHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private T[] array;

    public BinaryHeap(int capacity) {
        array = (T[]) new Comparable[capacity + 1];
        currentSize = 0;
    }

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 根据输入数组构建堆
     *
     * @param items 输入数组
     */
    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (T item : items) {
            array[i++] = item;
        }
        for (int j = currentSize / 2; j > 0; j--) {
            percolateDown(j);
        }
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    public T findMin() {
        if (isEmpty()) {
            System.err.println("Empty Heap");
            return null;
        }
        return array[1];
    }

    public void insert(T x) {
        if (currentSize == array.length - 1) {
            enlargeArray(2 * array.length + 1);
        }
        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    public void makeEmpty() {
        currentSize = 0;
    }

    public T deleteMin() {
        if (isEmpty()) {
            System.err.println("Empty Heap");
            return null;
        }
        T min = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);
        return min;
    }

    private void percolateDown(int hole) {
        T tmp = array[hole];
        int child;

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                child++;
            }
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    public static void main(String[] args) {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
    }
}
