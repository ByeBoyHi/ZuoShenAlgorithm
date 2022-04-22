package Chapter2_Structure.S3_GraphTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class th6_Dijkstra {
    /*
        Dijkstra算法：单源最短路径算法 -->  要求权值没有负数
        要做的事：生成每个节点到其他节点的最短路径长度
        思路：假设从某一个点A出发。
        定义该点到其他所有点的距离都是无穷，到自己是0。
        选择A相连的边的值，判断是否会有距离更新，只要小于已经记录的值就更新。
        更新完一遍后，A到A的位置就锁死了，因为我们从A出发的点已经看完了。
        接下来在剩下的点里面找一个最小的值，重复上面的操作。
        当新的点走到另一个点的距离加上A走到这个点的距离小于A走到另一个点的距离，那么就更新。
        以此类推。
        直到锁死所有点关于A的记录，那么A的所有单源最短路径就找完了。

        D算法可以处理有负数权值的边的图，因为每一次判断一个点的时候，他都会去看前面锁死的点是不是最优解
        但是不能在有负数的时候，对负数所在的边形成环路，且这个环路和是负数
        因为如果这个环路和是负数，那么你没在那里转一圈，都会减小，最后一直转，就会出现无穷小
        对于我们的需求就没有意义了
     */

    /**
     * 从一个集合里面找出一个距离最小的节点，并且这个节点是没有被锁的
     * @param distanceMap  已有集合找距离最小的节点
     * @param touchedNode   已经锁住的节点集合
     * @return              返回找到的最小距离的节点，如果节点为null，说明所有节点都被锁死了
     */
    public static Node getMinDistanceAndUnselectedNode(
            HashMap<Node,Integer> distanceMap,
            HashSet<Node> touchedNode
    ){
        // 需要返回的最短距离
        Node ans = null;
        int minDis = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node cur = entry.getKey();
            int dis = entry.getValue();
            if (!touchedNode.contains(cur) && dis<minDis){
                minDis = dis;
                ans = cur;
            }
        }
        return ans;
    }

    // 经典D算法

    /**
     * 从任意指定的节点出发，计算从该节点到其他各个节点的最短路径
     * @param head  指定的出发节点
     * @return      返回的指定节点到目标节点的路径映射，Node是终点，Integer是最短路径长度
     */
    public static HashMap<Node, Integer> Dijkstra(Node head){
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        // 到自己的距离是0
        distanceMap.put(head, 0);
        // 用一个Set来锁死已经确定的路径
        HashSet<Node> touchedNode = new HashSet<>();
        // 获取目前映射里面的最小的值，作为新的出发点，没有的点被默认为无穷，因为还没访问过
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, touchedNode);
        while (minNode!=null){
            int distance = distanceMap.get(minNode);  // 取到到达当前点的最短距离
            for (Edge edge: minNode.edges){  // 遍历该点的所有边
                Node node = edge.to;
                if (!distanceMap.containsKey(node)){
                    // 如果这个点还没有去过
                    // 那么更新距离是当前节点的最短距离+边的权重
                    distanceMap.put(node, distance+edge.weight);
                }else { // 如果这个点已经记录过了，那么我们就比较一下新的距离和之前的距离谁更小，更新一下
                    distanceMap.put(node, Math.min(distance+edge.weight,
                            distanceMap.get(node)));
                }
            }
            // 当前节点访问完了，注册一下
            touchedNode.add(minNode);
            // 找新的最短距离的节点
            minNode = getMinDistanceAndUnselectedNode(distanceMap, touchedNode);
        }
        return distanceMap;
    }


}
