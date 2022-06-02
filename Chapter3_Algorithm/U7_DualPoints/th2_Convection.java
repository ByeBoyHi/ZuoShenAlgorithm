package Chapter3_Algorithm.U7_DualPoints;

/**
 * 对向双指针：解决两数之和 <br>
 * Time: 2022/6/2 <br>
 * User: HeyBoy <br>
 */
public class th2_Convection {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,5,6,8,9,10,11};
        System.out.println(f(arr, 15));
        System.out.println(f(arr, 22));
    }

    public static boolean f(int[] arr, int target){
        int left = 0;
        int right = arr.length-1;
        while (left<right){
            if (arr[left]+arr[right]>target){
                right--;
            }else if (arr[left]+arr[right]<target){
                left++;
            }else {
                return true;
            }
        }
        return false;
    }

}
