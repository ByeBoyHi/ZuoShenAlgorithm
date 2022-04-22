package Chapter2_Structure.S2_TreeTest;

import java.util.HashMap;
import java.util.HashSet;

public class th7_Get2NsOfConParent {

    // 寻找两个节点的最低公共祖先：也就是在那个节点第一次相遇
    // 也就是说某个节点自身可能就是最低公共祖先

    /**
     * 返回一棵树的两个节点的最低公共祖先
     * @param head  这棵树的头节点
     * @param n1    节点1
     * @param n2    节点2
     * @return      公共祖先节点
     * 前提条件：n1 n2 一定属于 head 树。
     * 思路：给一个HashMap<curNode, parent>来记录当前节点和他的父节点
     * 然后用一个HashSet<Node>去存储其中一个节点的全部祖先节点
     * 接着再去另一个节点遍历他的所有的祖先，找第一个交集
     */
    public static Node LCA(Node head, Node n1, Node n2){
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, head);  // 根节点的祖先就是自己
        process(head, map);
        HashSet<Node> set = new HashSet<>();
        Node cur = n1;
        while (cur!=map.get(cur)){
            set.add(n1);
            cur = map.get(cur);
        }
        cur = n2;
        // cur不断的从n2的祖先网上找，直到找到了与n1的第一个交集
        while (!set.contains(cur)){
            cur = map.get(cur);
        }
        return cur;
    }
    // 去记录所有节点和他的父节点
    public static void process(Node head, HashMap<Node, Node> map){
        if (head==null){
            return;
        }
        map.put(head.left, head);
        map.put(head.right, head);
        process(head.left, map);
        process(head.right, map);
    }

    /**
     * 思路二：n1和n2会有LCA有以下两个可能性：
     *  1. n1是n2的祖先或者n2是n1的祖先
     *  2. n1和n2有个共同的祖先
     *  从根节点head出发，当某一个子树没有n1或者n2的时候，那么这颗子树和LCA是没有关系的，直接返回null。
     *  如果某个子树有n1或者n2，那么我们需要返回n1或者n2。
     *  当某一个节点他的左右子树的返回值只接收到了n1或者n2，说明 n1是n2的祖先 或者 n2是n1的祖先。
     *  当某一个节点他的左右子树的返回值既收到了n1也收到n2，那么在第一次收到这个结果的时候，我们就返回当前节点，
     *  因为当前节点是n1和n2第一次祖先交汇的时候，也就是最低公共祖先。
     */
    public static Node LCA2(Node head, Node n1, Node n2){
        if (head==null || head==n1 || head==n2){  // 没有n1或者n2，或者遇到了n1和n2
            return head;
        }

        Node left = LCA2(head.left, n1, n2);
        Node right = LCA2(head.right, n1, n2);
        // 如果左右子树都收到了非空结果，说明当前节点就是LCA
        if (left!=null && right!=null){
            return head;
        }
        // 如果左子树为n1或者n2，就返回left，否则返回right
        return left!=null?left:right;
    }
}
