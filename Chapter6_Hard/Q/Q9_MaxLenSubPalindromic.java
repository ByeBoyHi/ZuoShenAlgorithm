package Chapter6_Hard.Q;

/**
 * <寻找最长回文子序列>
 * 2022/5/21
 * HeyBoy
 */
public class Q9_MaxLenSubPalindromic {
    /**
     * 给一个二维数组dp，dp[i][j]表示从i~j的最长回文子序列的长度<br>
     * 开始初始化：<br>
     * 1. 对于i>j的部分，全部非法。<br>
     * 2. 对于i==j的部分，全部是1。<br>
     * 3. 对于i==j-1的部分，如果str[i]==str[j]，那么等于2，否则等于1。<br>
     * 4. 对于其他部分：<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.1 以i开头，以j结尾     当str[i]==str[j],dp[i+1][j-1]+2<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.2 不以i开头，以j结尾   dp[i+1][j]<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.3 以i开头，不以j结尾   dp[i][j-1]<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.4 不以i开头，不以j结尾  dp[i+1][j-1]<br>
     *
     */
    public static int process(String str){
        char[] chs = str.toCharArray();
        int m = chs.length;
        int[][] dp = new int[m][m];
        for (int i=0; i<m; i++){
            dp[i][i]=1; // 对角线
            if(i!=m-1) {// 对角线上面那一条
                if(chs[i]==chs[i+1]){
                    dp[i][i+1] = 2;
                }else{
                    dp[i][i+1] = 1;
                }
            }
        }

        for (int row = m-2; row>=0; row--){
            for (int col = row+1; col<m; col++){
                // 头尾相等并且中间是回文
                if (chs[row]==chs[col]&&dp[row+1][col-1]==col-(row+1)){
                    dp[row][col] = dp[row+1][col-1]+2;
                }else{ // 头尾不等，取其他情况的最大值
                    dp[row][col] = Math.max(dp[row+1][col], Math.max(dp[row][col-1], dp[row+1][col-1]));
                }
            }
        }

        return dp[0][m-1];
    }

    public static void main(String[] args) {
        String str = "ababababababa";
        System.out.println(process(str));
    }
}
