package Chapter6_Hard.Q;

/**
 * <将一个字符串切割成若干个回文子串，要求回文子串的数量最少>
 * Time: 2022/5/21
 * User: HeyBoy
 */
public class Q11_CutMinNumPalindromic {

    static boolean[][] isPalind;

    /**
     * 预处理：使用一个数组进行预先处理范围内的字符串是否回文。
     * 进行预处理后，时间复杂度从O(n^3)降为O(n^2)
     * 判断str[i...j]是否回文
     * 只需要判断str[i+1...j-1]是否回文且str[i]==str[j]
     */
    public static void preProcess(char[] str){
        int m = str.length;
        isPalind = new boolean[m][m];
        for (int i=0; i<m-1; i++){
            isPalind[i][i] = true;
            isPalind[i][i+1] = str[i]==str[i+1];
        }
        isPalind[m-1][m-1] = true;

        for (int row=m-2; row>=0; row--){
            for (int col=row+1; col<m; col++){
                if (isPalind[row+1][col-1] && str[row]==str[col]){
                    isPalind[row][col] = true;
                }
            }
        }
    }

    // 用一个dp去代替递归栈的使用
    public static int process1(char[] str){
        if (str==null || str.length==0){
            return 0;
        }
        int m = str.length;
        // dp[i]表示从i到最后，有多少个回文子串
        int[] dp = new int[m+1];
        for (int i=m-2; i>=0; i--){  // i是当前位置
            // 初始化为m-i意思是后面的m-i个字符分别自成一个回文串的长度
            dp[i] = m-i;
            for (int j=i; j<m; j++){
                if (isPalind[i][j]){  // 从i...j是回文，那么判断取哪种可能，可以使得回文子串的数量最少
                    dp[i] = Math.min(dp[i], dp[j+1]+1);
                }
            }
        }
        return dp[0];
    }

    public static int F(String str){
        char[] chs = str.toCharArray();
        preProcess(chs);
        return process1(chs);
    }


    /**
     * 对每个位置进行尝试。
     * 对第i个位置尝试，[start...i]是回文串，然后从i+1递归处理。
     * 需要一个函数进行回文串的判断。
     */
    // 主运行函数
    public static int process(char[] str, int index){
        if (index==str.length){  // 已经越界了，表示没有回文了
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int end=index; end<str.length; end++){
            if (isValid(str, index, end)){
                ans = Math.min(ans, 1 + process(str, end+1));
            }
        }
        return ans;
    }
    // 判断chs数组的start~end范围是否是回文串
    // 把这个方法嵌套进递递归主函数里面，会使得整个复杂度是O(n^3)
    public static boolean isValid(char[] chs, int start, int end){
        while (start<=end){
            if (chs[start++]!=chs[end--]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(F("aba"));
    }

}
