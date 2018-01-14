package datastructures;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lu Tainle
 * Date: 2017-09-09
 * Description: 分离链接Hash表
 */

// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

public class SeparateChainingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private int currentSize;
    private List<T>[] theLists;

    private static boolean isPrime(int n) {
        if (n < 3) {
            return n == 2;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public SeparateChainingHashTable(Integer size) {
        BigInteger bigSize = new BigInteger(size.toString());
        theLists = new LinkedList[bigSize.nextProbablePrime().intValue()];
        for (List<T> linkedList : theLists) {
            linkedList = new LinkedList<>();
        }
    }

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    private void reHash() {
        List<T>[] oldList = theLists;
        BigInteger len = new BigInteger(Integer.toString(theLists.length));
        len = len.multiply(new BigInteger("2"));
        theLists = new List[len.nextProbablePrime().intValue()];
        for (List<T> aArray : theLists) {
            aArray = new LinkedList<>();
        }
        for (List<T> list : oldList) {
            for (T t : list) {
                insert(t);
            }
        }
    }

    /**
     * 将元素的Hash Code 转化为数组下标
     *
     * @param x 输入
     * @return 数组下标
     */
    private int myHash(T x) {
        int hashValue = x.hashCode();
        hashValue %= theLists.length;
        if (hashValue < 0) {
            hashValue += theLists.length;
        }
        return hashValue;
    }

    public void makeEmpty() {
        currentSize = 0;
        for (List<T> list : theLists) {
            list.clear();
        }
    }

    public boolean contains(T x) {
        List<T> whichList = theLists[myHash(x)];
        return whichList.contains(x);

    }

    private void insert(T x) {
        List<T> whichList = theLists[myHash(x)];
        if (!whichList.contains(x)) {
            whichList.add(0, x);
            if (++currentSize > theLists.length) {
                reHash();
            }
        }
    }

    private void remove(T x) {
        List<T> whichList = theLists[myHash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }
}
