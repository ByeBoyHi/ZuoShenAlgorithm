package Chapter2_Structure.S3_GraphTest;

import java.util.ArrayList;
import java.util.List;

public class Node {
    // 图的节点类：入度、出度、指向的节点集、边集、自己的值
    int val;
    int in;
    int out;
    List<Node> nexts;
    List<Edge> edges;

    public Node(int val) {
        this.val = val;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
