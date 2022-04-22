package Chapter2_Structure.S3_GraphTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class th4_KruskalMST {
    // K算法思路：要求无向图
    /*
        每次都是选取 "最短的一条边"  --> 这种是可能出现两个大集合的合并的
        使得最后选的所有边把所有的点合在一起，且没有环路
        且保证权重开销最小
        ******
        使用一个小根堆，对边进行权重从小到大的建立
        使用一个集合Set对每一个合格的边进行保存
        使用我们自定义的MySets，判断当添加这条边之后，两个集合时候会形成环路
        只有当两个集合是同一个集合的时候，才会生成环路
        可以添加这条边的时候，合并这两个集合
     */
    public static Set<Edge> kruskalMST(Graph graph){
        Set<Edge> ans = new HashSet<>();
        PriorityQueue<Edge> heap = new PriorityQueue<>(new EdgeComparator());
        // 建立边的小根堆
        heap.addAll(graph.edges);  // n 条边
        MySets sets = new MySets(new ArrayList<>(graph.nodes.values())); // 自成一个集合
        while (!heap.isEmpty()){  // n
            Edge edge = heap.poll();  // log n
            Node fromNode = edge.from;
            Node toNode = edge.to;
            if (!sets.isSameSet(fromNode, toNode)){  // 只要不是同一个集合
                sets.union(fromNode, toNode); // m个节点  // 就合并到一起
                ans.add(edge);  // 把这个边加进去
            }
        }
        return ans;
    }
}
