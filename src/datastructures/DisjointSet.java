package datastructures;

/**
 * Created with IntelliJ IDEA.
 * User: Lu Tainle
 * Date: 2017-09-21
 * Description: 不相交集类，所有元素已按0~N-1编号
 */

// ******************PUBLIC OPERATIONS*********************
// void union( root1, root2 ) --> Merge two sets
// int find( x )              --> Return set containing x
// ******************ERRORS********************************
// No error checking is performed

public class DisjointSet {
    private int[] s;

    public DisjointSet(int numElements) {
        s = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            s[i] = -1;
        }
    }

    /**
     * 合并两个元素所在类,按大小合并
     *
     * @param a 元素a
     * @param b 元素b
     */
    public void union(int a, int b) {
        if (s[a] < s[b]) {
            s[b] += s[a];
            s[a] = b;
        } else {
            s[a] += s[b];
            s[b] = a;
        }
    }

    /**
     * 包含路径压缩的寻找元素所在类
     *
     * @param x 元素
     * @return x所在类
     */
    public int find(int x) {
        if (s[x] < 0) {
            return x;
        } else {
            return s[x] = find(s[x]);
        }
    }

}
