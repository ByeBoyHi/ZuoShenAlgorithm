package Chapter6_Hard.Q;

public class Q7_SnackMove {
    /*
        这条蛇可以从当前格子往：右侧、右上、右下走。
        那么反过来思考，当前位置可以由：左侧、左上、左下走过来。
        有一个超能力，可以让整数变成负数，但是只能用一次。
     */

    static class Info {
        int no;  // 没有用超能力时，走到这个位置的最大和
        int yes; // 用超能力的时候，走到这个位置的最大和

        public Info() {
        }

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    // 主方法
    public static int f1(int[][] matrix){
        int ans = -1;
        // 从最左侧出发
        for (int i=0; i<matrix.length; i++){
            Info info = process(matrix, i, 0);
            ans = Math.max(ans, Math.max(info.no, info.yes));
        }
        return ans;
    }

    // 暴力递归，每个格子展开执行
    public static Info process(int[][] matrix, int row, int col) {
        // 最左侧
        if (col == 0) {
            return new Info(matrix[row][col], -matrix[row][col]);  // 第一个值不使用超能力，第二个值使用超能力
        }

        // 记录之前位置的 no 和 yes
        int preNo = -1;
        int preYes = -1;
        // p1: 左上
        if (row > 0) {  // 前提是有左上方的格子
            Info leftUp = process(matrix, row - 1, col - 1);
            if (leftUp.no >= 0) {
                preNo = leftUp.no;
            }
            if (leftUp.yes >= 0) {
                preYes = leftUp.yes;
            }
        }

        // p2: 左侧
        Info left = process(matrix, row, col - 1);
        preNo = Math.max(preNo, left.no);
        preYes = Math.max(preYes, left.yes);

        // p3: 左下
        if (row < matrix.length - 1) {
            Info leftDown = process(matrix, row + 1, col - 1);
            if (leftDown.no >= 0) {
                preNo = Math.max(preNo, leftDown.no);
            }
            if (leftDown.yes >= 0) {
                preYes = Math.max(preYes, leftDown.yes);
            }
        }

        // 记录当前递归层的 no 和 yes
        int no = -1;
        int yes = -1;
        if (preNo >= 0) {  // 之前没有用能力且有用
            no = preNo + matrix[row][col];
            yes = preNo - matrix[row][col];
        }
        if (preYes >= 0) {  // 之前用了能力且有用
            yes = Math.max(yes, preYes + matrix[row][col]);
        }

        return new Info(no, yes);
    }
    //--------------------------------------

    // 主方法
    public static int f2(int[][] matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        Info[][] dp = new Info[n][m];
        int ans = -1;
        for (int i=0; i<n; i++){
            process2(matrix, i, 0, dp);
        }

        for (int i=0; i<n; i++){
            if (dp[i][m - 1] != null) {
                ans = Math.max(ans, Math.max(dp[i][m-1].no, dp[i][m-1].yes));
            }
        }

        return ans;
    }

    // 加缓存
    public static Info process2(int[][] matrix, int row, int col, Info[][] dp) {
        // 最左侧
        if (col == 0) {
            return new Info(matrix[row][col], -matrix[row][col]);  // 第一个值不使用超能力，第二个值使用超能力
        }
        if (dp[row][col] != null) {
            return dp[row][col];
        }

        // 记录之前位置的 no 和 yes
        int preNo = -1;
        int preYes = -1;
        // p1: 左上
        if (row > 0) {  // 前提是有左上方的格子
            Info leftUp = process2(matrix, row - 1, col - 1, dp);
            if (leftUp.no >= 0) {
                preNo = leftUp.no;
            }
            if (leftUp.yes >= 0) {
                preYes = leftUp.yes;
            }
        }

        // p2: 左侧
        Info left = process2(matrix, row, col - 1, dp);
        preNo = Math.max(preNo, left.no);
        preYes = Math.max(preYes, left.yes);

        // p3: 左下
        if (row < matrix.length - 1) {
            Info leftDown = process2(matrix, row + 1, col - 1, dp);
            if (leftDown.no >= 0) {
                preNo = Math.max(preNo, leftDown.no);
            }
            if (leftDown.yes >= 0) {
                preYes = Math.max(preYes, leftDown.yes);
            }
        }

        // 记录当前递归层的 no 和 yes
        int no = -1;
        int yes = -1;
        if (preNo >= 0) {  // 之前没有用能力且最终值有用
            no = preNo + matrix[row][col];  // 依然不用
            yes = preNo - matrix[row][col]; // 用一次
        }
        if (preYes >= 0) {  // 之前用了能力且最终值有用
            yes = Math.max(yes, preYes + matrix[row][col]);  // 用了一次
        }

        // 更新缓存
        dp[row][col] = new Info(no, yes);

        return dp[row][col];
    }
    // -------------------------------------------

    // dp
    public static int processDP(int[][] matrix) {

        Info[][] dp = new Info[matrix.length][matrix[0].length];
        int n = matrix.length;
        int m = matrix[0].length;

        int ans = -1;

        // 预处理第一列
        for (int i = 0; i < n; i++) {
            dp[i][0] = new Info(matrix[i][0], -matrix[i][0]);
            ans = Math.max(dp[i][0].no, Math.max(ans, dp[i][0].yes));
        }

        for (int row = 0; row < n; row++) {
            for (int col = 1; col < m; col++) {
                // 记录之前位置的 no 和 yes
                int preNo = -1;
                int preYes = -1;
                // p1: 左上
                if (row > 0) {  // 前提是有左上方的格子
                    Info leftUp = dp[row - 1][col - 1];
                    if (leftUp.no >= 0) {
                        preNo = leftUp.no;
                    }
                    if (leftUp.yes >= 0) {
                        preYes = leftUp.yes;
                    }
                }

                // p2: 左侧
                Info left = dp[row][col - 1];
                preNo = Math.max(preNo, left.no);
                preYes = Math.max(preYes, left.yes);

                // p3: 左下
                if (row < n - 1) {
                    Info leftDown = dp[row + 1][col - 1];
                    if (leftDown.no >= 0) {
                        preNo = Math.max(preNo, leftDown.no);
                    }
                    if (leftDown.yes >= 0) {
                        preYes = Math.max(preYes, leftDown.yes);
                    }
                }

                // 记录当前递归层的 no 和 yes
                int no = -1;
                int yes = -1;
                if (preNo >= 0) {  // 之前没有用能力且最终值有用
                    no = preNo + matrix[row][col];  // 依然不用
                    yes = preNo - matrix[row][col]; // 用一次
                }
                if (preYes >= 0) {  // 之前用了能力且最终值有用
                    yes = Math.max(yes, preYes + matrix[row][col]);  // 用了一次
                }

                // 更新缓存
                dp[row][col] = new Info(no, yes);
            }
        }

        for (int i=0; i<n; i++){
            if (dp[i][m - 1] != null) {
                ans = Math.max(ans, Math.max(dp[i][m-1].no, dp[i][m-1].yes));
            }
        }

        return ans;
    }
}
