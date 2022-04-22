package Chapter3_Algorithm.U6_DP;

public class th3_FirstHandOr {
    /*
     * 先手后手游戏：一共有n张牌，用一个数组保存
     *  1. 要求拿到的分数是最高的。
     *  2. 你可以选择先手或者后手拿其中一张牌。
     */

    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return Math.max(f(arr, 0, arr.length), s(arr, 0, arr.length));
    }

    // 先手函数：在 i~j 范围上拿
    // 当前该你拿，那么你肯定会选择拿了后，保证自己分数最大
    public static int f(int[] arr, int i, int j) {
        if (i == j) {
            return arr[i];
        }
        return Math.max(arr[i] + s(arr, i + 1, j),
                arr[j] + s(arr, i, j - 1));
    }

    // 后手函数
    // 当前该对方拿，那么对方肯定会拿让你分数最小的情况
    public static int s(int[] arr, int i, int j) {
        if (i == j) {
            return 0;
        }
        return Math.min(f(arr, i + 1, j),
                f(arr, i, j - 1));
    }

    // 改DP
    // 定义两张表：f , s
    // 变化范围都是 0~arr.len，两个正方形
    // 由递归可以看出 f[i][j]由 (s[i+1][j]、arr[i]) 和 (s[i][j-1]、arr[j])决定
    // 对于s[i][j]，同理可得
    // 并且这是一个范围尝试，也就是i一定小于等于j，那么对于所有i>j的区域，都是无用的
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = arr.length;
        int[][] f = new int[len][len];
        int[][] s = new int[len][len];
        for (int i = 0; i < len; i++) {  // f的base case
            f[i][i] = arr[i];
        }
        // s的base case等于0，默认值，不用管

        int row = 0;
        int col = 1;
        /*
            走对角线。
            都是从第零行开始走，只有col列在变化来控制走的对角线。
            对角线的方向控制就是行和列同时++。
            // 也可以从最下面一层往上走，从左往右，只要确定我们的格子谁先谁后就好。
         */
        while (col < len) {
            int i = row;
            int j = col;
            while (i < len && j < len) {
                f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
                s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
                i++;
                j++;
            }
            col++;
        }
        return Math.max(f[0][len-1], s[0][len-1]);
    }
}
