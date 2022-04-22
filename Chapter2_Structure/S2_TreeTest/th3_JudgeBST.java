package Chapter2_Structure.S2_TreeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class th3_JudgeBST {

    // 版本一
    // 用一个变量来记录一棵树目前搜索到的最小值
    public static int preValue = Integer.MIN_VALUE;

    // 判断一个树是不是搜索二叉树
    // 整个搜索过程是中序遍历的顺序
    public static boolean isBST(Node head) {
        if (head == null) {  // 空树默认是BST
            return true;
        }
        boolean isLeftBst = isBST(head.left);
        if (!isLeftBst) {  // 如果左树不是搜索二叉树
            return false;
        }
        if (head.val < preValue) {  // 如果当前节点小于之前的记录的节点，那么就说明不是BST
            return false;
        } else {  // 否则继续记录最小值
            preValue = head.val;
        }
        return isBST(head.right);
    }

    // 版本二：直接中序遍历一棵树，然后放入一个集合里面，最后去检查这个集合的顺序是否升序
    public static boolean isBST2(Node head) {
        List<Integer> list = new ArrayList<>();
        process(head, list);
        int cur = list.get(0);
        for (int num : list) {
            if (num < cur) {
                return false;
            }
            cur = num;
        }
        return true;
    }

    public static void process(Node head, List<Integer> list) {
        if (head == null)
            return;
        process(head.left, list);
        list.add(head.val);
        process(head.right, list);
    }

    // 版本三
    // 非递归判断BST
    // 中序遍历：左 头 右
    public static boolean isBST3(Node head) {
        if (head != null) {
            int preValue = Integer.MIN_VALUE;
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {  // 不断的往左边走
                    stack.push(head.left);
                    head = head.left;
                } else { // 左子树走完了，去右子树
                    head = stack.pop();

                    // 操作当前节点，然后去右子树
                    if (preValue > head.val) {
                        return false;
                    } else {
                        preValue = head.val;
                    }

                    head = head.right;
                }
            }
        }
        return true;
    }

    // 版本四：用满二叉树思想
    // 1. 左树是搜索二叉树
    // 2. 右树是搜索二叉树
    // 3. 左树的max < x
    // 4. 右树的min > x
    // 这四个条件都成立就是搜索二叉树
    // 构造对象：
    // 对于左树：是否是搜索二叉树，需要最大值
    // 对于右树：是否是搜索二叉树，需要最小值
    // 整合到一起：是否是搜索二叉树，最大值，最小值
    public static class ReturnType {
        int max;
        int min;
        boolean isBST;

        public ReturnType(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    public static boolean isBST4(Node head) {
        return process(head).isBST;
    }

    public static ReturnType process(Node head) {
        if (head == null) {
            return null;  // 最小值最大值不方便直接设置值，直接返回空，在下面判断的时候需要判断一下
        }

        ReturnType leftTree = process(head.left);
        ReturnType rightTree = process(head.right);


        int min = head.val;
        int max = head.val;
        if (leftTree != null) {
            min = Math.min(min, leftTree.min);
            max = Math.max(max, leftTree.max);
        }
        if (rightTree != null) {
            min = Math.min(min, rightTree.min);
            max = Math.max(max, rightTree.max);
        }

        // 子树为空，认为永远达标
//        boolean isBST = leftTree == null || (leftTree.isBST && leftTree.max < head.val);
        // 右子树不为空，并且右子树不是BST或者右子树的最小值小于当前根节点，则当前节点所在的子树不是BST。
//        if (rightTree!=null && (!rightTree.isBST || rightTree.min<=head.val)){
//            isBST = false;
//        }
        boolean isBST = false;
        // 换成三目运算符：系统推荐用位运算 = =，给提示让改了
        if (
                (leftTree == null || (leftTree.isBST && leftTree.max < head.val))
                        &&
                        (rightTree == null || (rightTree.isBST && rightTree.min > head.val))
        ) {
            isBST = true;
        }

        return new ReturnType(max, min, isBST);
    }
}
