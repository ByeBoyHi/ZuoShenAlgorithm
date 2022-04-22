package Chapter3_Algorithm.U6_DP;

public class th4_HorseJump {
    /*
        象棋马在一个规定的数组上跳格子，要求必须走k步，从(0,0)出发跳到(r,c)位置的方法数有多少
     */

    public static int getWays(int x, int y, int k){
        return process(x, y,0,0,8,9, k);
    }

    /**
     * 这个数组是 8*9 的大小。
     * 马始终从(0,0)出发，只能走step步，然后跳到(r,c)位置的方法数有多少种
     */
    public static int process(int x, int y, int r, int c, int xRange, int yRange, int step){
        if (x<0 || y<0 || x>xRange || y>yRange){  // 越界
            return 0;
        }
        if (step==0){  // 步数用完了，判断是否跳到了我们想要的终点
            return (x==r && y==c)?1:0;
        }
        // 还有步数没走完，走向周围可能的八个位置
        return process(x+2, y-1, r, c, xRange, yRange, step-1)
                + process(x+2, y+1, r, c, xRange, yRange, step-1)
                + process(x+1, y-2, r, c, xRange, yRange, step-1)
                + process(x+1, y+2, r, c, xRange, yRange, step-1)
                + process(x-2, y-1, r, c, xRange, yRange, step-1)
                + process(x-1, y-2, r, c, xRange, yRange, step-1)
                + process(x-2, y+1, r, c, xRange, yRange, step-1)
                + process(x-1, y+2, r, c, xRange, yRange, step-1);
    }

    // 改DP：三维DP

    /**
     * 对于上面的递归
     *      1. 这是一个三维体，step作为z轴从下到上
     *      2. 我们从最底层的step=0走到最顶层的step=step
     *      3. 因为我们需要在step=0的时候，走到指定的位置(r,c)上
     *      4. 那么最底层的那一面，只有(r,c)位置的值是1，其他都是0。（base case:因为其他都是无效位置）
     *      5. 并且我们的我们的step层的点只和step-1层的点依赖
     *      6. 那么我们可以从最底层，不断往上推到最顶层的(x,y)点
     *      7. 我们就可以得到下一层的八个点到顶层的(x,y)点的总方法数
     *  精华：一层一层的走，step控制层。（三维体）
     */
    public static int dpWays(int x, int y, int step){
        if (x<0 || y<0 || x>8 || y>9 || step<0){  // 越界
            return 0;
        }
        int[][][] dp = new int[9][10][step+1];
        dp[0][0][0] = 1; // 目标点(0,0)
        for (int h=1; h<=step; h++){  // 从第一层开始
            // 枚举x y，同一层互相不依赖，谁先谁后都可
            for (int r=0; r<9; r++){
                for (int c=0; c<10; c++){
                    dp[r][c][h] += getValue(dp, r+1, c-2, h-1);
                    dp[r][c][h] += getValue(dp, r+2, c-1, h-1);
                    dp[r][c][h] += getValue(dp, r+1, c+2, h-1);
                    dp[r][c][h] += getValue(dp, r+2, c+1, h-1);
                    dp[r][c][h] += getValue(dp, r-2, c-1, h-1);
                    dp[r][c][h] += getValue(dp, r-1, c-2, h-1);
                    dp[r][c][h] += getValue(dp, r-1, c+2, h-1);
                    dp[r][c][h] += getValue(dp, r-2, c+1, h-1);
                }
            }
        }
        return dp[x][y][step];
    }
    // 用一个单独取值的函数来处理，因为可能越界
    public static int getValue(int[][][] dp, int row, int col, int step){
        if (row<0 || col<0 || row>8 || col>9){  // 越界
            return 0;
        }
        return dp[row][col][step];
    }

    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        int step = 10;
        System.out.println(getWays(x, y, step));
        System.out.println(dpWays(x, y, step));
    }
}
