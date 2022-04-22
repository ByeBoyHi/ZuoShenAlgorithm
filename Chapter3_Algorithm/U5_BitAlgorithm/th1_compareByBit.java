package Chapter3_Algorithm.U5_BitAlgorithm;

public class th1_compareByBit {
    // Q1

    // 保证参数n，不是1就是0的情况
    // 1 --> 0
    // 0 --> 1
    public static int flip(int n) {
        return n ^ 1;
    }

    // n是非负数，返回1
    // n是负数，返回0
    public static int sign(int n) {
        return flip((n >> 31) & 1);
    }

    // 互斥相加的形式
    public static int getMax1(int a, int b) {
        int c = a - b;  // 缺点：a-b可能溢出
        int scA = sign(c);  // a>=b的时候，scA=1；否则scA=0
        int scB = flip(scA); // scA=1的时候，scB=0；否则scB=1
        // 互斥发出，会返回a或者b中的一个数字
        return scA * a + scB * b;
    }

    // 解决溢出
    public static int getMax2(int a, int b) {
        int c = a - b; // 差值
        int sa = sign(a);  // a的符号
        int sb = sign(b);  // b的符号
        int sc = sign(c);  // 差值的符号
        int difSab = sa ^ sb;  // a和b是否不同号，不同号为1，否则为0
        int sameSab = flip(difSab);  // a和b是否同号，同号为1
        /*
            返回A的情况：
                1. 在a和b不同号的情况下，a为正数的时候，返回a
                2. 在a和b同号的情况下，差值非负的时候，返回a
                这两种情况是互斥的。
            溢出情况：2^31-1 - (-2^31)
            这时候他俩异号，异号的话，肯定返回正数那个就好了，避开差值，就不会有溢出的影响了
         */
        int returnA = difSab * sa + sameSab * sc;

        int returnB = flip(returnA);
        return a * returnA + b * returnB;
    }

    public static void main(String[] args) {
        int a = 14;
        int b = 1;
        System.out.println(getMax1(a, b));
        System.out.println(getMax2(a, b));
        a = Integer.MAX_VALUE;
        b = Integer.MIN_VALUE;
        System.out.println(getMax1(a, b));  // wrong answer: overflow
        System.out.println(getMax2(a, b));
    }
}
