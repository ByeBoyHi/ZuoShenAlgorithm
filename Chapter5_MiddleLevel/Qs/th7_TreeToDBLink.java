package Chapter5_MiddleLevel.Qs;

import java.util.ArrayDeque;
import java.util.Queue;

public class th7_TreeToDBLink {
    static class Node{
        int number;
        Node left, right;
        public Node(int number){
            this.number = number;
        }
        public Node(){}
    }

    // 用一个类来记录单链表的开始节点和结束节点
    // 用这个类做双向
    static class Info{
        Node start, end;
        public Info(Node start, Node end){
            this.start = start;
            this.end = end;
        }
    }

    // 将一个二叉树转换为有序的双向链表，并且返回头节点。
    // 假设二叉树是中序遍历顺序
    public static Info generateOne(Node X){
        if (X==null){ // 叶子的下面
            return new Info(null, null);
        }
        Info leftEnd = generateOne(X.left);
        Info rightEnd = generateOne(X.right);
        if (leftEnd.end!=null){
            leftEnd.end.right = X;
        }
        X.left = leftEnd.end;
        X.right = rightEnd.start;
        if (rightEnd.start!=null){
            rightEnd.start.left = X;
        }
        return new Info(leftEnd.start!=null?leftEnd.start:X,
                rightEnd.end!=null?rightEnd.end:X);
    }

    static Queue<Node> queue = new ArrayDeque<>();
    // 我自己的方法
    public static void f(Node head){
        if (head.left!=null){
            queue.add(head.left);
            f(head.left);
        }
        queue.add(head);
        if (head.right!=null){
            queue.add(head.right);
            f(head.right);
        }
    }
    // transfer
    public static Node transfer(){
        Node head = null;
        // 用完就弹完
        if (!queue.isEmpty()){
            head = queue.poll();
            Node cur = head;
            while (!queue.isEmpty()){
                cur.left = queue.peek();
                queue.peek().right = cur;
                cur = queue.poll();
            }
        }
        // 如果原来的数据还用的话，可以边弹边入，记录不等于头节点为结束条件
        return head;
    }
}
