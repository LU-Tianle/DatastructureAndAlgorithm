package graph.search;

import graph.representation.AdjacentList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-25 20:07<p>
 * 图深度优先搜索算法（算法导论22.3），可用于有向图和无向图，
 * 该算法常作为其他算法的子程序，如提取强连通分量和拓扑排序<p>
 * 算法生成深度优先搜索森林（前驱子图）T：<p>
 * // ******************PUBLIC OPERATIONS***************<p>
 * // LinkedList<String> topologicalSort() --> 用于有向无环图的拓扑排序
 */
public class DeepFirstSearch {
    private AdjacentList adjacentList;//图的邻接链表
    private Map<String, Vertex> vertices;//将图的顶点重新封装为对象
    private Integer time = 0;//时间

    public DeepFirstSearch(AdjacentList adjacentList) {
        this.adjacentList = adjacentList;
        this.vertices = new HashMap<>();
        DFS();
    }

    /**
     * 图的顶点类
     */
    private static class Vertex implements Comparable<Vertex> {
        String name;//顶点名
        String color;//用于搜索算法对顶点的标记
        Integer startTime;//发现时间
        Integer endTime;//完成时间
        String parent;//父结点

        Vertex(String name) {
            this.name = name;
            this.color = "white";
        }

        @Override
        public int compareTo(@NotNull Vertex vertex2) {
            if (endTime.compareTo(vertex2.endTime) < 0) {
                return -1;
            } else if (endTime.equals(vertex2.endTime)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 深度优先搜索算法
     */
    private void DFS() {
        for (Object name : adjacentList.getAdjacentList().keySet()) {
            vertices.put((String) name, new Vertex((String) name));
        }
        Set<Map.Entry<String, Vertex>> entrySet = vertices.entrySet();
        for (Map.Entry<String, Vertex> entry : entrySet) {
            if ("white".equals(entry.getValue().color)) {
                DFSVisit(entry.getValue());
            }
        }
    }

    /**
     * 深度优先搜索算法的递归子程序
     */
    private void DFSVisit(Vertex u) {
        time++;
        u.color = "gray";
        u.startTime = time;
        for (String vName : adjacentList.getArrivalVertexes(u.name)) {
            Vertex v = vertices.get(vName);
            if ("white".equals(v.color)) {
                v.parent = u.name;
                DFSVisit(v);
            }
        }
        time++;
        u.color = "black";
        u.endTime = time;
    }

    /**
     * 用于有向无环图的拓扑排序
     *
     * @return 排序后的顶点名数组
     */
    public String[] topologicalSort() {
        List<Vertex> list = new LinkedList<>(vertices.values());
        String[] sortedVertexes = new String[list.size()];
        Collections.sort(list);
        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++) {
            sortedVertexes[i] = list.get(i).name;
        }
        return sortedVertexes;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        AdjacentList adjacentList = new AdjacentList(new String[]{"u", "v", "w", "x", "y", "z"},
                new String[][]{{"x", "v"}, {"y"}, {"y", "z"}, {"v"}, {"x"}, {"z"}});
        DeepFirstSearch deepFirstSearch = new DeepFirstSearch(adjacentList);
        System.out.print("拓扑排序结果：");
        for (String s : deepFirstSearch.topologicalSort()) {
            System.out.print(s + " ");
        }

    }
}
