package Chapter6_Hard.Q;

/**
 * <给一个字符串，然后删除若干个字符从而使得剩下的形成一个回文串，问最多可以形成多少种回文串，不同位置的相同字符不算一种回文，空串不算回文><br>
 * Time: 2022/5/21 <br>
 * User: HeyBoy<br>
 */
public class Q12_DelStrCountPalindNum {
     /**
     * 对空间递归进行优化，使用dp数组避开递归 <br>
     * 1. 对角线以下无效<br>
     * 2. 对角线上的值为1（长度为1默认回文）<br>
     * 3. 对角线上的一根线，如果两个字符相等则回文，值为3，否则为2<br>
     * 4. 其他部分：<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.1 以i开头，以j结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.2 不以i开头，不以j结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.3 以i开头，不以j结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4.4 不以i开头，以j结尾<br>
     * 对于 dp[i][j-1]，包括了以下四部分：<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;1 以i开头，以 j-1 结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;2 不以i开头，不以 j-1 结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;3 以i开头，不以 j-1 结尾<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;4 不以i开头，以 j-1 结尾<br>
     * 也就是说对于dp[i][j-1]来说，包括了(以i开头，不以j结尾___3)和(不以i开头，不以j结尾___2)<br>
     * 对于 dp[i+1][j] 包括了：(不以i开头，以j结尾___4)和(不以i开头，不以j结尾___2)<br>
     *<br>
     * ①因为我们需要回文串，因此当i==j的时候，我们就需要 dp[i+1][j-1]+1，即取(不以i开头不以j结尾)且(头尾相等)的时候。<br>
     * 所以所有的情况为 dp[i][j-1]+dp[i+1][j]-dp[i+1][j-1]+dp[i+1][j-1]+1=dp[i][j-1]+dp[i+1][j]+1<br>
     * ②当i!=j的时候，说明i..j回文是不可能了，那么把子回文加上，也就是情况 2 3 4，即dp[i][j-1]+dp[i+1][j]-dp[i+1][j-1]。<br>
     * 因为多加了情况3，所以要减去dp[i+1][j-1]。<br>
     *<br>
     */
    public static int process(char[] chs){
        if (chs==null || chs.length==0){
            return 0;
        }
        int m = chs.length;
        int[][] dp = new int[m][m];
        for (int i=0; i<m-1; i++){
            dp[i][i] = 1;
            dp[i][i+1] = chs[i]==chs[i+1]?3:2;
        }
        dp[m-1][m-1] = 1;

        for (int row=m-2; row>=0; row--){
            for (int col = row+1; col<m; col++){
                dp[row][col] = dp[row][col-1] + dp[row+1][col] - dp[row+1][col-1];
                if (chs[row]==chs[col]){
                    dp[row][col] += dp[row+1][col-1]+1;
                }
            }
        }

        return dp[0][m-1];
    }

    public static void main(String[] args) {
        // aba a b a aa
//        String str = "aba";
        // aabb aa bb a a b b
        String str = "aabb";
        System.out.println(process(str.toCharArray()));
    }

}
