package Chapter2_Structure.S1_LinkTest;

import java.util.HashMap;
import java.util.Map;

public class th5_CopyRandomLink {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node1.random = node3;
        node2.random = node4;
        Node node = node1;
        while (node != null) {
            System.out.println(node.val + " random " + (node.random!=null?node.random.val:""));
            node = node.next;
        }
        System.out.println("-------------------");
        node = copyLink2(node1);
        while (node != null) {
            System.out.println(node.val + " random " + (node.random!=null?node.random.val:""));
            node = node.next;
        }
        System.out.println("-------------------");
        node = node1;
        while (node != null) {
            System.out.println(node.val + " random " + (node.random!=null?node.random.val:""));
            node = node.next;
        }
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
        }
    }

    // 使用Map记录原节点和复制节点
    public static Node copyLink(Node head) {
        Node cur = head;
        Map<Node, Node> map = new HashMap<>();
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            Node copyCur = map.get(cur);
            copyCur.next = cur.next;
            copyCur.random = cur.random;
            cur = cur.next;
        }
        return map.get(head);
    }

    // 使用链接相邻复制，降低空间复杂度
    public static Node copyLink2(Node head) {
        Node cur = head;
        while (cur != null) {
            Node copy = new Node(cur.val);
            copy.next = cur.next;
            cur.next = copy;
            cur = cur.next.next;
        }

        cur = head;
        Node copy = cur.next;
        Node ans = copy; // 记录复制链表的头部用于返回
        while (cur != null) {
            copy = cur.next;
            if (cur.random!=null) {
                copy.random = cur.random.next;
            }

            cur.next = cur.next.next; // 恢复原链表的节点

            cur = cur.next; // 后移
            // 这个指向要后动，因为前面的cur还需要靠next指针移动
            if (copy.next!=null) {
                copy.next = copy.next.next;
            }
        }

        return ans;
    }

}
