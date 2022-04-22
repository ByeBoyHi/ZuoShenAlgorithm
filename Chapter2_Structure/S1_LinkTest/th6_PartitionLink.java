package Chapter2_Structure.S1_LinkTest;

// 将单链表按照某个值，划分为 小于区域、等于区域、大于区域
// 且保留其稳定性
public class th6_PartitionLink {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(3);
        Node node3 = new Node(8);
        Node node4 = new Node(6);
        Node node5 = new Node(6);
        Node node6 = new Node(7);
        Node node7 = new Node(2);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        Node node = partition(node1, 6);
        while (node!=null){
            System.out.println(node.val);
            node = node.next;
        }
    }

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 定义六个指针，分别是：
     * 1. 小于区域的 头指针 SH 和尾指针 ST
     * 2. 等于区域的 头指针 EH 和尾指针 ET
     * 3. 大于区域的 头指针 LH 和尾指针 LT
     * 然后对每个元素判断其所属区域，然后链接进入对应的区域
     */
    public static Node partition(Node head, int key) {
        Node SH = null;
        Node ST = null;
        Node EH = null;
        Node ET = null;
        Node LH = null;
        Node LT = null;
        Node cur = head;
        while (cur != null) {
            if (cur.val < key) {
                if (SH == null) {
                    SH = cur;
                    ST = cur;
                } else {
                    ST.next = cur;
                    ST = cur;
                }
            } else if (cur.val == key) {
                if (EH == null) {
                    EH = cur;
                    ET = cur;
                } else {
                    ET.next = cur;
                    ET = cur;
                }
            } else {
                if (LH == null) {
                    LH = cur;
                    LT = cur;
                } else {
                    LT.next = cur;
                    LT = cur;
                }
            }
            cur = cur.next;
        }

        if (SH!=null){
            ST.next = EH;
            ET = ET==null?ST:ET;  // 下一步谁去连大于区域的尾巴
        }
        // 如果有小于等于区域，那么可以试着连接大于区域
        if (ET!=null){
            ET.next = LH;
        }

        return SH != null ? SH : (EH != null ? EH : LH);
    }
}
//        if (SH != null) {
//            ST.next = EH;
//        }
//        ET = ET != null ? ET : ST;
//        if (ET != null) {
//            ET.next = LH;
//        }
//        if (LT!=null) {
//            LT.next = null;  // 让最后一个置为空，防止出现圈
//        }

