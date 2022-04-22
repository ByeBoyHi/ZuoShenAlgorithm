package Chapter2_Structure.S1_LinkTest;

public class th2_ReDoubleLinked {

    public static void main(String[] args) {
        DoubleNode node1 = new DoubleNode(1);
        DoubleNode node2 = new DoubleNode(2);
        DoubleNode node3 = new DoubleNode(3);
        DoubleNode node4 = new DoubleNode(4);
        DoubleNode node5 = new DoubleNode(5);
        node1.next = node2;
        node2.next = node3;
        node2.pre = node1;
        node3.next = node4;
        node3.pre = node2;
        node4.next = node5;
        node4.pre = node3;
        node5.pre = node4;
        DoubleNode node = node1;
        while (node!=null){
            System.out.println(node.val);
            node = node.next;
        }
        System.out.println("------------");
        node = reverse(node1);
        while (node!=null){
            System.out.println(node.val);
            node = node.next;
        }
    }

    static class DoubleNode{
        int val;
        DoubleNode pre;
        DoubleNode next;
        public DoubleNode(int val){ this.val = val; }
    }

    // 反转双链表
    public static DoubleNode reverse(DoubleNode head){
        DoubleNode pre = head;
        DoubleNode cur = pre.next;
        DoubleNode next = cur.next;
        pre.next = null;
        pre.pre = cur;
        while (next!=null){
            cur.next = pre;
            cur.pre = next;
            // 整体后移
            pre = cur;
            cur = next;
            next = next.next;
        }
        cur.next = pre;
        cur.pre = null;
        return cur;
    }
}
