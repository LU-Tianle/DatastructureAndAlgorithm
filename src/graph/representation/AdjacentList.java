package graph.representation;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.<p>
 * Author: Lu Tianle<p>
 * Date: 2018-02-25 20:07<p>
 * 图的邻接链表表示<p>
 * // ******************PUBLIC OPERATIONS*********************<p>
 * // HashMap<String, LinkedList<Vertex>> getAdjacentList()                                     --> 返回邻接链表
 * // Boolean addVertex(String vertexName, String[] arrivalVertexNames, T[] distances)          --> 在图的邻接链表中增加一个顶点和它所能到达的顶点<p>
 * // Boolean addArrivalVertex2Vertex(String vertexName, String arrivalVertexName, T distance)  --> 对于图的邻接链表中已有的一个顶点，增加一个它所能到达的顶点<p>
 * // Boolean removeVertex(String vertexName)                                                   --> 从邻接链表中删除一个顶点<p>
 * // Boolean removeArrivalVertexFromVertex(String vertexName, String arrivalVertexName)        --> 删除某个顶点的一个到达顶点<p>
 * // Boolean add2ArrivalVertexes(String arrivalVertex, String[] targetVertexes)                --> 将一个结点作为到达结点插入到多个结点的到达结点链表中
 * // AdjacentMatrix transposeDigraph()                                                         --> 返回有向图的转置图<p>
 */
public class AdjacentList<T extends Number> {
    private Map<String, LinkedList<Vertex>> adjacentList = new HashMap<>();
    public String type;//有向图还是无向图
    //图的邻接链表表示，键是顶点名，值是链表：存储该顶点可以到达的顶点对象，顶点对象存储可以到达的顶点的名称和键顶点到该顶点的权值

    /**
     * @return 返回邻接链表
     */
    public Map<String, LinkedList<Vertex>> getAdjacentList() {
        return adjacentList;
    }

    /**
     * 给定一个顶点，返回其所有可达顶点
     *
     * @param vertexName 给定一个顶点名
     * @return 该顶点的所有可达顶点
     */
    public String[] getArrivalVertexes(String vertexName) {
        String[] arrivalVertexes = new String[adjacentList.get(vertexName).size()];
        int i = 0;
        for (Vertex vertex : adjacentList.get(vertexName)) {
            arrivalVertexes[i++] = vertex.name;
        }
        return arrivalVertexes;
    }

    /**
     * 构造函数
     *
     * @param vertexes 顶点名称构成的数组
     */
    public AdjacentList(String[] vertexes) {
        for (String vertex : vertexes) {
            adjacentList.put(vertex, new LinkedList<>());
        }
    }

    /**
     * 构造函数
     *
     * @param vertexes        顶点名称构成的数组
     * @param arrivalVertexes 各顶点对应的到达顶点
     * @param distances       顶点到其各到达顶点对应的权值
     */
    public AdjacentList(String[] vertexes, String[][] arrivalVertexes, T[][] distances) {
        if (vertexes.length != arrivalVertexes.length) {
            throw new IllegalArgumentException("顶点数与到达顶点数组中的顶点数不一致");
        } else if (vertexes.length != distances.length) {
            throw new IllegalArgumentException("顶点数与权值数组中的顶点数不一致");
        } else {
            for (int i = 0; i < arrivalVertexes.length; i++) {
                if (arrivalVertexes[i].length != distances[i].length) {
                    throw new IllegalArgumentException("顶点 " + vertexes[i] + " 的到达顶点数与权值数量不一致");
                }
            }
        }
        for (int i = 0; i < vertexes.length; i++) {
            addVertex(vertexes[i], arrivalVertexes[i], distances[i]);
        }
    }

    /**
     * 构造函数，权值默认是1
     *
     * @param vertexes        顶点名称构成的数组
     * @param arrivalVertexes 各顶点对应的到达顶点
     */
    @SuppressWarnings("unchecked")
    public AdjacentList(String[] vertexes, String[][] arrivalVertexes) {
        if (vertexes.length != arrivalVertexes.length) {
            throw new IllegalArgumentException("顶点数与到达顶点数组中的顶点数不一致");
        }
        Integer[][] distances = new Integer[arrivalVertexes.length][];
        for (int i = 0; i < arrivalVertexes.length; i++) {
            distances[i] = new Integer[arrivalVertexes[i].length];
            for (int j = 0; j < arrivalVertexes[i].length; j++) {
                distances[i][j] = 1;
            }
        }
        for (int i = 0; i < vertexes.length; i++) {
            addVertex(vertexes[i], arrivalVertexes[i], (T[]) distances[i]);
        }
        this.type = type();
    }

    /**
     * 顶点类，存储顶点名，和到这个顶点的权值
     */
    private class Vertex {
        String name;//顶点名
        T distance;//到这个顶点的权值

        Vertex(String name, T distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    /**
     * 在图的邻接链表中增加一个顶点和它所能到达的顶点
     *
     * @param vertexName         需要增加的顶点
     * @param arrivalVertexNames 它所能到达的顶点数组
     * @param distances          它到上述顶点的权值数组
     * @return 如果成功增加该顶点则返回true
     */
    public Boolean addVertex(String vertexName, String[] arrivalVertexNames, T[] distances) {
        if (arrivalVertexNames.length != distances.length) {
            throw new IllegalArgumentException("可到达的顶点数组长度与权值数组长度不一致");
        }
        LinkedList<Vertex> arrivalVertexList = new LinkedList<>();
        for (int i = 0; i < arrivalVertexNames.length; i++) {
            arrivalVertexList.add(new Vertex(arrivalVertexNames[i], distances[i]));
        }
        adjacentList.put(vertexName, arrivalVertexList);
        return true;
    }

    /**
     * 对于图的邻接链表中已有的一个顶点，增加一个它所能到达的顶点
     *
     * @param vertexName        图的邻接链表中已有的一个顶点
     * @param arrivalVertexName 增加的一个它所能到达的顶点
     * @param distance          它到该顶点权值
     * @return 如果成功增加则返回true
     */
    public Boolean addArrivalVertex2Vertex(String vertexName, String arrivalVertexName, T distance) {
        LinkedList<Vertex> arrivalVertexList = adjacentList.get(vertexName);
        if (arrivalVertexList == null) {
            throw new IllegalArgumentException("该顶点不存在");
        }
        for (int i = 0; i < arrivalVertexList.size(); i++) {
            if (vertexName.equals(arrivalVertexList.get(i).name)) {
                arrivalVertexList.remove(i);
                break;
            }
        }
        arrivalVertexList.add(new Vertex(arrivalVertexName, distance));
        return true;
    }

    /**
     * 从邻接链表中删除一个顶点
     *
     * @param vertexName 需要删除的顶点
     * @return 如果成功删除则返回true，该顶点不存在则返回false
     */
    public Boolean removeVertex(String vertexName) {
        if (adjacentList.get(vertexName) == null) {
            return false;
        }
        adjacentList.remove(vertexName);
        Set<Map.Entry<String, LinkedList<Vertex>>> entrySet = adjacentList.entrySet();
        for (Map.Entry<String, LinkedList<Vertex>> entry : entrySet) {
            LinkedList<Vertex> vertices = entry.getValue();
            for (int i = 0; i < vertices.size(); i++) {
                if (vertexName.equals(vertices.get(i).name)) {
                    vertices.remove(i);
                    break;
                }
            }
        }
        return true;
    }

    /**
     * 删除某个顶点的一个到达顶点
     *
     * @param vertexName        从该顶点的到达顶点中删除
     * @param arrivalVertexName 需要删除的到达顶点
     * @return 如果成功删除则返回true，该顶点或到达顶点不存在则返回false
     */
    public Boolean removeArrivalVertexFromVertex(String vertexName, String arrivalVertexName) {
        LinkedList<Vertex> arrivalVertexList = adjacentList.get(vertexName);
        if (arrivalVertexList == null) {
            return false;
        } else {
            for (int i = 0; i < arrivalVertexList.size(); i++) {
                if (arrivalVertexName.equals(arrivalVertexList.get(i).name)) {
                    arrivalVertexList.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 将一个结点作为到达结点插入到多个结点的到达结点链表中，如果该到达结点已存在，则更新其距离
     *
     * @param arrivalVertex  将该结点作为到达结点
     * @param targetVertexes 将arrivalVertex插入到这些结点的到达结点链表中
     * @return 如果成功插入到多个结点的到达结点链表中则返回true
     */
    public Boolean add2ArrivalVertexes(String arrivalVertex, String[] targetVertexes, T[] distances) {
        if (targetVertexes.length != distances.length) {
            throw new IllegalArgumentException("目标结点数与距离数不一致");
        } else if (adjacentList.get(arrivalVertex) == null) {
            throw new IllegalArgumentException("要插入的到达结点不存在");
        }
        for (int i = 0; i < targetVertexes.length; i++) {
            LinkedList<Vertex> targetList = adjacentList.get(targetVertexes[i]);
            for (int j = 0; j < targetList.size(); j++) {
                if (arrivalVertex.equals(targetList.get(i).name)) {
                    targetList.remove(i);
                    break;
                }
            }
            targetList.add(new Vertex(arrivalVertex, distances[i]));
        }
        return true;
    }

    /**
     * 打印这个邻接链表
     */
    public void printAdjacentList() {
        Set<Map.Entry<String, LinkedList<Vertex>>> entrySet = adjacentList.entrySet();
        for (Map.Entry<String, LinkedList<Vertex>> entry : entrySet) {
            System.out.print(entry.getKey() + ": ");
            for (Vertex vertex : entry.getValue()) {
                System.out.print(vertex.name + "(" + vertex.distance + ")" + ", ");
            }
            System.out.println();
        }
    }

    /**
     * @return 有向图的转置图
     */
    public AdjacentList<T> transposeDigraph() {
        if ("graph".equals(type)) {
            return this;
        }
        AdjacentList<T> transposeDigraph = new AdjacentList<>(adjacentList.keySet().toArray(new String[adjacentList.size()]));
        Set<Map.Entry<String, LinkedList<Vertex>>> entrySet = adjacentList.entrySet();
        for (Map.Entry<String, LinkedList<Vertex>> entry : entrySet) {
            LinkedList<Vertex> list = entry.getValue();
            if (list == null) {
                continue;
            }
            String[] targetVertexes = new String[list.size()];
            @SuppressWarnings("unchecked")
            T[] distances = (T[]) Array.newInstance(list.get(0).distance.getClass(), list.size());

            for (int i = 0; i < targetVertexes.length; i++) {
                targetVertexes[i] = list.get(i).name;
                distances[i] = list.get(i).distance;
            }
            transposeDigraph.add2ArrivalVertexes(entry.getKey(), targetVertexes, distances);

        }
        return transposeDigraph;
    }

//    /**
//     * 比较两个图（的邻接链表）是否相同
//     *
//     * @param adjacentList2 另一个邻接链表
//     * @return 相同：true，不同：false
//     */
//    public boolean equals(AdjacentList<T> adjacentList2) {
//

//    }

    /**
     * 测试图是有向图还是无向图
     *
     * @return digraph or graph
     */
    private String type() {
        if (this.transposeDigraph().equals(this)) {
            return "graph";
        } else {
            return "digraph";
        }
    }

    public static void main(String[] args) {
        AdjacentList adjacentList = new AdjacentList(new String[]{"u", "v", "w", "x", "y", "z"},
                new String[][]{{"x", "v"}, {"y"}, {"y", "z"}, {"v"}, {"x"}, {"z"}});
        System.out.println("邻接链表");
        adjacentList.printAdjacentList();
        AdjacentList transpose = adjacentList.transposeDigraph();
        System.out.println("转置图的邻接链表");
        transpose.printAdjacentList();
    }
}