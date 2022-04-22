package Chapter3_Algorithm.U5_BitAlgorithm;

public class th2_IsPower {

    // 是不是2的幂
    // 或者拿到最右侧的1
    public static boolean isPowerOf2(int n){
        return (n&(n-1))==0;
    }

    // 是不是4的幂
    // 2的平方幂
    public static boolean isPowerOf4(int n){
        int t = (int)Math.sqrt(n);
        if (t*t!=n){
            return false;
        }
        return (t&(t-1))==0;
    }
    // 4的幂的二进制特征：居然没有我上面的那个速度快？？？
    /*
        0000 0001
        0000 0100
        0001 0000
        0100 0000
        位置分别是 0 2 4 6 8跳跃
        那么和对应的二进制数进行位运算的话： 1010 1010
        假如有一个数字：0100 0000 & 1010 1010 == 0，那么这个数字就是4的幂
        记住：0101 0101 不是4的幂，就像 1111 1111 不是2的幂一样，他只是有很多的2次方加在一起
     */
    public static boolean isPowerOf4_2(int n) {
        if(n==0) return false;
        // 第一个判断是否只有一个1
        // 第二个判断这个1是否在偶数索引位上
        // 因为 10：1010 的二进制位，就是我们上面举的例子
        // 也可以用 5：0101 那么判断要换成 !=0
        return ((n & (n - 1)) == 0) && ((n & 0xAAAAAAAA) == 0);
    }

    public static void main(String[] args) {
        int a = 8;
        int b = 16;
        int c = 17;
        System.out.println(isPowerOf2(a));
        System.out.println(isPowerOf2(b));
        System.out.println(isPowerOf2(c));
        System.out.println("------------------");
        System.out.println(isPowerOf4(a));
        System.out.println(isPowerOf4(b));
        System.out.println(isPowerOf4(c));
    }
}
