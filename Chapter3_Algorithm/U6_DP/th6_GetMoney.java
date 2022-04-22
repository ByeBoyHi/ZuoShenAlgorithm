package Chapter3_Algorithm.U6_DP;

import java.util.Arrays;

public class th6_GetMoney {
    /**
     * Q：给一个币值数组，每一个值代表一个钱的面额，然后给出一个钱数。
     * 问，利用这些币值，最后拼出指定的钱数，有多少种方法。
     */

    public static int way1(int[] arr, int aim){
        return process(arr, 0, aim);
    }
    public static int process(int[] arr, int index, int rest){
        if (index==arr.length){  // 面值选完
            return rest==0?1:0;
        }

        int way = 0;
        // 尝试当前面值的所有可能的张数
        // arr[index] 0张 1张 ... 不断尝试下去，不超过rest的情况下
        for (int zhang=0; arr[index]*zhang<=rest; zhang++){
            way+=process(arr, index+1, rest-arr[index]*zhang);
        }
        return way;
    }

    // dp
    /*
        时间复杂度：O(N*(aim^2))
        里面的枚举有一个aim
     */
    public static int way2(int[] arr, int aim){
        if (arr==null || arr.length==0){
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n+1][aim+1];
        dp[n][0] = 1;

        for (int row=n-1; row>=0; row--){  // 当前取的币值的索引
            for (int col=0; col<=aim; col++){  // 当前剩下的钱，从0~aim
                int way = 0;
                // 尝试当前面值的所有可能的张数
                // arr[index] 0张 1张 ... 不断尝试下去，不超过rest的情况下
                for (int zhang=0; arr[row]*zhang<=col; zhang++){
                    way+=dp[row+1][col-arr[row]*zhang];
                }
                dp[row][col] = way;
            }
        }
        return dp[0][aim];
    }

    // dp优化
    /*
        当前值，可以等于前一个区间长度的值加上正下方的值
        时间复杂度：O(N*aim)
     */
    public static int way3(int[] arr, int aim){
        if (arr==null || arr.length==0){
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n+1][aim+1];
        dp[n][0] = 1;

        for (int row=n-1; row>=0; row--){  // 当前取的币值的索引
            for (int col=0; col<=aim; col++){  // 当前剩下的钱，从0~aim
                dp[row][col] = dp[row+1][col];
                if (col-arr[row]>=0){ // 确定前一个区间没有出界
                    // 前一个区间的值+正下方的值
                    dp[row][col]+=dp[row][col-arr[row]];
                }
            }
        }
        return dp[0][aim];
    }


    // 对数器
    public static int[] generateRandomArr(int maxSize, int maxValue){
        int[] arr = new int[(int)(Math.random()*(maxSize+1))]; // 长度随机
        for (int i=0; i<arr.length; i++){
            arr[i] = (int)((maxValue+1)*Math.random());  // 值随机
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("hi?");
        int N = 10;
        int maxSize = 10;
        int maxValue = 10;
        for (int i=0; i<N; i++){
            int aim = (int)(Math.random()*maxValue*3+1);
            int[] arr = generateRandomArr(maxSize, maxValue);
            if (way1(arr, aim)!=way2(arr, aim) || way2(arr, aim)!=way3(arr, aim)){
                System.out.println("oops!");
                break;
            }
//            System.out.println(Arrays.toString(arr));
        }
    }
}
