package Chapter6_Hard.Q;

import java.util.*;

public class Q1_SkylineProblem {
    // 数据节点
    public static class Node {
        int x;  // 节点位置
        boolean isAdd; // 是否是增加高度
        int h;  // 增加或减少的高度

        public Node() {
        }

        public Node(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    // 比较器
    /*
        1. 第一个维度x从小到大排序
        2. 如果第一个维度一样，那么第二个维度增加在前，减少在后
        3. 如果前两个维度都一样，那么谁前谁后都一样
     */
    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x == o2.x) {
                return o1.isAdd ? (o2.isAdd ? 0 : 1) : -1;
            }
            return o1.x - o2.x;
        }
    }

    public static List<List<Integer>> buildingOutline(int[][] matrix) {
        Node[] nodes = new Node[matrix.length*2];
        // 每一个大楼轮廓的数组，产生两个描述高度变化的对象
        // 起点处增加一个高度，终点处减少一个高度
        for (int i = 0; i < matrix.length; i++) {
            nodes[i*2] = new Node(matrix[i][0], true, matrix[i][2]);
            nodes[i*2+1] = new Node(matrix[i][1], false, matrix[i][2]);
        }

        // 把描述高度变化的对象数组，按照给定规则排序
        Arrays.sort(nodes, new NodeComparator());

        // TreeMap是java中的有序表，用红黑树实现
        // 高度，次数
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        // 坐标，最大高度
        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();

        for (Node node : nodes) {
            if (node.isAdd) {  // 如果是加入操作
                // 如果是没有出现的高度，直接新加记录
                if (!mapHeightTimes.containsKey(node.h)) {
                    mapHeightTimes.put(node.h, 1);
                } else {  // 出现次数+1
                    mapHeightTimes.put(node.h, mapHeightTimes.get(node.h) + 1);
                }
            } else {
                if (mapHeightTimes.get(node.h) == 1) {  // 如果当前高度出现次数为1，直接删除
                    mapHeightTimes.remove(node.h);
                } else {
                    mapHeightTimes.put(node.h, mapHeightTimes.get(node.h) - 1);
                }
            }

            // 根据mapHeightTimes中的最大高度，设置maxXHeight表
            if (mapHeightTimes.isEmpty()) {  // 如果mapHeightTimes为空，设置高度为0
                mapXHeight.put(node.x, 0);
            } else {  // 否则，通过apHeightTimes.lastKey取得最大高度
                mapXHeight.put(node.x, mapHeightTimes.lastKey());
            }
        }

        // res为结果数组，每个List<Integer>代表一个轮廓线，分别是 起点, 终点, 高度
        List<List<Integer>> res = new ArrayList<>();
        // 一个新的轮廓线开始的位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;
        // 根据mapXHeight生成res数组
        for (Map.Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            // 当前位置
            int curX = entry.getKey();
            // 当前最大高度
            int curHeight = entry.getValue();
            if (preHeight!=curHeight){  // 之前最大高度和现在不一样
                if (preHeight!=0){
                    res.add(new ArrayList<>(Arrays.asList(start, curX, curHeight)));
                }
                start = curX;
                preHeight = curHeight;
            }
        }

        return res;
    }
}
