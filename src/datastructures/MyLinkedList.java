package datastructures;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * Author: Lu Tianle
 * Date: 2017-9-13
 * Description: 表的链表实现，即：实现Collection中的LinkedList
 */

public class MyLinkedList<T> implements Iterable<T> {
    private int theSize;
    private int modCount;
    //每次调用该类的remove和add方法将改变这个值，以便在迭代器中检测原对象有没有被改变
    private Node<T> beginMaker;//beginMaker不存数据
    private Node<T> endMaker;//endMaker不存数据

    public MyLinkedList() {
        doClear();
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public void clear() {
        doClear();
    }

    private void doClear() {
        beginMaker = new Node<>(null, null, null);
        endMaker = new Node<>(null, beginMaker, null);
        beginMaker.next = endMaker;

        theSize = 0;
        modCount++;
    }

    private static class Node<T> {
        public T data;
        public Node<T> previous;
        public Node<T> next;

        public Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }

    /**
     * 在 position 位置插入一个节点
     *
     * @param position 需要插入的位置
     * @param val      节点的值
     */
    private void addBefore(Node<T> position, T val) {
        Node<T> newNode = new Node<>(val, position.previous, position);
        position.previous.next = newNode;
        position.previous = newNode;
        theSize++;
        modCount++;
    }

    public void add(T val) {
        add(theSize, val);
    }

    public void add(int idx, T val) {
        addBefore(getNode(idx, 0, theSize), val);
    }

    public T set(int idx, T val) {
        Node<T> position = getNode(idx);
        T oldVal = position.data;
        position.data = val;
        return oldVal;
    }

    public void remove(int idx) {
        remove(getNode(idx));
    }

    private T remove(Node<T> position) {
        position.previous.next = position.next;
        position.next.previous = position.previous;
        theSize--;
        modCount++;
        return position.data;
    }

    @Contract(pure = true)
    private Node<T> getNode(int idx) {
        return getNode(idx, 0, theSize - 1);
    }

    @Contract(pure = true)
    private Node<T> getNode(int idx, int lower, int upper) {
        if (idx < lower && idx > upper) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode;
        if (idx < theSize / 2) {
            currentNode = beginMaker.next;
            for (int i = 0; i < idx; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = endMaker;
            for (int i = theSize; i > idx; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        //内部类向上转型为接口
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        //注意这个T不是一个泛型参数，而是指定了一个类型
        //所以ArrayListIterator不是一个泛型类

        private Node<T> current = beginMaker.next;
        private boolean okToMove = false;//调用next之前不能调用remove的判断
        private int exceptedModCount = modCount;


        @Override
        public boolean hasNext() {
            return current != endMaker;
        }

        @Override
        public T next() {
            if (modCount != exceptedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T val = current.data;
            current = current.next;
            okToMove = true;
            return val;
        }

        @Override
        public void remove() {
            if (modCount != exceptedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToMove) {
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(current.previous);
            exceptedModCount++;
            okToMove = false;
        }

    }

    /**
     * @return Object类型的数组
     */
    public T[] toArray() {
        T[] array = (T[]) new Comparable[theSize];
        Iterator<T> iterator = this.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            array[i] = iterator.next();
        }
        return array;
    }

}
