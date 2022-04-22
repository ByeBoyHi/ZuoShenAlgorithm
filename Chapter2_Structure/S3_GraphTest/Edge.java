package Chapter2_Structure.S3_GraphTest;

public class Edge {
    // 图的边集类：入的点，出的点，权重
    Node from;
    Node to;
    int weight;

    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
