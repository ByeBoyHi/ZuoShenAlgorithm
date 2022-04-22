package Chapter5_MiddleLevel.Qs;

public class th4_FibType {
    /*
        对于Fib问题的 F(n)=F(n-1)+F(n-2)，在线性代数中，恒有：
        |F(n), F(n-1)| = |F(n-1), F(n-2)| * |2*2矩阵|
        因为从n到n-2，下降了两次，是一个二阶问题，其他类似问题同解。
        那么对于这类问题就可以不断推到最底层：
        |F(n), F(n-1)| = |F(2), F(1)| * |2*2矩阵|^(n-2)
        对于n-2次幂，可以通过二进制位的方式，进行计算，时间复杂度是 log n 。
        比如 n-2= 75 = 1001011，令后面的矩阵为A。
        那么就可以计算A，A^2，A^8，A^64，并且这些次幂，可以通过自身相乘，进行快速升幂。
     */

    // 递归fib
    public static int fib(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    //===============================================================================
    //===============================================================================

    //常规O(n)时间复杂度的Fib
    public static int fibOn(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int[] fib = new int[n];
        fib[0] = fib[1] = 1;
        for (int i = 2; i < n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n - 1];
    }

    //===============================================================================
    //===============================================================================

    // 矩阵相乘
    public static int[][] matrixMulti(int[][] a, int[][] b) {
        if (a.length == 0 || b.length == 0 || a[0].length == 0 || b[0].length == 0) {
            throw new RuntimeException("The matrix has no a right dimensionality.");
        }
        if (a[0].length != b.length) {
            throw new RuntimeException("The dimensionality of two matrices is mismatch.");
        }

        int[][] res = new int[a.length][b[0].length];
        for (int i = 0; i < res.length; i++) {  // 列
            for (int j = 0; j < res[0].length; j++) { // 行
                for (int k = 0; k < b.length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

    // log(n)的Fib
    public static int fibLog(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int[][] base = new int[][]{
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n - 2);

        return res[0][0] + res[1][0];
    }

    // 次幂计算
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = matrixMulti(res, t);
            }
            t = matrixMulti(t, t);
        }
        printMatrix(res);
        return res;
    }

    // 矩阵输出
    public static void printMatrix(int[][] a) {
        int n = a[0].length;

        for (int[] ints : a) {
            for (int j = 0; j < n; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    //===============================================================================
    //===============================================================================

    /*
        假设山上有一只羊，每年会剩下一只小羊，小羊两年后成熟且可以生下小羊。
        问：n年后，山上一共有多少只羊。
        A       1
        A (B)   2
        A B (C) 3
        A B C (D E) 5
        A B C D E (F G H) 8
        ...
        F(n) = 2*F(n-2)+F(n-1)
        今年羊的数量，等于两年前羊的数量在今年可以下的崽，加上去年羊的数量。
     */
    // 递归
    public static int GoatNum(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return GoatNum(n - 2) + GoatNum(n - 1);
    }

    // 矩阵形式
    public static int GoatMatrix(int n) {
        int[][] base = new int[][]{
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n-2);

        return 2*res[0][0]+res[1][0];
    }

    public static void main(String[] args) {
        System.out.println(fib(8));
        System.out.println(fibLog(8));
        System.out.println(fibOn(8));
        System.out.println("--------------");
        System.out.println(GoatNum(10));
        System.out.println(GoatMatrix(10));
    }
}
