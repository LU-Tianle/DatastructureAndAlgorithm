package graph.representation;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-25 19:58<p>
 * 图的邻接矩阵表示，图的顶点用从<strong>0开始的连续整数</strong>编号<p>
 * // ******************PUBLIC OPERATIONS*********************<p>
 * // void void printAdjacentMatrix() --> 打印邻接矩阵<p>
 * // AdjacentMatrix transposeDegraph() --> 返回有向图的转置图<p>
 */
public class AdjacentMatrix<T extends Number> {
    private T[][] adjacentMatrix;//图的邻接矩阵
    public String type;//有向图还是无向图

    /**
     * 注意图的顶点用从<strong>0开始的连续整数</strong>编号
     *
     * @param adjacentMatrix 图的邻接矩阵，
     */
    public AdjacentMatrix(T[][] adjacentMatrix) {
        if (adjacentMatrix.length != adjacentMatrix[0].length) {
            throw new IllegalArgumentException("邻接矩阵必须是方阵");
        }
        this.adjacentMatrix = adjacentMatrix;
        this.type = type();
    }

    /**
     * 打印邻接矩阵
     */
    public void printAdjacentMatrix() {
        System.out.println("邻接矩阵：");
        for (int i = 0; i < adjacentMatrix.length; i++) {
            for (int j = 0; j < adjacentMatrix[i].length; j++) {
                System.out.print(adjacentMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 测试图是有向图还是无向图
     *
     * @return digraph or graph
     */
    private String type() {
        for (int i = 0; i < adjacentMatrix.length; i++) {
            for (int j = i + 1; j < adjacentMatrix[0].length; j++) {
                if (!adjacentMatrix[i][j].equals(adjacentMatrix[j][i])) {
                    return "digraph";
                }
            }
        }
        return "graph";
    }

    /**
     * @return 有向图的转置图
     */
    public AdjacentMatrix<T> transposeDegraph() {
        if ("graph".equals(type)) {
            return this;
        }
        @SuppressWarnings("unchecked")
        T[][] transposeMatrix = (T[][]) Array.newInstance(adjacentMatrix.getClass(), adjacentMatrix.length, adjacentMatrix[0].length);
        for (int i = 0; i < adjacentMatrix.length; i++) {
            for (int j = 0; j < adjacentMatrix[0].length; j++) {
                transposeMatrix[i][j] = adjacentMatrix[j][i];
            }
        }
        return new AdjacentMatrix<>(transposeMatrix);
    }
}
