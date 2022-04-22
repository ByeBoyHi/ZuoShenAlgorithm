package Chapter2_Structure.S3_GraphTest;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class th5_PrimMST {
    // Prim算法：要求无向图生成最小生成树  -- 存储最小生成树的边
    /*
        从任意一个节点出发
        选择和该点相连的最小权重的边，于此同时另一个相连的点也被合并进来了
        新合并进来的点，判断其是否有新的边进来
        重复以上步骤，直到所有点都在里面
        这样的操作每次都只是 "拉进来一个点" 进来，不会有 K算法 的两个大集合合并的情况。
     */
    public static Set<Edge> PrimMST(Graph graph){
        // 返回的结果
        Set<Edge> ans = new HashSet<>();
        // 用来建立边的小根堆
        PriorityQueue<Edge> heap = new PriorityQueue<>(new EdgeComparator());
        // 用来判断当前节点是否已经访问过
        HashSet<Node> set = new HashSet<>();
        // 如果是单连通的图，那么其实拿其中一个节点就可以走完所有节点
        // 这里的for循环是为了处理森林的出现
        for (Node node: graph.nodes.values()){
            if (!set.contains(node)) {  // 如果还没有访问过这个节点
                set.add(node);  // 注册：也就是出发点，下面只需要判断终点
                heap.addAll(node.edges);
                while (!heap.isEmpty()) {
                    Edge edge = heap.poll();
                    Node toNode = edge.to;
                    if (!set.contains(toNode)){  // 这个点还没有访问过
                        set.add(toNode);
                        ans.add(edge); // 新加入了一个点，那么对应的边也合格，加入结果集合里面
                        heap.addAll(toNode.edges);
                    }
                }
            }
        }
        return ans;
    }
}
