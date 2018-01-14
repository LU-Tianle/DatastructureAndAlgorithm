package datastructures;

// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

public class AVLTree<T extends Comparable<? super T>> {

    /**
     * 节点类
     *
     * @param <T>
     */
    private static class Node<T> {
        public Node(T element) {
            this(element, null, null);
        }

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            height = 0;
        }

        T element;
        Node<T> left;
        Node<T> right;
        int height;
    }

    /**
     * 返回节点高度
     *
     * @param t 节点
     * @return 高度
     */
    private int height(Node<T> t) {
        return t == null ? -1 : t.height;
    }

    /**
     * 向树中插入一个值
     *
     * @param x    要插入的值
     * @param root 树的根节点
     * @return 子树的新的根节点
     */
    private Node<T> insert(T x, Node<T> root) {

        if (root == null) {
            return new Node<>(x, null, null);
        }

        int compareResult = x.compareTo(root.element);

        if (compareResult < 0) {
            root.left = insert(x, root.left);

        } else if (compareResult > 0) {
            root.right = insert(x, root.right);
        }

        return balance(root);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private Node<T> balance(Node<T> root) {
        if (root == null) {
            return root;
        }
        if (height(root.left) - height(root.right) > ALLOWED_IMBALANCE) {
            if (height(root.left.left) > height(root.left.right)) {
                root = rotateLeft(root);
            } else {
                root = doubleRotateLeft(root);
            }
        } else if (height(root.right) - height(root.left) > ALLOWED_IMBALANCE) {
            if (height(root.right.right) > height(root.right.left)) {
//                rotateRight();
            } else {
//                doubleRoteRight();
            }
        }
        root.height = Math.max(height(root.left), height(root.right));
        return root;
    }

    private Node<T> rotateLeft(Node<T> k2) {
        Node<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height((k2.right))) + 1;
        k1.height = Math.max(height((k1.left)), k2.height) + 1;
        return k1;
    }

    private Node<T> doubleRotateLeft(Node<T> k3) {
        //...
        return null;
    }


}
