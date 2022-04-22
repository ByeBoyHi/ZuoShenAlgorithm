package Chapter1_Sort;

import java.util.Arrays;

// 从[i,n]选一个最小的放在第i个位置，然后找[i-1,n]
// 可能越过相等的值，导致不稳定
public class th3_SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[]{
                5,1,34,8,1,64,9,7,6
        };
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void selectSort(int[] arr){
        for(int i=0; i<arr.length; i++){
            int min = i;
            for (int j=i; j<arr.length; j++){
                if (arr[j]<arr[min]){
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
