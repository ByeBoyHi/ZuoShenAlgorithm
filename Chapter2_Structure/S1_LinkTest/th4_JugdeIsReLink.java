package Chapter2_Structure.S1_LinkTest;

import java.util.Stack;

// 判断是否是回文单链表
public class th4_JugdeIsReLink {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
//        Node node33 = new Node(5);
        Node node4 = new Node(2);
        Node node5 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
//        node33.next = node4;
        node4.next = node5;
        System.out.println(SlowAndFast(node1));
        Node node = node1;
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

    // 使用栈来判断
    public static boolean judgeByStack(Node head){
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur!=null){
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (cur!=null){
            if (stack.pop().val!= cur.val){
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    // 利用快慢指针走到中点位置，让后半部分入栈
    /**
     * 思路：对于一个单链表，我们可以根据对称轴进行判断，判断基于对称轴的两边是否相等
     * 那么这里我们需要走到中点的位置，然后把后半部分放入栈里面，基于后入先出原则
     * 入完后，链表从头走，栈也依次出栈，只要彼此相等，就是回文，否则就不回文
     */
    public static boolean SlowAndFastByStack(Node head){
        Stack<Node> stack = new Stack<>();
        // 慢指针一次走一步，快指针一次走两步，当快指针走到尽头的时候，慢指针就在中点的前一个位置
        Node slow = head;
        Node fast = head;
        // 先判断next，再判断next.next
        // 防止前一步就是空的，然后出现空指针异常
        while (fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        slow = slow.next;
        while (slow!=null){
            stack.push(slow);
            slow = slow.next;
        }
        fast = head;
        while (!stack.isEmpty()){
            if(stack.pop().val!= fast.val){
                return false;
            }
            fast = fast.next;
        }
        return true;
    }

    // 利用快慢指针走到中点位置，然后让后半部分反转
    // 因为镜像对称，所以从做往右走和从右往左走的值应该是一样的，则为回文
    // 令中点位置的next置为null，那么在null之前，只要彼此相等，就是回文
    // 相对于栈，优化了空间
    public static boolean SlowAndFast(Node head){
        Node slow = head;
        Node fast = head;
        while (fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }

        // 走到了中点的位置
        Node cur = slow.next;
        Node next = cur.next;
        Node pre = slow;
        pre.next = null;
        // 后半部分反转
        while (next!=null){
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next.next;
        }

        cur.next = pre;  // 处理最后一个节点的指针

        pre = cur; // 记录最后一个节点

        // 镜像判断
        Node H = head;
        while (H!=null){
            if (H.val!=cur.val){
                // 非回文链表也需要把链表恢复回来
                fun(pre);
                return false;
            }
            cur = cur.next;
            H = H.next;
        }

        // 把链表恢复回来
        fun(pre);

        return true;
    }

    // 恢复链表
    private static void fun(Node head){
        Node pre = head;
        Node cur = pre.next;
        Node next = cur.next;
        pre.next = null;
        while (next!=null){
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next.next;
        }
        cur.next = pre;
    }
}
