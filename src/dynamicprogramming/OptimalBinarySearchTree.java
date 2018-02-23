package dynamicprogramming;

import datastructures.BinarySearchTree;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-06 22:48<p>
 * 最优二叉搜索树（Optimal-BST）（算法导论15.5）<p>
 */
public class OptimalBinarySearchTree<T extends Comparable<? super T>> {
    private T[] keyWords;
    //需要构建二叉搜索树的关键字，已经按从小到大的顺序排序，下面是对应的概率和伪关键字的概率
    //设有n个关键字：算法内部对其按从小到大的顺序编号为 1,2,…,i,…,n 编号，
    private Double[] p;
    //它们被搜索的概率在数组p中：关键字i被搜索的概率是p[i-1]；
    private Double[] q;
    //n个关键字对应n+1个伪关键字：按 0,1,…,i,…,n 编号，它们被搜索的概率在数组q中：
    //q[0]：搜索值小于最小的关键字1的概率；q[end]：搜索值>最大的关键字n的概率；
    //对其他的i，q[i]：搜索值在关键字i和i+1之间的概率；即伪关键字i被搜索的概率；
    private BinarySearchTree<T> optimalBST = new BinarySearchTree<>();
    //据此构建平均搜索次数最少的二叉搜索树optimalBST
    private Integer[][] root;//用于构建最优二叉搜索树的数组
    //root[i][j](1<=i<=n,i<=j<=n)：子问题i~j的根结点

    public OptimalBinarySearchTree(T[] keyWords, Double[] p, Double[] q) {
        if (p.length != q.length - 1 || keyWords.length != p.length)
            throw new IllegalArgumentException("输入参数错误");
        this.keyWords = keyWords;
        this.p = p;
        this.q = q;
        optimalBST(p, q);
    }

    /**
     * 用于返回最优二叉搜索树
     *
     * @return 最优二叉搜索树
     */
    public BinarySearchTree<T> getOptimalBST() {
        return optimalBST;
    }

    /**
     * 根据p，q计算最优二叉搜索树中各个关键字的位置，
     * 自底向上的动态规划，不递归
     *
     * @param p 需要查找的各个关键字的对应的概率，注意关键字必须是按从小到大的顺序排序的
     * @param q 对应的各个伪关键字的概率
     */
    private void optimalBST(Double[] p, Double[] q) {
        int n = p.length;//关键字个数
        Double[][] e = new Double[n + 2][n + 2];
        //e[i][j](1<=i<=n,i<=j<=n)：在包含关键字i~j的子问题中搜索的期望搜索次数，原问题是e[1][p.length]
        //e[i][i-1](1<=i<=n)：子问题只包含伪关键字i-1的期望搜索次数，实际就是q[i-1]
        Double[][] w = new Double[n + 2][n + 2];
        //w[i][j](1<=i<=n,i<=j<=n)：包含关键字i~j的子问题（包含伪关键字i-1~j）的所有关键字和伪关键字的概率和
        //w[i][i-1](1<=i<=n)：子问题只包含伪关键字i-1的概率和，实际就是q[i-1]
        root = new Integer[n + 1][n + 1];
        for (int i = 1; i < n + 2; i++) {
            e[i][i - 1] = q[i - 1];
            w[i][i - 1] = q[i - 1];
        }
        for (int l = 1; l <= n; l++) {//l是子问题的规模
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j - 1] + p[j - 1] + q[j];
                for (int r = i; r <= j; r++) {
                    Double tmp = e[i][r - 1] + e[r + 1][j] + w[i][j];
                    if (tmp < e[i][j]) {
                        e[i][j] = tmp;
                        root[i][j] = r;
                    }
                }
            }
        }
        System.out.println("最优二叉搜索树的期望搜索次数：" + e[1][n]);
        ConstructOptimalBST(1, n);
    }

    /**
     * 根据上面的计算结果，构建最优二叉搜索树
     */
    private void ConstructOptimalBST(int i, int j) {
        if (i >= j) {
            optimalBST.insert(keyWords[root[j][j] - 1]);
        } else {
            optimalBST.insert(keyWords[root[i][j] - 1]);
            ConstructOptimalBST(i, root[i][j] - 1);
            ConstructOptimalBST(root[i][j] + 1, j);
        }
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        String[] keyWords = new String[]{"k1", "k2", "k3", "k4", "k5"};
        Double[] p = new Double[]{0.15, 0.10, 0.05, 0.10, 0.20};
        Double[] q = new Double[]{0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
        OptimalBinarySearchTree<String> optimalBST = new OptimalBinarySearchTree<>(keyWords, p, q);
        BinarySearchTree<String> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree = optimalBST.getOptimalBST();
    }
}
