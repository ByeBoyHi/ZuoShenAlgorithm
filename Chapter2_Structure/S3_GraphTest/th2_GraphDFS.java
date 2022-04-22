package Chapter2_Structure.S3_GraphTest;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

// 深度优先遍历：一条路走到底
public class th2_GraphDFS {
    /**
     * 一条路走到底，需要在进入一个新的节点的时候，要把这个节点的父节点也放进去
     * 这样在新节点走完之后回到父节点，父节点可以再继续走别的路
     * 用一个栈来存储，在当前节点，出去又回来的时候，要注意和子节点的入栈顺序
     */
    public static void DFS(Node node){
        if (node==null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            // 处理当前节点
            System.out.println(cur.val);
            for (Node n: cur.nexts){
                if (!set.contains(n)){
                    set.add(n);
                    stack.push(cur);
                    stack.push(n);
                    break;
                }
            }
        }
    }
}
