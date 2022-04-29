package Chapter6_Hard.Q;

import java.util.Arrays;

public class Q8_BoatNumber {
    /*
        数组arr，长度为N，代表每个人的体重。
        给出limit，表示一艘船的限重。
        一艘船最多坐两人，问需要多少艘船可以一次运完所有人。
     */
    public int boats(int[] arr, int limit){
        // 从小到大排序
        Arrays.sort(arr);
        int num = 0;
        int left = 0;
        int right = arr.length-1;
        while (left<=right){
            if (arr[left] + arr[right] > limit) {
                right--;  // 左右边的人只能单独坐一艘船
            } else {
                right--;
                left++;
            }
            num++;
        }
        return num;
    }
    // 左神方法
    public int boats2(int[] arr, int limit){
        Arrays.sort(arr);
        int leftLeave = 0;
        int rightLeave = 0;
        int left = 0;
        int right = 0;
        for (int i=0; i<arr.length; i++){
            if (arr[i]>limit/2){

            }
        }
        return 0;
    }
}
