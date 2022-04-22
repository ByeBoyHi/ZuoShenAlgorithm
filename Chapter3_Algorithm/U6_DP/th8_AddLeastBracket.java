package Chapter3_Algorithm.U6_DP;

public class th8_AddLeastBracket {
    // 给一个字符串，这个字符串里面只有左括号和右括号
    // 判断这个字符串的左右括号是否能合理匹配
    // 返回至少需要补上多少个括号才能使这个字符串的括号左右匹配合法

    // 这个方法只是用于判断整体需要的括号
    // 并不考虑左右括号的方向是否匹配
    public static int process(String str){
        char[] chs = str.toCharArray();
        int count = 0;
        for (char ch : chs) {
            if (ch == '(') count++;
            else if (ch == ')') count--;
        }
        // 左括号大于右括号，返回正数
        // 左括号小于右括号，返回负数
        // 否则返回0，合理括号匹配
        return count;
    }


    // ans来记录需要多少个左括号
    // count记录最后有多少左括号需要右括号
    // 这样可以记录有多少个右括号、左括号需要合理的对应括号匹配
    public static int process2(String str){
        int ans = 0;
        int count = 0;
        for (int i=0; i<str.length(); i++){
            if (str.charAt(i)=='('){
                count++;
            }else {
                if (count==0){
                    ans++;
                }else {
                    count--;
                }
            }
        }
        return ans+count; //以此得到需要的左括号和右括号的总数
    }
}
