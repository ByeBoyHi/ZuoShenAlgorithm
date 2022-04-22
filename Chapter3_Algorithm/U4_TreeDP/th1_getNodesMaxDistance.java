package Chapter3_Algorithm.U4_TreeDP;

import java.util.Objects;

public class th1_getNodesMaxDistance {
    /*
        Q1：求整棵树的最大距离——任意两个节点相遇，路过的节点个数，就是距离值
        DP构造：求出当前节点为根的时候，整个树的最大距离是多少
        求任意两个节点的距离，那么有以下情况：
        1. 头节点不参与。当前节点的最大距离是(左子树两个节点的最大距离)或者(右子树两个节点的最大距离)
        2. 头节点参与。当前节点的最大距离是
            (左子树上离我最远的节点+右子树上离我最远的节点+1) = (左高+右高+1)
        以上是以头参与或者不参与进行可能性分类。（只是其中一种可能性分类）
     */
    static class ReturnType{
        public int maxDistance;
        public int Height;
        public ReturnType(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            Height = height;
        }
    }
    static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
        }
    }

    // 返回以node为头的整棵树的两个信息
    public static ReturnType process(Node node){
        if (node==null){
            return new ReturnType(0,0);
        }

        ReturnType left = process(node.left);
        ReturnType right = process(node.right);

        int p1 = left.maxDistance;
        int p2 = right.maxDistance;
        int p3 = left.Height+right.Height+1;

        int maxDistance = Math.max(p3, Math.max(p1, p2));
        int Height = Math.max(left.Height, right.Height)+1;
        return new ReturnType(maxDistance, Height);
    }
    public static int maxDistance(Node head){
        return Objects.requireNonNull(process(head)).maxDistance;
    }
}
