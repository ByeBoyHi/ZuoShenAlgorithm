package Chapter3_Algorithm.U2_Recursion;

public class th6_N_To_A {
    /*
        1对应A，2对应B，以此类推
        111 -> AAA 或者 AK 或者 KA。
        返回一个数字可以返回多少种字符串
        1. 在前面i-1的位置都固定之后，如果当前位置出现0，那么一定不能转换。
        2. 如果当前位置是3~9，那么只能使用当前位置，而无法和下一个位置结合起来。
        3. 如果当前位置是1，那么可以考虑使用当前位置或者当前位置加下一个位置一起。
        4. 如果是2，当下一个数字小于等于6的时候，和1相同，否则和3~9相同。
     */
    public static int process(char[] str, int i){
        if (i==str.length){  // 做完了所有决定
            return 1;
        }
        if (str[i]=='0'){ // 0不能单独用
            return 0;
        }
        if (str[i]=='1'){  // 1
            int res = process(str, i+1);
            if (i+1<str.length){
                res+=process(str, i+2); // 两个结合起来
            }
            return res;
        }
        if (str[i]=='2'){  // 2
            int res = process(str, i+1);
            if (i+1<str.length && str[i+1]<='6' && str[i+1]>='0'){ // 0~6
                res+=process(str, i+2);  // 两个结合起来
            }
            return res;
        }
        return process(str, i+1); // 3~9
    }
    public static int number(String str){
        return process(str.toCharArray(), 0);
    }

    public static void main(String[] args) {
        System.out.println(number("11111"));
    }
}
