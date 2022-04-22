package Chapter2_Structure.S3_GraphTest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// 广度优先遍历：一层一层走
public class th1_GraphBFS {

    /**
     * 从一个节点触发，用一个队列，出一个头节点，然后依次入他相连的其他点，并且用一个Set判断这个点是否重复处理过
     */
    public static void BFS(Node node){
        if (node==null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            for (Node n :cur.nexts){
                if (!set.contains(n)){
                    queue.add(n);
                    set.add(n);
                }
            }
            // 处理一下当前节点
            System.out.println(cur.val);
        }
    }
}
