package Chapter2_Structure.S3_GraphTest;

import java.util.HashMap;

public class th7_DijkstraByHeap {
    // 用自定义堆结构加速D算法
    /*
        我们需要如下数据的记录：
        1. 记录每个节点的信息：距离和当前值  -- RecordNode
        2. 记录所有点的距离信息：使用节点值数组根据距离构建堆  -- Node：这里使用的之前定义的
        3. 堆结构，用于把所有信息整合起来  --  NodeHeap
        4. 记录每个节点在堆数组的位置
        5. 更新节点的目前的距离，用于求单源最短路径
        6. 弹出堆顶元素后，要交换节点，更新位置
     */

    public static class RecordNode {
        Node node;
        int distance;

        public RecordNode(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private final Node[] nodes;  // 记录所有节点
        private final HashMap<Node, Integer> hashIndexMap;  // 记录每个节点在数组里面的位置
        private final HashMap<Node, Integer> distanceMap;   // 记录当前节点到其他节点的最短距离
        private int size;  // 记录目前已有的节点个数

        public NodeHeap(int capacity) {
            this.nodes = new Node[capacity];
            this.hashIndexMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.size = 0;
        }

        /*
            需要以下方法：
            1. 堆调整函数
            2. 判空函数
            3. 是否加入过堆里面
            4. 目前是否还在堆里面
            5. 弹出堆顶元素
            6. 加入节点后，更新元素
         */
        // 判空操作
        private boolean isEmpty() {
            return this.size == 0;
        }

        // 是否进入过堆
        private boolean isEntered(Node node) {
            return hashIndexMap.containsKey(node);
        }

        // 是否还在堆里面
        private boolean inHeap(Node node) {
            // 默认进入过堆但是已经弹出的节点，hashIndexMap会设置为-1
            return isEntered(node) && hashIndexMap.get(node) != -1;
        }

        // 加入新元素
        /*
            1. 如果元素是新进来的，直接加入
            2. 如果元素之前已经进来了并且在堆里面，那么更新较小值，然后调整堆
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {  // 如果已经在堆里面
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, hashIndexMap.get(node)); // 调整堆
            }
            if (!isEntered(node)) {  // 还没有进来过
                nodes[size++] = node; // 放入堆数组
                distanceMap.put(node, distance); // 放入距离表
                insertHeapify(node, size++); // 调整堆
            }
        }

        /*
            弹出堆元素
         */
        public RecordNode pop() {
            RecordNode node = new RecordNode(nodes[0], distanceMap.get(nodes[0])); // 弹出之后
            swap(0, size - 1);// 交换
            hashIndexMap.put(nodes[size - 1], -1); // 表示来过，但是已经退出堆
            distanceMap.remove(nodes[size - 1]); // 已经确定的点，直接移除
            nodes[size - 1] = null; // 交给GC
            heapify(0, --size); // 堆调整
            return node;
        }

        /*
            交换
         */
        private void swap(int a, int b) {
            hashIndexMap.put(nodes[a], b);
            hashIndexMap.put(nodes[b], a);
            Node temp = nodes[a];
            nodes[a] = nodes[b];
            nodes[b] = temp;
        }

        /*
            堆调整：往上调整
         */
        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        /*
            堆调整：往下调整
         */
        private void heapify(int index, int size) {
            int left = index*2+1; // 左孩子索引
            while (left<size){
                int smallest = left+1<size
                        && distanceMap.get(nodes[left+1])<distanceMap.get(nodes[left])?
                        left+1:left;
                smallest = distanceMap.get(nodes[smallest])<distanceMap.get(nodes[index])?
                        smallest:index;
                if (smallest==index){  // 说明已经是最小了
                    break;
                }
                // 交换并下移一层
                swap(smallest, index);
                index = smallest;
                left = index*2+1;
            }
        }
    }

    /*
        给一个头节点，然后返回这个节点到其他节点的单源最短路径
     */
    public static HashMap<Node, Integer> Dijkstra(Node head, int size){
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> ans = new HashMap<>();
        while (!nodeHeap.isEmpty()){
            RecordNode record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge: cur.edges){
                nodeHeap.addOrUpdateOrIgnore(edge.to, distance+edge.weight);
            }
            ans.put(cur, distance);
        }
        return ans;
    }
}
