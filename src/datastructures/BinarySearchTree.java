package datastructures;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.<p>
 * User: Lu Tainle<p>
 * Date: 2017-09-05<p>
 * 二叉搜索树<p>
 * // ******************PUBLIC OPERATIONS*********************<p>
 * // void insert( x )       --> Insert x<p>
 * // void remove( x )       --> Remove x<p>
 * // boolean contains( x )  --> Return true if x is present<p>
 * // Comparable findMin( )  --> Return smallest item<p>
 * // Comparable findMax( )  --> Return largest item<p>
 * // boolean isEmpty( )     --> Return true if empty; else false<p>
 * // void makeEmpty( )      --> Remove all items<p>
 * // void printTree( )      --> Print tree in sorted order<p>
 * // ******************ERRORS********************************<p>
 * // Throws UnderflowException as appropriate<p>
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public Node<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    private boolean contains(T x, Node<T> root) {
        if (root == null) {
            return false;
        }

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            return contains(x, root.left);
        } else if (compareResult > 0) {
            return contains(x, root.right);
        } else {
            return true;
        }
    }

    private Node<T> findMin(Node<T> root) {
        if (root == null) {
            return null;
        } else if (root.left == null) {
            return root;
        } else {
            return findMin(root.left);
        }
    }

    private Node<T> findMax(Node<T> root) {
        if (root == null) {
            return null;
        } else if (root.right == null) {
            return root;
        } else {
            return findMin(root.right);
        }
    }

    public T findMin() {
//        if(isEmpty()){
//            throw new UnderflowException();
//        }
        return findMin(root).element;
    }

    public T findMax() {
//        if(isEmpty()){
//            throw new UnderflowException();
//        }
        return findMax(root).element;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    private Node<T> insert(T x, Node<T> root) {
        if (root == null) {
            return new Node<>(x, null, null);
        } else {
            int compareResult = x.compareTo(root.element);
            if (compareResult < 0) {
                root.left = insert(x, root.left);
            } else if (compareResult > 0) {
                root.right = insert(x, root.right);
            }
            return root;
        }
    }

    private Node<T> remove(T x, Node<T> root) {
        if (root == null) {
            return null;
        }

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            root.left = remove(x, root.left);
        } else if (compareResult > 0) {
            root.right = remove(x, root.right);
        } else if (root.left != null && root.right != null) {
            root.element = findMin(root.right).element;
            root.right = remove(x, root.right);
        } else {
            root = root.left != null ? root.left : root.right;
        }
        return root;
    }

    public Node<T> remove(T x) {
        return remove(x, root);
    }

    /**
     * 按从小到大的顺序打印关键字
     */
    public void printKeywords() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        } else {
            printKeywords(root);
        }
    }

    private void printKeywords(Node<T> root) {
        if (root != null) {//中序遍历
            printKeywords(root.left);
            System.out.print(root.element + " ");
            printKeywords(root.right);
        }
    }
}
