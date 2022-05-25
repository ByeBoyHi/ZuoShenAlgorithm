package Chapter2_Structure.S6_UnionAndSelectTest;

// 并查集
public class th1_IslandProblem {
    // Q1：一个二维数组，有多少1连在一起，算一个岛，斜着不算，一个单独的1也算一个岛，返回岛的数量
    // F1：不使用并查集的解法
    public int getIslandNum(int[][] matrix){
        if (matrix==null || matrix[0]==null){
            return 0;
        }
        int M = matrix.length;
        int N = matrix[0].length;
        int ans = 0;

        for (int i=0; i<M; i++){
            for (int j=0; j<N; j++){
                if (matrix[i][j]==1){
                    ans++;
                    process(matrix, i, j, M, N); // 感染这一片所有的1
                }
            }
        }
        // 时间复杂度是 M*N
        return ans;
    }

    // 感染操作，把连成一片的1全部变成别的数字，比如2，表示这一片1已经访问过了
    /*
        1. 感染次数就是岛屿的数量
        2. 退出条件是走到边界，或者走到不是1的位置
        3. 对上下左右进行递归
        4. 递归之前，先让当前位置变成2，防止去了其他位置再回来，进入死循环
        综上，需要的参数如下：
        1. 当前矩阵数据。
        2. 当前所在坐标位置 i j
        3. 矩阵数据的长和宽 M N
     */
    public void process(int[][] matrix, int i, int j, int M, int N){
        if (i<0 || j<0 || i>=M || j>=N || matrix[i][j]!=1){
            return;
        }

        matrix[i][j] = 2;
        process(matrix, i-1, j, M, N);  // 上
        process(matrix, i, j-1, M, N);  // 左
        process(matrix, i+1, j, M, N);  // 下
        process(matrix, i, j+1, M, N);  // 右
    }
}
