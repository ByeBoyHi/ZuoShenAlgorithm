package Chapter6_Hard.Q;

public class Q2_MaxLen1 {

    public static void main(String[] args) {
        int[] arr = new int[]{6,1,2,2,2,1,1,1,1,1};
        System.out.println(maxLen(arr, 5));
    }

    /*
        求一个数组的连续最大长度，要求长度范围内的和等于指定的key
     */
    public static int maxLen(int[] arr, int key){
        int L = 0;
        int R = 0;
        int len = 1;
        int sum = arr[0];
        // 解1
//        for (int j : arr) {
//            if (sum + j <= key) {
//                sum += j;
//                R++;
//                if (sum == key) {
//                    len = Math.max(len, R - L + 1);
//                }
//            } else {
//                sum -= arr[L];
//                L++;
//            }
//            if (L > R) {
//                R = L;
//            }
//        }

        // 解2
        while (R<arr.length){
            if (sum==key){
                len = Math.max(len, R-L+1);
                // 等于之后，也要更新一下区间，否则每次循环都是等于，进入死循环了
                sum-=arr[L++];
            }else if (sum<key){
                R++;
                if (R!=arr.length){
                    sum+=arr[R];
                }
            }else{
                sum-=arr[L++];
            }
        }

        return len;
    }
}
