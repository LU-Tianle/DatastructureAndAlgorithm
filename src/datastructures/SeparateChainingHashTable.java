package datastructures;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.<p>
 * User: Lu Tainle<p>
 * Date: 2017-09-09<p>
 * 分离链接Hash表<p>
 * // ******************PUBLIC OPERATIONS*********************<p>
 * // void insert( x )       --> Insert x<p>
 * // void remove( x )       --> Remove x<p>
 * // boolean contains( x )  --> Return true if x is present<p>
 * // void makeEmpty( )      --> Remove all items<p>
 */
public class SeparateChainingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private int currentSize;
    private List<T>[] theLists;

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
