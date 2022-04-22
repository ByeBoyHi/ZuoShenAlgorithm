package Chapter2_Structure.S1_LinkTest;

import java.util.HashSet;
import java.util.Set;

/**
 * 对于两个单链表相交问题，需要考虑两个单链表是否有环，有以下几种情况：
 * 1. 两个单链表都无环。
 * 2. 两个单链表一个有环一个无环。
 * 3. 两个单链表都有环。
 * <p>
 * 对于情况一：均无环情况。
 * 1. 如果相交，必然有其公共部分，也就是在不同的起点出发，会到达共同的终点；
 * 2. 如果不相交，那么必然会有不同的起点。
 * 对于情况二：一个有环另一个无环。
 * 假设他们相交，那么必然有公共部分，但是起点处均是无环的地方，那么相交部分必然是环，所以与其中一个无环矛盾，所以必然不相交。
 * 对于情况三：均有环情况。
 * 1. 假设他们不相交，那么在都走到环上的时候，其中一个结点在环上绕一圈后，是不会和另一个节点相遇的。
 * 2. 假如他们相交，那么有两种情况：
 * 2.1 他们是同一个入环节点或该节点之前相交；
 * 2.2 他们是环上相交，也就是在环的不同的位置入环，然后相遇。
 */

public class th7_CoPartLink {

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
//        node5.next = node3;

        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
//        node9.next = node2;

        Node node = new th7_CoPartLink().coPart(node1, node6);
        if (node == null) {
            System.out.println("null");
        } else {
            System.out.println(node.val);
        }
    }

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    // 主要函数，返回两个节点的公共部分
    public Node coPart(Node head1, Node hea2) {
        if (head1 == null || hea2 == null) {  // 有一个为空就没有相交部分
            return null;
        }
        if (isCircle(head1) && isCircle(hea2)) {  // 都有环
            return bothLoop(head1, hea2);
        } else if (!isCircle(head1) && !isCircle(hea2)) {  // 都无环
            return noLoop(head1, hea2);
        } else { // 一个有环一个无环
            return null;
        }
    }

    // 判断单链表是否有环路：哈希表
    private boolean isCircle(Node head) {
        Set<Node> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }

    // 判断单链表是否有环路：快慢指针
    // 在快指针和慢指针相遇之后，快指针回到起点，慢指针在原地，然后都走一步
    // 如果有环，返回入环节点
    private Node getLoopNode(Node head) {
        Node slow = head;
        Node fast = head;
        boolean flag = false;
        while (fast.next != null && head.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // 有环
                flag = true;
                break;
            }
        }
        fast = head;
        while (flag && fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        if (fast == slow) {  // 返回入环节点
            return fast;
        }
        return null;
    }


    // 对于两个无环链表是否相交，返回相交节点
    private Node noLoop(Node head1, Node head2) {
        // 首先一次循环记录他们的长度之差，然后结束的时候判断他们的终点是否是同一个
        // 因为比较长的单链表先走长度差的距离之后，两个链表一起走后，必然会在第一个相交节点相遇
        int divide = 0;
        Node cur1 = head1;
        Node cur2 = head2;
        // 这里以next为空作为条件是为了走到最后一个的时候退出循环
        while (cur1.next != null) {
            divide++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            divide--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {  // 不相交，不是同一个终点
            return null;
        }
        // divide>0说明head1长，让cur1始终指向长的链表
        cur1 = divide > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        divide = Math.abs(divide);
        while (divide != 0) {
            assert cur1 != null;
            cur1 = cur1.next;
            divide--;
        }
        // 现在两个链表同时走
        while (cur1 != cur2) {
            assert cur1 != null;
            cur1 = cur1.next;
            assert cur2 != null;
            cur2 = cur2.next;
        }
        // 返回相交的第一个节点
        return cur1;
    }

    // 两个单链表都有环
    private Node bothLoop(Node head1, Node head2) {
        // 找到两个单链表的入环节点
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        assert loop1 != null;
        Node loop = loop1.next;

        // 不同的入环节点
        if (loop1 != loop2) {
            while (loop != loop1 && loop != loop2) {
                loop = loop.next;
            }
            if (loop != loop2) {  // 两个不同的环
                return null;
            } else { // 不同的点入的环，且相交于环
                return loop;
            }
        } else {  // 同一个入环节点，必然相交
            // 以入环节点为终点，找前面半截的长度
            loop = loop1;
            loop1 = head1;
            loop2 = head2;
            int n = 0;
            while (loop1 != loop) {
                loop1 = loop1.next;
                n++;
            }
            while (loop2 != loop) {
                loop2 = loop2.next;
                n--;
            }
            loop1 = n > 0 ? head1 : head2;
            loop2 = loop1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                loop1 = loop1.next;
                n--;
            }
            while (loop1 != loop2) {
                loop1 = loop1.next;
                loop2 = loop2.next;
            }
            // 返回相遇节点
            return loop1;
        }
    }
}
