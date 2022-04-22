package Chapter2_Structure.S3_GraphTest;

import java.util.*;

public class th3_Topology {
    // graph no loop
    public static List<Node> sortedTopology(Graph graph){
        // 存放结果
        List<Node> ans = new ArrayList<>();
        // 记录每个点的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 入度为0的入队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node: graph.nodes.values()){
            inMap.put(node, node.in);  // 对每个点的入度做记录
            if (node.in==0){
                zeroInQueue.add(node);
            }
        }
        // 开始对入度为0的节点进行操作
        while (!zeroInQueue.isEmpty()){
            Node cur = zeroInQueue.poll();
            ans.add(cur);
            for (Node node: cur.nexts){
                // 让当前节点的所有指向节点的入度-1，就消除了当前节点的影响，相当于间接删除该节点
                inMap.put(node, inMap.get(node)-1);
                if (inMap.get(node)==0){
                    zeroInQueue.add(node);
                }
            }
        }
        return ans;
    }
}
