package graph.search;

import graph.representation.AdjacentList;

import java.util.*;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-20<p>
 * 图广度优先搜索算法（算法导论22.2），可用于有向图和无向图<p>
 * 算法计算从源点出发到其他顶点的距离，如果距离是null则该顶点不可达<p>
 * 算法生成广度优先搜索树（前驱子图）T：<p>
 * T以源点s为根，T的任意结点v是G中从s出发可以到达的，T中s到v的距离就是G中s到v的最短路径<p>
 * // ******************PUBLIC OPERATIONS***************<p>
 * // Integer getVertexDepth(Integer vertex)  -->  返回参数结点到源点的距离<p>
 * // void printAllDepth()                    -->  打印所有顶点到源点的距离<p>
 */

public class BreadthFirstSearch {
    private String sourceVertex;// 源点s
    private Map<String, Vertex> vertices;//将图的顶点重新封装为对象

    /**
     * 构造函数
     *
     * @param adjacentList 图G的邻接链表
     * @param sourceVertex 源点s
     */
    public BreadthFirstSearch(AdjacentList adjacentList, String sourceVertex) {
        if (adjacentList.getAdjacentList().get(sourceVertex) == null) {
            throw new IllegalArgumentException("该源点不存在");
        }
        this.sourceVertex = sourceVertex;
        vertices = new HashMap<>();
        BFS(adjacentList);
    }

    /**
     * 图的顶点类
     */
    private static class Vertex {
        String name;//顶点名
        String color;//用于搜索算法对顶点的标记
        Integer depth;//顶点相对于源点的距离
        String parent;//父结点

        Vertex(String name) {
            this.name = name;
            this.color = "white";
        }
    }

    /**
     * 广度优先搜索算法
     *
     * @param adjacentList 图的邻接链表
     */
    private void BFS(AdjacentList adjacentList) {
        for (Object name : adjacentList.getAdjacentList().keySet()) {
            vertices.put((String) name, new Vertex((String) name));
        }
        vertices.get(sourceVertex).color = "gray";
        vertices.get(sourceVertex).depth = 0;
        List<Vertex> queue = new LinkedList<>();//用链表实现一个队列
        queue.add(vertices.get(sourceVertex));
        while (!queue.isEmpty()) {
            Vertex u = queue.remove(0);
            for (String v : adjacentList.getArrivalVertexes(u.name)) {
                if (vertices.get(v).color.equals("white")) {
                    vertices.get(v).parent = u.name;
                    vertices.get(v).color = "gray";
                    vertices.get(v).depth = u.depth + 1;
                    queue.add(vertices.get(v));
                }
            }
            u.color = "black";
        }
    }

    /**
     * 返回一个顶点相对于源点的距离
     *
     * @param vertex 顶点
     */
    public Integer getVertexDepth(String vertex) {
        return vertices.get(vertex).depth;
    }

    /**
     * 打印所有顶点到源点的距离
     */
    public void printAllDepth() {
        System.out.println("源点：" + sourceVertex);
        Set<Map.Entry<String, Vertex>> entrySet = vertices.entrySet();
        for (Map.Entry<String, Vertex> entry : entrySet) {
            Integer depth = getVertexDepth(entry.getKey());
            if (depth == null) {
                System.out.print("顶点" + entry.getKey() + "不可达");
            } else {
                System.out.print("到顶点" + entry.getKey() + "的距离是：" + depth);
                System.out.print("，最短路径是：");
                Vertex vertex = vertices.get(entry.getKey());
                while (vertex.parent != null) {
                    System.out.print(vertex.name + " ");
                    vertex = vertices.get(vertex.parent);
                }
                System.out.println(sourceVertex);
            }
        }
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        AdjacentList adjacentList = new AdjacentList(new String[]{"v", "r", "s", "w", "t", "x", "u", "y"},
                new String[][]{{"r"}, {"v", "s"}, {"r", "w"}, {"s", "t", "x"}, {"w", "x", "u"}, {"w", "t", "u", "y"}, {"t", "x", "y"}, {"x", "u"}});
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(adjacentList, "s");
        breadthFirstSearch.printAllDepth();
    }
}