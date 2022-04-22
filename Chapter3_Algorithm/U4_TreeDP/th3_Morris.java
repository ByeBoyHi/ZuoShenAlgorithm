package Chapter3_Algorithm.U4_TreeDP;

public class th3_Morris {
    /*
        每个节点最多来两次。
        利用的底层线索回到当前节点，并且只能由左子树回来。
        和递归不一样，递归是利用递归栈回来的。
     */
    static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value = value;
        }
    }
    /*
        空间复杂度：O(1)  只准备了两个变量
        时间复杂度：O(n)  见图 I2

        morris序列该先序、中序、后序：例子见图 I3
        先序遍历：一个节点如果只到一次，直接打印；如果到两次，第一次打印。
        中序遍历：只到一次，直接打印；到两次，第二次打印
     */
    public static void morris(Node node){
        if (node==null){
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur!=null){  // 3
            mostRight = cur.left;
            if (mostRight!=null){  // 2
                while (mostRight.right!=null && mostRight.right!=cur){  // 2
                    mostRight = mostRight.right;
                }
                if (mostRight.right==null){  // a
                    // 先序遍历：第一次来到cur，操作cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {  // b
                    // 中序遍历：第二次到达cur，操作cur
                    mostRight.right = null;
                }
            }else {
                // 先序/中序遍历：没有左子树的情况，直接操作cur
            }
            // 中序遍历可以直接在这里直接操作cur，这里包含了第二次来到cur和没有左子树的时候
            cur = cur.right; // 1
        }
    }

    // 先序遍历
    public static void morrisPre(Node node) {
        if (node==null){
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur!=null){  // 3
            mostRight = cur.left;
            if (mostRight!=null){  // 2
                while (mostRight.right!=null && mostRight.right!=cur){  // 2
                    mostRight = mostRight.right;
                }
                if (mostRight.right==null){  // a
                    System.out.println(cur.value);
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {  // b
                    mostRight.right = null;
                }
            }else {
                System.out.println(cur.value);
            }
            cur = cur.right; // 1
        }
    }

    // 中序遍历
    public static void morrisIn(Node node) {
        if (node==null){
            return;
        }
        Node cur = node;
        Node mostRight = null;
        while (cur!=null){  // 3
            mostRight = cur.left;
            if (mostRight!=null){  // 2
                while (mostRight.right!=null && mostRight.right!=cur){  // 2
                    mostRight = mostRight.right;
                }
                if (mostRight.right==null){  // a
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {  // b
                    mostRight.right = null;
                }
            }
            System.out.println(cur.value);
            cur = cur.right; // 1
        }
    }

    /*
        后序遍历
        对于第一次来和只能来一次的，什么也不做
        在第二次来到这个节点的时候，逆序打印这个节点左树的有边界（记住这里不打印当前节点）
        整个过程结束后，单独逆序打印整棵树的右边界。
        逆序打印要求：额外空间复杂度O(1)。
        逆序打印：就是单链表的逆序操作！！！！！打印完了后，再调整回来就行了！！！
     */
    public static void morrisPost(Node head){
        if (head==null){
            return;
        }
        Node cur = head;
        Node mostRight;
        while (cur!=null){
            mostRight = cur.left;
            if (mostRight!=null){
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right==null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
    }

    // 反转单链表
    public static Node reverseEdge(Node node){
        Node pre = null;
        Node next = null;
        while (node!=null){
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    // 打印右边界
    public static void printEdge(Node head){
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur!=null){
            System.out.println(cur.value);
            cur = cur.right;
        }
        reverseEdge(tail);
    }


    /*
        把morris用在isBST上！
        对于BST，中序遍历是升序。
        只需要在中序遍历的位置，进行大小判断
        在中序遍历的位置，我们获得的当前节点的值，以及中序遍历顺序上，前一个节点的值
     */
    public static boolean isBST(Node head){
        if (head==null){
            return true;
        }
        Node cur = head;
        Node mostRight;
        int preValue = Integer.MIN_VALUE;
        while (cur!=null){
            mostRight = cur.left;
            if (mostRight!=null){
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right==null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            }
            if (cur.value<=preValue){  // 因为可能第二次回来，所以取等于，而且等于是符合BST的
                return false;
            }
            preValue = cur.value;
            cur = cur.right;
        }

        return true;
    }

    /*
        哪些题目是以morris作为最优解的：
        1. 如果题目必须做第三次的强整合，那么使用二叉树递归。
        2. 否则使用morris。（最优解）
     */
}
