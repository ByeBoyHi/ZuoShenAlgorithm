package Chapter5_MiddleLevel.Qs;

public class th3_TransStTorNumber {
    /*
        Q3: 给一个字符串，判断这个字符串是不是一个合理的数字字符串，
        并且将这个字符串转换为数字类型。
     */

    // 判断是否是合理数字，再进行转型
    public static boolean isValid(String str){
        if (
                // 字符串长度为0，则不合理
                (str.length()==0)
            ||
                // 第一个字符为负号，并且字符串长度小于2或者第二个字符不是1~9的数字，则不合理
                (str.charAt(0)=='-' && (str.length()<2 || str.charAt(1)<'1' || str.charAt(1)>'9'))
            ||
                // 第一个字符为0，则不合理
                (str.charAt(0)=='0')
        ) {
            return false;
        }

        // 在字符串的任意位置，如果有不合理字符，则返回false
        boolean neg = str.charAt(0) == '-';
        for (int i=neg?1:0; i<str.length(); i++){
            if (str.charAt(i)!='-' && str.charAt(i)<'0' || str.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    // 对合理的数字进行转型
    public static int transfer(String number){
        if (!isValid(number)){
            throw new RuntimeException("It's not valid.");
        }

        // 用两个变量来判断越界
        // 因为字符串数字是用乘以10来转换的
        int minq = Integer.MIN_VALUE/10; // 除数
        int minr = Integer.MIN_VALUE%10; // 余数
        boolean neg = number.charAt(0)=='-';
        int res = 0;
        int cur = 0;
        for (int i=neg?1:0; i<number.length(); i++){
            cur = '0'-number.charAt(i); // 记录负数的绝对值，因为负数比正数多一个越界值
            // 越界判断：因为这里面的求和都是按照负数形式求和的
            if (res<minq || (res==minq && cur<minr)){
                throw new RuntimeException("not convert.");
            }
            res = res*10+cur;
        }
        if (!neg && res==Integer.MIN_VALUE){
            throw new RuntimeException("not convert.");
        }
        return neg ? res:-res;
    }

    public static void main(String[] args) {
        String number = "-015483121354";
        System.out.println(transfer(number));
    }
}
