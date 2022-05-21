package Chapter6_Hard.Q;

/**
 * <给一个字符串中间添加字符，使得最后整个字符串是一个回文串，并且返回最终的回文字符串> <br>
 * 2022/5/21<br>
 * HeyBoy<br>
 */
public class Q10_AddStrAndMinCount {
    /**
     * 对于一个字符串Str，长度为m。<br>
     * 用一个二维表dp[m][m]来记录，dp[i][j]表示至少填多少个字符串可以使得str[i...j]回文
     * 初始化：<br>
     * 1. 对于对角线，初始值为0，一个字符串默认为回文。<br>
     * 2. 对于对角线上面的线，如果str[i]==str[i+1]，则为0，否则为1（添加在头或尾）<br>
     * 3. 其他位置：<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;3.1 以i结尾，不以j结尾：dp[i][j-1]+1——>在头部添加j字符，使得i-1...j回文<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;3.2 不以i结尾，以j结尾：dp[i+1][j]+1——>在尾部添加i字符，使得i...j+1回文<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;3.3 以i结尾，以j结尾：当str[i]==str[j]的时候，dp[i][j]=dp[i+1][j-1]，也就是中间的回文结果，因为不需要添加新的字符<br>
     * <br>
     * 通过以上方式建立完二维表后，从右上角往左下角移动，因为右上角[0...m]是我们的最终回文结果。<br>
     * 移动过程中，判断str[i]==str[j]，如果相等，取三个可能性的最小值走。<br>
     * 如果不等，取3.1、3.2两个格子中小的走。<br>
     */
    public static Object[] process(String str){
        int m = str.length();
        char[] chs = str.toCharArray();
        int[][] dp = new int[m][m];
        for (int i=0; i<m-1; i++){ //对角线初始值为0，我们就不初始化了，直接初始化上面那条线
            if (chs[i]!=chs[i+1]) {
                dp[i][i + 1] = 1;
            }
        }
        for (int row=m-2; row>=0; row--){
            for (int col=row+1; col<m; col++){
                dp[row][col] = Math.min(dp[row][col-1], dp[row+1][col])+1;
                if (chs[row]==chs[col]){
                    dp[row][col] = Math.min(dp[row][col], dp[row+1][col-1]);
                }
            }
        }

        char[] ans = new char[m+dp[0][m-1]];
        // 从右上角开始移动
        int row = 0;
        int col = m-1;
        int left = 0;
        int right = ans.length-1;
        while (dp[row][col]!=0){  // 这一部分只是把需要插入的字符添加完了，但是中间不需要添加的部分需要我们自己加进去
            // "a123c3c21"
            // -->"a12c3c3c21a"
            int a = dp[row][col-1];  // i..j-1
            int b = dp[row+1][col];  // i+1..j
            int c = Integer.MAX_VALUE;
            if (chs[row]==chs[col]){
                c = dp[row+1][col-1];
            }
            if (c<a && c<b){  // c最小
                ans[left++] = chs[row++];
                ans[right--] = chs[col--];
            }else if(a<c && a<b){  // a最小
                ans[left++] = chs[col];
                ans[right--] = chs[col--];
            }else{  // b最小
                ans[left++] = chs[row];
                ans[right--] = chs[row++];
            }
        }
        while (left<=right){
            ans[left++] = chs[row++];
        }

        return new Object[]{ans, dp[0][m-1]};
    }

    public static void main(String[] args) {
        String str = "a123c3c21";
        Object[] objs = process(str);
        for( char c: (char[])objs[0]){
            System.out.print(c+" ");
        }
        System.out.println(": " + objs[1]);
    }

}
