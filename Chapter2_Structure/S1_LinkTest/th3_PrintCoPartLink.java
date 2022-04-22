package Chapter2_Structure.S1_LinkTest;

public class th3_PrintCoPartLink {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Node node4 = new Node(1);
        Node node5 = new Node(3);
        Node node6 = new Node(4);

        node1.next = node2;
        node2.next = node3;

        node4.next = node5;
        node5.next = node6;

        printComPart(node1, node4);
    }

    static class Node{
        int val;
        Node next;
        public Node(int val){ this.val = val; }
    }
    // 打印两个有序链表的公共元素值
    public static void printComPart(Node head1, Node head2){
        Node p1 = head1;
        Node p2 = head2;
        while (p1!=null && p2!=null){
            if (p1.val==p2.val){
                System.out.println(p1.val);
            }
            if (p1.val<p2.val){  // 假设两个链表都是升序的
                p1 = p1.next;
            }else{
                p2 = p2.next;
            }
        }
    }
}
