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
            if (arr[left] + arr[right] > limit) {  // 让right往小移动才有可能坐下
                right--;  // 左右边的人只能单独坐一艘船
            } else {  // 就算重合了，当作两个人算了也不要紧，因为我们算的都是一艘船
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
        int leftLeave = 0;  // 左侧剩下的个数
//        int rightLeave = 0; // 右侧剩下的个数
        int left = -2;       // 左指针
        int right = 0;      // 右指针
        int ans = 0;
        for (int i=0; i<arr.length; i++){
            if (arr[i]>limit/2){
                 left=i-1;
                 right = i;
                 break;
            }
        }
        if (left==-1){  // 说明第一个元素就比limit的一半大，即所有的都只能单独做一艘船
            return arr.length;
        }
        if (left==-2){  // 说明没有遇到比limit一半大的数字，即都可以成双成对的坐船
            return (arr.length+1)/2;
        }

        // 正常情况
        while (left>=0 && right<arr.length){
            if (arr[left]+arr[right]<=limit){  // 能坐下一艘船
                left--;
                right++;
                ans++;
            }else { // 坐不下一艘船，因为是从中间往两边移动，所以中间坐不了的，right往右更坐不了，所以left往左移动
                left--;
                leftLeave++;
            }
        }

        return ans+leftLeave+arr.length-right;
    }
}
