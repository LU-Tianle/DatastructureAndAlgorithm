package graph.search;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-04-14 12:07<p>
 * 树的遍历
 */
public class PrintTree {

    //二叉树结点
    private static class BinaryTreeNode<T> {
        T element;
        BinaryTreeNode<T> left;
        BinaryTreeNode<T> right;

        public BinaryTreeNode(T element) {
            this.element = element;
        }

        public BinaryTreeNode(T element, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 二叉树的层序遍历，分层打印
     */
    public static <T extends Comparable<? super T>> void printBinaryTreeLevelOrder(BinaryTreeNode<T> root) {
        LinkedList<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        BinaryTreeNode<T> lineLast = root; //表示下一层的最后结点
        while (!queue.isEmpty()) {
            BinaryTreeNode<T> node = queue.pop();
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            System.out.print(node.element + " ");
            if (lineLast == node) {
                System.out.println();
                if (!queue.isEmpty()) {
                    lineLast = queue.getLast();
                }
            }
        }
    }

    //多叉树结点，每个结点只存第一个子节点和最近的右兄弟结点
    private static class MultiTreeNode<T> {
        T element;
        MultiTreeNode<T> firstChild = null;
        MultiTreeNode<T> nextBrother = null;

        public MultiTreeNode(T element) {
            this.element = element;
        }
    }

    /**
     * 多叉树的层序遍历，分层打印
     */
    public static <T extends Comparable<? super T>> void printMultiTreeLevelOrder(MultiTreeNode<T> root) {
        LinkedList<MultiTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        MultiTreeNode<T> lineLast = root; //表示下一层的最后结点
        while (!queue.isEmpty()) {
            MultiTreeNode<T> node = queue.pop();
            System.out.print(node.element + " ");
            if (node.firstChild != null) queue.add(node.firstChild);
            MultiTreeNode<T> tmp = node;
            while (tmp.nextBrother != null) {
                tmp = tmp.nextBrother;
                if (tmp.firstChild != null) queue.add(tmp.firstChild);
                System.out.print(tmp.element + " ");
            }
            if (lineLast == node) {
                System.out.println();
                if (!queue.isEmpty()) {
                    lineLast = queue.getLast();
                }
            }
        }
    }

    public static void main(String[] args) {
        MultiTreeNode<String> a = new MultiTreeNode<>("A");
        MultiTreeNode<String> b = new MultiTreeNode<>("B");
        MultiTreeNode<String> c = new MultiTreeNode<>("C");
        MultiTreeNode<String> d = new MultiTreeNode<>("D");
        MultiTreeNode<String> e = new MultiTreeNode<>("E");
        MultiTreeNode<String> f = new MultiTreeNode<>("F");
        MultiTreeNode<String> g = new MultiTreeNode<>("G");
        MultiTreeNode<String> h = new MultiTreeNode<>("H");
        MultiTreeNode<String> i = new MultiTreeNode<>("I");
        MultiTreeNode<String> j = new MultiTreeNode<>("J");
        MultiTreeNode<String> k = new MultiTreeNode<>("K");
        MultiTreeNode<String> l = new MultiTreeNode<>("L");
        MultiTreeNode<String> m = new MultiTreeNode<>("M");
        MultiTreeNode<String> n = new MultiTreeNode<>("N");
        MultiTreeNode<String> o = new MultiTreeNode<>("O");
        MultiTreeNode<String> p = new MultiTreeNode<>("P");
        MultiTreeNode<String> q = new MultiTreeNode<>("Q");
        a.firstChild = b;
        b.nextBrother = c;
        b.firstChild = d;
        d.nextBrother = e;
        e.nextBrother = f;
        c.firstChild = g;
        g.nextBrother = h;
        d.firstChild = i;
        i.nextBrother = k;
        k.nextBrother = l;
        g.firstChild = m;
        m.nextBrother = n;
        h.firstChild = o;
        i.firstChild = j;
        o.firstChild = p;
        p.nextBrother = q;
        printMultiTreeLevelOrder(a);
    }
}
