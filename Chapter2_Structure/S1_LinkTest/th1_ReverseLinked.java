package Chapter2_Structure.S1_LinkTest;

public class th1_ReverseLinked {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        Node node = reverse(node1);
        while (node!=null){
            System.out.println(node.val);
            node = node.next;
        }
    }
    static class Node{
        int val;
        Node next;
        public Node(int val){ this.val = val; }
    }

    // 反转单链表
    public static Node reverse(Node head){
        Node pre = head;
        Node cur = pre.next;
        Node next = cur.next;
        pre.next = null;
        while (next!=null){
            cur.next = pre;
            // 后移
            pre = cur;
            cur = next;
            next = next.next;
        }
        cur.next = pre;
        return cur;
    }
}
