package Chapter2_Structure.S2_TreeTest;

import java.util.Stack;

public class th5_JudgeFull {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.left = node2;
//        node1.right = node3;
//        node2.left = node4;
//        node3.right = node5;
        System.out.println(th5_JudgeFull.isFull2(node1));
    }

    // 判断一棵树是不是满二叉树

    // 方法二：宽度优先遍历
    // 思路：出现一下情况，则不是满二叉树
    // 1. 出现了单孩子情况。
    // 2. 在出现叶子节点之后，又出现了有孩子的情况
    public static boolean isFull2(Node head){
        if (head!=null){
            boolean leaf = false; // 记录是否到达叶子节点
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()){
                head = stack.pop();
                if (!leaf) {  // 还没出现叶子
                    if (head.left != null && head.right != null) {
                        stack.push(head.right);
                        stack.push(head.left);
                    } else if (head.left == null && head.right == null) {
                        leaf = true; // 来到叶子节点
                    } else {  // 出现了单孩子情况
                        return false;
                    }
                }else {  // 后面都应该是叶子
                    if (head.left!=null || head.right!=null){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    // 方法一：先计算树的深度L，然后计算树的节点个数N，有 N=2^L-1
    // 方法三：运用DP结构，存储左右子树的节点个数，以及树的高度
    public static class ReturnType {
        int height;  // 这个节点的高度
        int nodes;  // 这个所在子树的节点个数

        public ReturnType(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static boolean isFull(Node head) {
        ReturnType process = process(head);
        return process.nodes == (1 << process.height) - 1;
    }

    public static ReturnType process(Node head) {
        if (head == null) {
            return new ReturnType(0, 0);
        }
        ReturnType leftTree = process(head.left);
        ReturnType rightTree = process(head.right);
        int height = Math.max(leftTree.height, rightTree.height) + 1;
        int nodes = leftTree.nodes + rightTree.nodes + 1;
        return new ReturnType(height, nodes);
    }
}
