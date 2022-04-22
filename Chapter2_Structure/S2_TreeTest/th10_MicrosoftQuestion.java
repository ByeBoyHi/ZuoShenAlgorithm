package Chapter2_Structure.S2_TreeTest;

public class th10_MicrosoftQuestion {
    // 题目：对一个折纸从下往上面向自己，不断折 n 次，纸张会有折痕
    // 折痕凹下去的，记作“凹“，否则记作”凸“

    // 题解：除了第一次，每进行一次折痕，都会在上一次的折痕上面生成”凹“，下面生成”凸“，只有第一次生成一个”凹“
    // 这就像如下一个二叉树：
    /**
     *                   凹
     *           凹               凸
     *       凹      凸        凹     凸
     * 也就是除了根节点必然是凹的。
     * 所有节点的左节点是凹的，右节点是凸的。
     * 用一个变量记录目前在左节点还是右节点
     */
    /**
     * 不断递归，中序打印所有的节点的信息。
     * 因为题目要求从上至下的打印凹痕凸痕信息，就相当于从最左边打印到最右边，根在中间
     * @param cur   当前在第几层折痕
     * @param N     最大折痕次数
     * @param down  当前是在左节点还是右节点，左节点(凹)true,右节点(凸)false
     */
    public static void process(int cur, int N, boolean down){
        if (cur>N){  // 超过了最大折痕层数
            return;
        }
        // 这更类似于我们想象出来的一棵树，用boolean来控制当前在左子树还是右子树
        process(cur+1, N, true);
        System.out.println(down?"凹":"凸");
        process(cur+1, N, false);
    }
    // 主方法调用上面的递归
    public static void MicrosoftQ(int N){
        process(1, N, true); // 第一层是凹，默认是true
    }
}
