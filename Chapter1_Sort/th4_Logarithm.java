package Chapter1_Sort;

import java.util.Arrays;

public class th4_Logarithm {

    public static void main(String[] args){
        // test start
        int maxSize = 10;
        int maxValue = 20;
        int counts = 100000;  // 10万次测试
        for (int i=0; i<counts; i++){
            int[] arr = generateRandomArr(maxSize,maxValue);
            int[] copy = copyArray(arr);
            insertSort(arr);
            comparator(copy);
            if (!isEquals(arr, copy)){
                System.out.println("不合格");
                break;
            }
        }
        System.out.println("合格");
    }

    // for test
    public static void insertSort(int[] arr){
        for (int i=0; i<arr.length-1; i++){
            for (int j=i+1; j>0; j--){
                // 插入排序：只要比前面的小，就交换位置
                if (arr[j]<arr[j-1]){  // 升序
                    swap(arr, j, j-1);
                }
            }
        }
    }

    // for test
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 以插入排序为例
    // 方法B：必然对的一个方法
    public static void comparator(int[] arr){
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArr(int maxSize, int maxValue){
        /*
             Math.random() -> [0,1)之间的所有小数，等概率返回一个
             Math.random()*N -> [0,N)之间的所有小数，等概率返回一个
             (int)Math.random()*N -> [0,N-1]的整数，等概率返回一个
         */
        int[] arr = new int[(int)(Math.random()*(maxSize+1))]; // 长度随机
        for (int i=0; i<arr.length; i++){
            arr[i] = (int)((maxValue+1)*Math.random())-
                    (int)(maxValue*Math.random());  // 值随机
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr){
        if (arr==null){
            return null;
        }
        int[] res = new int[arr.length];
        for (int i=0; i<arr.length; i++){
            res[i]=arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEquals(int[] arr1, int[] arr2){
        if ((arr1==null && arr2!=null) || (arr1!=null && arr2==null)){
            return false;
        }
        if (arr2==null){ // 从上面的循环下来的，不是全空，就是全不空，所以只需要判断一个
            return true;
        }

        for (int i=0; i<arr1.length; i++){
            if (arr1[i]!=arr2[i]){
                return false;
            }
        }
        return true;
    }
}
