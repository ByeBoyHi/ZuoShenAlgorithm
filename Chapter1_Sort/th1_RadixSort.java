package Chapter1_Sort;

public class th1_RadixSort {
    // 返回一个数组中的所有数字的最大位数
    public static int maxbits(int[] arr){
        int max = Integer.MIN_VALUE;
        for (int num : arr){
            max = Math.max(max, num);
        }
        int res = 0;
        while (max!=0){
            res++;
            max = max/10;
        }
        return res;
    }

    /**
     * 对一个数组的[L, R]范围进行基数排序
     * @param arr       待排序的数组
     * @param L         需要排序的第一个元素位置
     * @param R         需要排序的最后一个元素位置
     * @param digits    数组里面数字的最大位数
     */
    public static void radixSort(int[] arr, int L, int R, int digits){
        final int radix = 10; // 十个基数
        int i=0, j=0;
        // 有多少个数就准备多少个辅助空间
        int[] bucket = new int[R-L+1];
        for (int d=1; d<=digits; d++){  // 有多少位，就进出多少次
            // 10个空间
            // count[0]表示(d)位是0
            // count[1]表示(d)位是1
            // ...
            int[] count = new int[10];  // 0..9  词频表
            for (i=L; i<=R; i++){  // 对每一位出现的当前d位的数字进行记录，记录词频
                j = getDigits(arr[i], d);
                count[j]++;
            }
            for (i=1; i<radix; i++){  // 总和每一位及之前的词频和：d位数字小于等于i的有多少个
                count[i] = count[i]+count[i-1];
            }
            // 从右往左走，相当于最右侧的数字是最后进桶的，
            // 那么在倒出来的时候，也就是从右往左出桶，那么他就是第一个判断并入位置的数字

            // 记录了d位数字小于等于i的有多少个，在放入元素的时候，就会从索引为i的地方开始放，
            // 前面会留i-1个空间给小于等于的数字

            // 当前位置的count--后，后面的不需要跟着减，因为前面的数字是否放入，都不影响当前的元素应该放入的位置，
            // 依然需要把前面的位置留出，如果跟着减1，那么就会少一个位置
            for (i=R; i>=L; i--){
                j = getDigits(arr[i], d);
                bucket[count[j]-1] = arr[i];  // 一次入桶，就让d位的数字有序
                count[j]--;  // 已经放入了一个小于等于i的数字
            }
            for (i=L, j=0; i<=R; i++, j++){  // 把第一次入桶排好序的数字顺序放回原数组里面
                arr[i] = bucket[j];
            }
        }
    }

    /**
     * 获取指定数字的第d位的数字
     * @param number  指定的数字
     * @param d       指定的第几位
     * @return        第d位的值
     */
    public static int getDigits(int number, int d){
        return (int) (number%Math.pow(10,d));
    }

}
