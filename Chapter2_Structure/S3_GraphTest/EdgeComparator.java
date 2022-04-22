package Chapter2_Structure.S3_GraphTest;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge o1, Edge o2) {
        return o1.weight-o2.weight;
    }
}
