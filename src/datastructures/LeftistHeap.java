package datastructures;

/**
 * Created with IntelliJ IDEA.
 * User: Lu Tainle
 * Date: 2017-09-10
 * Description: 左式堆
 */

// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void merge( rhs )      --> Absorb rhs into this heap
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

public class LeftistHeap<T extends Comparable<? super T>> {
    public static class Node<T> {
        int npl;//零路径长
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
            npl = 0;
        }
    }

    private Node<T> root;

    public LeftistHeap() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }

    public T findMin() {
        if (isEmpty()) {
//            throw new UnderflowException( );
            return null;
        }
        return root.element;
    }

    /**
     * 左式堆rhs与当前堆合并，然后销毁rhs指向的堆
     *
     * @param rhs 需要合并的堆
     */
    public void merge(LeftistHeap<T> rhs) {
        if (this == rhs) return;
        root = merge(root, rhs.root);
        rhs.root = null;
    }

    private Node<T> merge(Node<T> h1, Node<T> h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        if (h1.element.compareTo(h2.element) < 0)
            return merge1(h1, h2);
        else
            return merge1(h2, h1);
    }

    /**
     * 把h1的右子堆合并到h2
     *
     * @param h1 有小的根节点左式堆
     * @param h2 有大的根节点左式堆
     * @return 合并之后的左式堆
     */
    private Node<T> merge1(Node<T> h1, Node<T> h2) {
        if (h1.left == null) {
            h1.left = h2;
        } else {
            h1.right = merge(h1.right, h2);
            if (h1.left.npl < h1.right.npl) {
                swapChildren(h1);
                h1.npl = h1.right.npl + 1;
            }
        }
        return h1;
    }

    private void swapChildren(Node<T> t) {
        Node<T> tmp = t.left;
        t.left = t.right;
        t.right = tmp;
    }

    public T deleteMin() {
        if (isEmpty()) {
//            throw new UnderflowException();
            return null;
        }
        T minItem = root.element;
        root = merge(root.left, root.right);
        return minItem;
    }

    public void insert(T x) {
        root = merge(new Node<>(x), root);
    }

}