package Chapter2_Structure.S3_GraphTest;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.val-o2.val;
    }
}
