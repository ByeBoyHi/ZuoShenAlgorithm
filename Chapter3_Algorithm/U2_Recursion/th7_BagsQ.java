package Chapter3_Algorithm.U2_Recursion;

public class th7_BagsQ {
    /*
        有两个数组 weights 和 values。往背包里面放，不超过载重的情况下，能装下的最大价值是多少？
        不断的尝试，从左往右，要或者不要当前物品，然后取最大值。
        重量永远不要超过bag。
     */

    /**
     * 在不超过总重量的情况下，返回能得到的最大价值
     *
     * @param weights       重量列表
     * @param values        价值列表
     * @param i             当前到达第i件物品
     * @param alreadyWeight 之前所达到的重量
     * @param bag           要求的总重量不超过的值
     * @return 返回能获得的最大价值
     */
    // 方法一
    public static int process(int[] weights, int[] values, int i,
                              int alreadyWeight, int bag) {
        if (alreadyWeight > bag) {
            return 0; // 超重了
        }
        if (i == weights.length) {
            return 0;  // 物品放完了
        }
        return Math.max(
                // 不要第i件物品
                process(weights, values, i + 1, alreadyWeight, bag),
                // 留下第i件物品
                values[i] + process(weights, values, i + 1, alreadyWeight + weights[i], bag));
    }
    // 方法二
    public static int process2(int[] weights, int[] values, int i,
                               int alreadyWeight, int alreadyValue, int bag) {
        // 超重或者装完了
        if (alreadyWeight > bag || i == weights.length) {
            return 0;
        }
        return  Math.max(
                process2(weights, values, i+1, alreadyWeight, alreadyValue, bag),
                process2(weights, values, i+1, alreadyWeight+weights[i],
                        alreadyValue+values[i], bag));
    }
    /*
        在上面的方法中，选择可变参数少的，形式简单的，这样越容易该DP。
     */
}
