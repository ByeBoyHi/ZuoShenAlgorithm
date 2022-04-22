package Chapter2_Structure.S2_TreeTest;

import java.util.ArrayList;
import java.util.List;

public class th8_GetPostNode {

    static class Node {
        int val;
        Node left;
        Node right;
        Node parent;

        public Node(int val) {
            this.val = val;
        }
    }

    // 获取指定节点的任一后继节点
    // 方法一：暴力破解，直接中序遍历存入列表，然后对列表取值
    public static Node getPostNode(Node head, Node n1) {
        List<Node> list = new ArrayList<>();
        process(head, list);
        Node cur = null;
        for (Node node: list){
            if (node==n1){
                cur = node;
            }
            if (cur==n1){
                cur = node;
                break;
            }
        }
        return cur;
    }
    public static void process(Node head, List<Node> list){
        if (head==null){
            return;
        }
        process(head.left, list);
        list.add(head);
        process(head.right, list);
    }

    /**
     * 思路：
     * 1. 从指定节点出发，指定节点是叶子，不断的走父节点，只要n1是父节点的右节点，并且父节点又是爷爷节点的右子树，那么不断上传
     * 直到null或者是左子树————因为这个找到的后继节点的前继就是左子树的最右边。
     * 2. 如果指定节点是叶子，且父亲的左节点，那么后继节点就是父节点。  --> 2 可以合到 3 里面，相当于只要不是左孩子，就不断上溯
     * 3. 如果指定节点不是叶子但是又没有右子树，讨论 1 2。
     * 4. 如果指定节点有右子树，那么后继是右子树的最左边的孩子
     */
    public static Node getPostNode2(Node n1){
        if (n1==null) return null;
        if (n1.right!=null){  // 有右子树
            n1 = n1.right;
            while (n1.left!=null){
                n1 = n1.left;
            }
            return n1;
        }else {  // 无右子树
            Node p = n1.parent;
            // 如果依然是右孩子并且父亲依然没有走到null，继续往上走
            // 当走到了null的时候，说明n1是最右边的节点，没有后继节点
            while (p!=null && p.right==n1){
                n1 = p;
                p = n1.parent;
            }
            return p;
        }
    }

}
