package datastructures;


import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Lu Tainle
 * Date: 2017-09-09
 * Description: 二次探测Hash Table
 */

// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

public class QuadraticProbingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 11;
    private int currentSize;
    private HashEntry<T>[] array;

    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        makeEmpty();
    }

    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public void makeEmpty() {
        for (HashEntry<T> hashEntry : array) {
            hashEntry = null;
        }
    }

    private void allocateArray(Integer size) {
        BigInteger bigSize = new BigInteger(size.toString());
        array = new HashEntry[bigSize.nextProbablePrime().intValue()];
    }

    private static class HashEntry<T> {
        public T element;
        public boolean isActive;

        public HashEntry(T element, boolean isActive) {
            this.element = element;
            this.isActive = isActive;
        }

        public HashEntry(T element) {
            this(element, true);
        }
    }
    
    private int myHash(T x) {
        int hashValue = x.hashCode();
        return hashValue;
    }

    private void reHash() {
        HashEntry<T>[] oldArray = array;
        BigInteger len = new BigInteger(Integer.toString(oldArray.length));
        len = len.multiply(new BigInteger("2"));
        allocateArray(len.nextProbablePrime().intValue());
        currentSize = 0;
        for (HashEntry<T> hashEntry : oldArray) {
            if (hashEntry != null && hashEntry.isActive) {
                insert(hashEntry.element);
            }
        }
    }

    private int myhash(T x) {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;
        return hashVal;
    }

    private int findPos(T x) {
        int currentPos = myHash(x);
        int offset = 1;

        while (array[currentPos] != null || !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }

        }
        return currentPos;
    }

    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    public boolean contains(T x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    public void remove(T x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
        }
    }

    public void insert(T x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return;
        }
        array[currentPos] = new HashEntry<>(x, true);
        currentSize++;
        if (currentSize > array.length / 2) {
            reHash();
        }
    }

}
