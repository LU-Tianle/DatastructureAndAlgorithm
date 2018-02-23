package datastructures;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2017-9-13<p>
 * 表的数组实现，即：实现Collection中的ArrayList<p>
 */
public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private int theSize;
    private T[] theItems;

    public MyArrayList() {
        doClear();
    }


    public void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size() {
        return theSize;
    }

    public void trimToSize() {
        ensureCapacity(theSize);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity >= theSize) {
            T[] old = theItems;
            theItems = (T[]) new Object[newCapacity];
            for (int i = 0; i < theSize; i++) {
                theItems[i] = old[i];
            }
        }
    }

    public T get(int idx) {
        if (idx < 0 || idx > theSize - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return theItems[idx];
    }

    public void set(int idx, T val) {
        if (idx < 0 || idx > theSize - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        theItems[idx] = val;
    }

    public void add(int idx, T val) {
        if (theItems.length == theSize) {
            ensureCapacity(2 * theSize + 1);
        }
        for (int i = theSize; i > idx; i--) {
            theItems[i] = theItems[i - 1];
        }
        theItems[idx] = val;
        theSize++;
    }

    public void add(T val) {
        add(theSize, val);
    }

    public T remove(int idx) {
        for (int i = idx; i < theSize - 1; i++) {
            theItems[i] = theItems[i + 1];
        }
        theSize--;
        return theItems[idx];
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        //内部类向上转型为接口
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        //注意这个T不是一个泛型参数，而是指定了一个类型
        //所以ArrayListIterator不是一个泛型类
        private int current = 0;

        @Override
        public void remove() {
            MyArrayList.this.remove(--current);
        }

        @Override
        public boolean hasNext() {
            return current < theSize;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return theItems[current++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * @return Object类型的数组
     */
    public T[] toArray() {
        trimToSize();
        return this.theItems;
    }


}
