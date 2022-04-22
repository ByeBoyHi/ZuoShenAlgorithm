package Chapter2_Structure.S3_GraphTest;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    // 图类：使用节点类和边集类
    // 编号：节点
    HashMap<Integer, Node> nodes;
    // 边集
    HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    // 给一个图的邻接矩阵
    // 假设邻接矩阵的结构：[起点，终点，权重]*n
    public static Graph createGraph(int[][] matrix){
        Graph graph = new Graph();
        for (int[] ints : matrix) {
            Integer from = ints[0];
            Integer to = ints[1];
            int weight = ints[2];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            Edge edge = new Edge(fromNode, toNode, weight);
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
