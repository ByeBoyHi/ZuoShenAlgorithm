package Chapter2_Structure.S2_TreeTest;

import java.util.LinkedList;
import java.util.Queue;

public class th4_judgeComplete {
    // 判断一棵树是否是完全二叉树
    /*
      宽度优先遍历
      1. 如果一个节点有右孩子没有左孩子，直接false。
      2. 在不违反1的情况下，如果遇到了第一个左右孩子不全的时候，那么之后所有的节点必须都是叶子节点
     */

    // Complete binary tree
    // 先写宽度优先遍历
    public static boolean isCBT(Node head){
        if (head==null){
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node l = null;
        Node r = null;
        boolean leaf = false;  // 用来判断是否已经走到了叶子的前一个
        while (!queue.isEmpty()){
            head = queue.poll();
            l = head.left;
            r = head.right;
            if (
                    (leaf && (l!=null || r!=null))  // 情形 2
                    ||
                    (l==null && r!=null)  // 情景1
            ){
                return false;
            }
            if (l!=null){
                queue.add(l);
            }
            if (r!=null){
                queue.add(r);
            }
            if (l==null || r==null){  // 判断是否是双全节点，这里考虑了左不空右为空的情形
                leaf = true;
            }
        }
        return true;
    }
}
