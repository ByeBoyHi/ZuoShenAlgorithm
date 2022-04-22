package Chapter3_Algorithm.U6_DP;

public class th7_NNodesOfDiffBiTree {

    // 有n个节点，能形成多少种二叉树的结构，和节点方向有关

    // 递归
    public static int process(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        // 有n个节点，那么有f(n) = f(0) * f(n-1)
        //                    + f(1) * f(n-2)
        //                    + ...
        //                    + f(n/2) * f(n/2)
        int res = 0;
        for (int leftNum = 0; leftNum <= n - 1; leftNum++) {
            int leftWays = process(leftNum);  // 左边的可能性
            int rightWays = process(n - 1 - leftNum);  // 右边的可能性
            res += leftWays * rightWays;  // 左边的可能性*右边的可能性
        }
        return res;
    }

    // DP
    public static int DP(int n) {
        if (n < 2) return 1;
        int[] dp = new int[n + 1];  // 走到第n层
        dp[0] = 1; // 节点数为0的时候，默认一种可能性
        for (int i = 1; i < n + 1; i++) { // 节点数为i的时候，答案是多少
            for (int j = 0; j <= i - 1; j++) {  // 左侧节点个数为j-1，右侧节点个数为i-j
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

}
