package Chapter3_Algorithm.U6_DP;

public class th5_BobDie {
    /**
     * 问题：在一个M*N的矩阵里面，Bob在位置之(x,y)上，只能走K步，出了边界就会死亡。
     * 判断Bob死亡的概率。
     * 1. 可以得出Bob活下来的方法数有多少。
     * 2. 然后计算出全部的方法数。
     * 3. 然后除以就可以得到概率了。
     * 4. Bob只能走上下左右直线，且只能走一格。
     */

    public static String bob1(int N, int M, int i, int j, int K) {
        long all = (long) Math.pow(4, K);  // 不考虑死的情况下，总方法数
        long live = process(N, M, i, j, K);  // 活下来的方法数
        long gcd = gcd(all, live);  // 最大公约数，可以用于约分，减小计算值的计算量
        return (live / gcd) + "/" + (all / gcd);
    }

    public static long process(int N, int M, int row, int col, int rest) {
        // 越界
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        // 走完了步数
        if (rest == 0) {
            return 1;
        }
        // 还没走完
        long live = process(N, M, row - 1, col, rest - 1);  // 上走
        live += process(N, M, row + 1, col, rest - 1);  // 下走
        live += process(N, M, row, col - 1, rest - 1);   // 左走
        live += process(N, M, row, col + 1, rest - 1);   // 右走
        return live;
    }

    // 辗转求余
    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    // 改DP：也是三维体，当前层只和下一层有依赖
    public static String bob2(int N, int M, int i, int j, int K) {
        if (i < 0 || i >= N || j < 0 || j >= M || K < 0) {
            return "";
        }

        long[][][] dp = new long[N + 1][M + 1][K + 1];
        for (int r = 0; r <= N; r++) {
            for (int c = 0; c <= M; c++) {
                dp[r][c][0] = 1;
            }
        }

        for (int rest = 1; rest <= K; rest++) {
            for (int row = 0; row <= N; row++) {
                for (int col = 0; col <= M; col++) {
                    dp[row][col][rest] += getValue(dp, row - 1, col, N, M, rest - 1);
                    dp[row][col][rest] += getValue(dp, row + 1, col, N, M, rest - 1);
                    dp[row][col][rest] += getValue(dp, row, col - 1, N, M, rest - 1);
                    dp[row][col][rest] += getValue(dp, row, col + 1, N, M, rest - 1);
                }
            }
        }

        long all = (long) Math.pow(4, K);
        long gcd = gcd(all, dp[i][j][K]);
        return (dp[i][j][K] / gcd) + "/" + (all / gcd);
    }

    public static long getValue(long[][][] dp, int x, int y, int N, int M, int rest) {
        if (x < 0 || y < 0 || x >= N || y >= M) {  // 只有N*M，所以等于也不行
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int N = 100;
        int M = 100;
        int x = 2;
        int y = 2;
        int K = 100;
        System.out.println(bob1(N, M, x, y, K));
        System.out.println(bob2(N, M, x, y, K));
    }
}
