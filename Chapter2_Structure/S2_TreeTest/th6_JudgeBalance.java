package Chapter2_Structure.S2_TreeTest;

public class th6_JudgeBalance {
    public static boolean isBT(Node head) {
        return process(head).isBalance;
    }

    public static ReturnType process(Node head) {
        if (head == null) {  // 到达叶子节点
            return new ReturnType(true, 0);
        }
        ReturnType leftTree = process(head.left);
        ReturnType rightTree = process(head.right);
        int height = Math.max(leftTree.height, rightTree.height) + 1;
        boolean isBalance = (leftTree.isBalance && rightTree.isBalance &&
                Math.abs(leftTree.height - rightTree.height) < 2);
        return new ReturnType(isBalance, height);
    }

    // 判断一棵树是不是平衡二叉树：任何一个节点，左子树高度和右子树高度差不超过一
    // 这种方式非常善用用于处理树形DP问题！！！！比如判断是否是满二叉树
    // 方法一：用一个自定义对象来进行返回，存储当前节点是否平衡，高度多少
    // 左子树必须平衡、右子树必须平衡、左右高度差小于等于1，三个条件都成立，这个节点就是平衡的
    public static class ReturnType {
        boolean isBalance;
        int height;

        public ReturnType(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }
}
