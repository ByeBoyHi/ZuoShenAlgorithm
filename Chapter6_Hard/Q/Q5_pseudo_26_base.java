package Chapter6_Hard.Q;

import java.util.HashMap;
import java.util.Map;

public class Q5_pseudo_26_base {

    /*
        A:1
        B:2
        ...
        Z:26
        AA:27
        AB:28
        ...
        AZ:52
        ...
        这就是一个伪26进制，从1~26，分别用A~Z表示

        伪26进制：
        对于一个数字，先除以每一个位置的数：26^0 26^1 26^2 ...
        然后反过来看最多需要多少个当前位置。

        真26进制：
        直接就是计算：26^0 26^1 26^2 ...
        从而得到每个位置的数字
     */

    static int BASE = 1;

    static String[] alphabet = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    // 传入一个数字，返回对应的ABC..字符串，对应关系如上
    public static String Pseudo_26Base(int number){
        StringBuilder sb = new StringBuilder();
        Map<Integer, Integer> map = new HashMap<>();
        int powerNum = 0;
        while (number>=BASE){
            number-=BASE;
            map.put(powerNum, 1);
            powerNum++;
            BASE*=26;
        }

        BASE/=26;
        powerNum--;

        while (powerNum>=0 && number!=0){
            int divisor = number/BASE; // 除数
            int remainder = number%BASE; // 余数
            map.put(powerNum, map.get(powerNum)+divisor);
            powerNum--;
            number = remainder;
            BASE/=26;
        }

        for (int value: map.values()){
            sb.append(alphabet[value-1]);
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(Pseudo_26Base(703));
    }

}
