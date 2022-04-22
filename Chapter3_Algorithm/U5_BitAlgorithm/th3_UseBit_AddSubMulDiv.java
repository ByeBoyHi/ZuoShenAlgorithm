package Chapter3_Algorithm.U5_BitAlgorithm;

public class th3_UseBit_AddSubMulDiv {
    // Q3：使用位运算实现加减乘除

    /*
       ------ 加法 ------
       a=13: 1101
       b=7 : 0111
          ^  1010 (得到无进位相加)
          &  0101 (得到发生进位的位置)
       &<<1: 1010 (得到需要+1的位置)

       a = : 1010 (^的结果)
       b = : 1010 (&<<1的结果)
          ^  0000
       &<<1  10100
       当^=0的时候，说明所有位置都是相同的，那么&<<1就是结果
       (或者下一轮后，&运算结果为0，也可以作为终止条件)
       溢出：一个极大值减去一个极小值
     */
    public static int add(int a, int b) {
//        int temA = a;
//        int temB = b;
        while (b != 0) {
            int sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
//        if ((temA^temB)>a){
//            throw new RuntimeException(th3_UseBit_AddSubMulDiv.class + "：数值溢出越界。");
//        }
        return a;
    }

    /*
        ------ 减法 ------
        a-b = a+(-b)
        -b = ~b+1
     */
    // 取相反数：取反加一
    public static int negNum(int n) {
        return add(~n, 1);
    }

    public static int subtract(int a, int b) {
        return add(a, negNum(b));
    }

    /*
        ------ 乘法 ------
           1011
         * 0110
         -------
           0000
          1011
         1011
        0000
        -------
        求和：0100 0010
        移位求和，第几位是1，就移动几位求和
     */
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) == 1) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }

    /*
        ------ 除法 ------
        1. 方法一：就是不断的减，直到变号
        2. 方法二：除数b向左移动，直到不超过被除数a，然后a-b，剩下的再用同样的规则，直到a<b
     */
    public static int divide(int a, int b) {
        if (b==0){
            throw new RuntimeException("divisor is zero!");
        }
        // 全部当作正数处理，然后最后再判断符号是否取反
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i=31; i>-1; i=subtract(i, 1)){
            // x右移比左移更安全，不会影响符号位
            // 然后y左移让x减掉
            if ((x>>i)>=y){
                res |= (1<<i);
                x = subtract(x, y<<i);
            }
        }
        // 同号取正，否则取相反数
        return isNeg(a)^isNeg(b)?negNum(res):res;
    }

    public static boolean isNeg(int n) {
        return n < 0;
    }

    public static void main(String[] args) {
        System.out.println("加法：" + add(13, 7));
        System.out.println("减法：" + subtract(13, 7));
        System.out.println("乘法：" + multi(13, -7));
        System.out.println("除法：" + divide(-5, 2));
        System.out.println("----------------");
        System.out.println(~7 + 1);
        // 最高位做了位运算
        System.out.println(-2 ^ -5);
        System.out.println(-2 & -5);
        // 无符号右移和右移
        System.out.println(-12 >> 1);
        System.out.println(-12 >>> 1); // 这个会把符号位一起移动，然后高位会用0补充
//        System.out.println("-------------");
//        int b = -7;
//        while (b != 0) {
//            System.out.println(b >>>= 1);
//        }
        System.out.println("--------------------");
        System.out.println(5<<1);
        System.out.println(-5<<1);
        System.out.println(5>>1);
        System.out.println(-5>>1); // 负数左移不完全和除以2对等
        System.out.println(-5/2);
        System.out.println(-5>>>1);
    }
}
