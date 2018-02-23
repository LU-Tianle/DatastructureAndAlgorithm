package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-20<p>
 * 图广度优先搜索算法（算法导论22.2），可用于有向图和无向图<p>
 * 图的顶点用0~|V|-1（连续整数）编号，算法的输入是图的邻接链表和一个顶点（源点）<p>
 * 算法计算从源点出发到其他顶点的距离，如果距离是null则该顶点不可达<p>
 * 算法生成广度优先搜索树T：<p>
 * T以源点s为根，T的任意结点v是G中从s出发可以到达的，T中s到v的距离就是G中s到v的最短路径<p>
 * // ******************PUBLIC OPERATIONS***************<p>
 * // Integer getVertexDepth(Integer vertex)  -->  返回参数结点到源点的距离<p>
 * // void printAllDepth()                    -->  打印所有顶点到源点的距离<p>
 */

public class BreadthFirstSearch {
    private ArrayList<Integer>[] adjacencyList;//图G的邻接链表
    private Integer sourceVertex;// 源点s
    private Vertex[] vertices;//将图的顶点封装为对象，放在这个数组中

    /**
     * 构造函数
     *
     * @param adjacencyList 图G的邻接链表，图的顶点用0~|V|-1（连续的整数）编号
     * @param sourceVertex  源点s
     */
    public BreadthFirstSearch(ArrayList<Integer>[] adjacencyList, Integer sourceVertex) {
        if (sourceVertex <= 0 || sourceVertex > adjacencyList.length) {
            throw new IllegalArgumentException("源点是大于0，小于等于顶点数的整数");
        }
        this.adjacencyList = adjacencyList;
        this.sourceVertex = sourceVertex;
        vertices = new Vertex[adjacencyList.length];
        BFS();
    }

    /**
     * 图的顶点类
     */
    private static class Vertex {
        Integer name;//顶点的编号
        String color;//用于搜索算法对顶点的标记
        Integer depth;//顶点相对于源点的距离
        Integer parent;//父结点

        public Vertex(Integer name) {
            this.name = name;
            this.color = "white";
            this.depth = null;
            this.parent = null;
        }
    }

    /**
     * 广度优先搜索算法
     */
    private void BFS() {
        for (int i = 0; i < adjacencyList.length; i++) {
            vertices[i] = new Vertex(i);
        }
        vertices[sourceVertex].color = "gray";
        vertices[sourceVertex].depth = 0;
        List<Vertex> queue = new LinkedList<>();//用链表实现一个队列
        queue.add(vertices[sourceVertex]);
        while (!queue.isEmpty()) {
            Vertex u = queue.remove(0);
            for (Integer v : adjacencyList[u.name]) {
                if (vertices[v].color.equals("white")) {
                    vertices[v].parent = u.name;
                    vertices[v].color = "gray";
                    vertices[v].depth = u.depth + 1;
                    queue.add(vertices[v]);
                }
            }
            u.color = "black";
        }
    }

    /**
     * 打印一个顶点相对于源点的距离，和最短路径
     *
     * @param vertex 顶点
     */
    public void getVertexDepth(Integer vertex) {
        System.out.println("源点：" + sourceVertex);
        if (vertices[vertex].depth == null) {
            System.out.print("，顶点" + vertex + "不可达");
        } else {
            System.out.println("到顶点" + vertex + "的距离是：" + vertices[vertex].depth);
            System.out.println("最短路径是：" + vertex);
            while (!vertices[vertex].parent.equals(sourceVertex)) {
                System.out.print(vertices[vertex] + " ");
            }
        }
    }

//    /**
//     * 打印所有顶点到源点的距离
//     */
//    public void printAllDepth() {
//        System.out.println("源点：" + sourceVertex);
//
//    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adjacencyList = (ArrayList<Integer>[]) new ArrayList[8];
        for (int i = 0; i < 8; i++) {
            adjacencyList[i] = new ArrayList<>();
        }


//        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(adjacencyList,)
    }
}




