package Chapter2_Structure.S2_TreeTest;

import java.util.Stack;

public class th1_TraverseTree {

    static class MyNode {
        int val;
        MyNode left;
        MyNode right;

        public MyNode(int val) {
            this.val = val;
        }
    }

    // 递归遍历
    public void f(MyNode head){
        // 1  这一部分是 ”第一次“ 回来这个函数
        if (head==null){
            return;
        }
        // 1
        // 然后进入左子树
        f(head.left);
        // 2  "第二次"
        // 2
        // 然后进入右子树
        f(head.right);
        // 3  "第三次"
        // 3

        // 每一次递归调用，最后都会回来一次
    }

    // 先序遍历：头左右
    // 中序遍历：左头右
    // 后序遍历：左右头
    // 变的只是头的处理位置，左右子树的相对位置是不变的。
    public void order(MyNode head, int flag){
        if (head==null){
            return;
        }
        // flag控制顺序
        if (flag==1)
            System.out.println(head.val); // 先序位置：第一次来到的时候打印
        order(head.left, flag);
        if (flag==2)
            System.out.println(head.val); // 中序位置：第二次来的时候打印
        order(head.right, flag);
        if (flag==3)
            System.out.println(head.val); // 后序位置：第三次来的时候打印
    }

    // 非递归
    // 前序遍历
    public static void preOrder(MyNode head){
        if (head==null) return;
        Stack<MyNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()){
            MyNode cur = stack.pop();
            System.out.println(cur.val);
            // 因为栈的先入后出原则，所以先入右后入左，才能保证输出的时候先左后右
            if (cur.right!=null){
                stack.push(cur.right);
            }
            if (cur.left!=null){
                stack.push(cur.left);
            }
        }
    }

    // 中序遍历
    // 直接遍历树的左子树，不断走到最左端节点，然后弹出
    // 如果有右子树，入右子树，然后重复操作右子树的左子树
    // 这个的是指是 “左边界”，先左再头，然后右数继续执行先再头，如此往复，整个树都被左边界分解掉了。
    public static void inOrder(MyNode head){
        if (head!=null){
            Stack<MyNode> stack = new Stack<>();
            while (!stack.isEmpty() || head!=null){
                if (head!=null){  // 只要head不是空，也就是没有走到最左边叶子，就不断往栈里面压
                    stack.push(head.left);  // 压栈
                    head = head.left;  // 左移
                }else {  // 走到最左边叶子
                    head = stack.pop();  // 接住当前没有左孩子的节点
                    System.out.println(head.val);  // 操作
                    head = head.right;  // 走到右节点，然后开始操作右节点的左子树
                }
            }
        }
    }

    // 后序遍历
    public static void postOrder(MyNode head){
        if (head!=null){
            // 给一个主栈和辅助栈，以头右左入主栈，然后弹出就入辅助栈
            // 由辅助栈顺序弹出的时候，因为入栈逆序问题，就是 左右头 了，也就是后序遍历
            Stack<MyNode> stack1 = new Stack<>();
            Stack<MyNode> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()){
                MyNode cur = stack1.pop();
                if (cur.right!=null){
                    stack1.push(cur.right);
                }
                if (cur.left!=null){
                    stack1.push(cur.left);
                }
                stack2.push(cur);
            }
            while (!stack2.isEmpty()){
                System.out.println(stack1.pop().val);
            }
        }
        System.out.println();
    }

}
