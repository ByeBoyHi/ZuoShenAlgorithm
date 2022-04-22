package Chapter5_MiddleLevel.Qs;

public class th9_ContinueScoreMax {
    /*
        给出一个数组，打印连续数字的最高分，如下：
        [1,1,-1,-10,10,5,-4,-3, 9] --> 17 = 10 + 5 - 4 - 3 + 9
     */
    public static int process(int[] nums) {
        if (nums.length == 0) return 0;
        int cur = 0;
        int max = Integer.MIN_VALUE;
        for (int n : nums) {
            // 都是负数的时候，max每次更新都是在cur更新为负数后才更新的，所以不会变成0
            // 而是会取到最大的负数
            cur += n;
            max = Math.max(max, cur);
            cur = Math.max(cur, 0);
        }
        return max;
    }

    /*
        给定一个整型矩阵，返回矩阵的最大累加和。
        这个累加和要构成一个子矩阵。
        压缩矩阵，两排竖着求和，使得他们合成一排。
     */
    public static int f(int[][] matrix){
        int max = Integer.MIN_VALUE;
        for (int[] cur: matrix){
            max = Math.max(max, process(cur));
        }
        return max;
    }
    // 矩阵处理
    public static int[][] p(int[][] matrix){
        int n = matrix.length*2;
        if ((matrix.length&1)==0) n-=2;
        int[][] ans = new int[n][matrix[0].length];
        System.arraycopy(matrix[0], 0, ans[0], 0, matrix[0].length);
        for (int i=1; i<matrix.length; i++){
            for (int j=0; j<matrix[0].length; j++){
                ans[i][j] = ans[i-1][j]+matrix[i][j];
            }
        }
        return ans;
    }

}
