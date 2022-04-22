package Chapter5_MiddleLevel.Qs;

public class th8_SubTreeMostNodes {

    static class ReturnType{
        /*
            六个属性的信息：
                1. 当前节点开始的子树是否是BST
                2. 当前节点的最大BST的总节点个数
                3. 这个最大BST树的头节点
                4. 左子树的最大值
                5. 右子树的最小值
         */
        boolean isBst;
        int count;
        TreeNode head;
        int leftMax, rightMin;

        public ReturnType(boolean isBst, int count, TreeNode head, int leftMax, int rightMin){
            this.isBst = isBst;
            this.count = count;
            this.head = head;
            this.leftMax = leftMax;
            this.rightMin = rightMin;
        }
    }

    static class TreeNode{
        int n;
        TreeNode left, right;
        public TreeNode(int n){
            this.n = n;
        }
    }

    public static ReturnType process(TreeNode head){
        if (head==null){ // 空节点
            return new ReturnType(true, 0, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        ReturnType leftNode = process(head.left);
        ReturnType rightNode = process(head.right);

        // 左树或者右树不是BST，则直接返回
        if (!leftNode.isBst || !rightNode.isBst){
            TreeNode X = null;
            int count = 0;
            if (leftNode.count> rightNode.count){
                X = leftNode.head;
                count = leftNode.count;
            }else {
                X = rightNode.head;
                count = rightNode.count;
            }
            return new ReturnType(false, count, head, -1, -1);
        }

        boolean isBst = false;
        int leftMax = head.n;
        int rightMin = head.n;
        // 当左树最大值和右树最小值以及当前节点的值不满足BST，则不进去。
        if (leftMax > leftNode.leftMax && rightMin < rightNode.rightMin){
            isBst = true;
            leftMax = leftNode.leftMax;
            rightMin = rightNode.rightMin;
        }

        return new ReturnType(isBst, leftNode.count+rightNode.count+1, head, leftMax, rightMin);
    }
}
