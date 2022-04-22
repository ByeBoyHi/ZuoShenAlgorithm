package Chapter3_Algorithm.U6_DP;

public class th1_RobotWalk {
    /*
        有N个点，机器人当前在S点，有个终点E，机器人只能走k步。
        那么从S->E，机器人的方法数有多少？
        例如：N=5，S=2，E=4，k=4，有以下可能。
        1. 2->3->4->5->4
        2. 2->3->4->3->4
        3. 2->3->2->3->4
        4. 2->1->2->3->4
     */

    // 递归
    public static int walkWays(int N, int E, int k, int S) {
        return f(N, E, k, S);
    }

    /**
     * 在剩下rest步数的时候，从cur走到E有多少种方法
     * 时间复杂度：O(2^k)
     *
     * @param N    固定参数：1~N这么多位置
     * @param E    固定参数：最终位置
     * @param rest 目前机器人还剩多少步需要走
     * @param cur  机器人当前所在的位置
     * @return 返回我们的方法数
     */
    public static int f(int N, int E, int rest, int cur) {
        if (rest == 0) {  // Base case：步数走完后，判断是否走到了E
            return E == cur ? 1 : 0;
        }
        if (cur == 1) {  // 在最左端的时候，只能往右走，也就是当前位置加1
            return f(N, E, rest - 1, cur + 1);
        }
        if (cur == N) { // 在最右端的时候，只能往左走，也就是当前位置减1
            return f(N, E, rest - 1, cur - 1);
        }
        return f(N, E, rest - 1, cur + 1) + f(N, E, rest - 1, cur - 1);
    }

    // 记忆化搜索：加一个空间缓存对已经走过的位置进行存储，避免重复递归
    // 有两个可变参数 rest 和 cur。
    // 也就是 f(r, c) = f(r-1, c-1) + f(r-1, c+1)
    public static int walkWays2(int N, int E, int k, int S) {
        // 存储k步的k+1种可能，N个位置的可能，第0个位置不用
        int[][] dp = new int[k + 1][N + 1];
        // 全部标记-1，表示这些状态都没计算过
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(N, E, k, S, dp);
    }

    // 时间复杂度：O(k*N) 相当于表的每一个位置都会建立依次，然后之后就不会重复递归，放完一下就返回
    // 空间复杂度：O(k*N) 二维表的大小
    public static int f2(int N, int E, int rest, int cur, int[][] dp) {
        if (dp[rest][cur] != -1) {  // 缓存命中
            return dp[rest][cur];
        }
        // 缓存没命中：存储走过的步数
        if (rest == 0) {
            dp[rest][cur] = E == cur ? 1 : 0;
            return dp[rest][cur];
        }
        if (cur == 1) {  // 最左端
            dp[rest][cur] = f2(N, E, rest - 1, cur + 1, dp);
        } else if (cur == N) {  // 最右端
            dp[rest][cur] = f2(N, E, rest - 1, cur - 1, dp);
        } else {  // 中间
            dp[rest][cur] = f2(N, E, rest - 1, cur - 1, dp) +
                    f2(N, E, rest - 1, cur + 1, dp);
        }
        return dp[rest][cur];
    }

    // DP：整理位置之间的依赖，通过依赖来得到我们的步数
    /*
        最左侧的值f(rest, 1)：依赖于f(rest-1, 2)     (依赖于右上角的值)
        最右侧的值f(rest, N)：依赖于f(rest-1, N-1)   (依赖于左上角的值)
        中间二点值f(rest, cur)：依赖于 f(rest-1, cur-1)+f(rest-1, cur+1)  (依赖于右上角和左上角的值的和)
        // 画一个严格表结构：原点在左上角，x轴向下是rest，y轴向右是cur。
     */
    public static int walkWays3(int N, int E, int k, int S) {
        return f3(N, E, k, S);
    }

    /*
        0 1 2 3 4 --> cur
        1
        2
        3
        4   x
        rest
     */
    public static int f3(int N, int E, int rest, int cur) {
        int[][] dp = new int[rest + 1][N + 1];
        dp[0][E] = 1;
        for (int i = 1; i <= rest; i++) {  // 第一行不管
            for (int j = 1; j <= N; j++) {  // 第一列不管
                if (j == 1) {
                    dp[i][j] = dp[i - 1][j + 1];  // 依赖于右上角
                } else if (j == N) {
                    dp[i][j] = dp[i - 1][j - 1];  // 依赖于左上角
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                }
            }
        }
        return dp[rest][cur]; // 相当于从cur->E，花了rest步
    }

    public static void main(String[] args) {
        System.out.println(walkWays(5, 4, 4, 2));
        System.out.println(walkWays2(5, 4, 4, 2));
        System.out.println(walkWays3(5, 4, 4, 2));
    }
}