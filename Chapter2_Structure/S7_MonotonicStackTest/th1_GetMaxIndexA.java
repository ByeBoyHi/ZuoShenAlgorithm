package Chapter2_Structure.S7_MonotonicStackTest;

import java.util.Objects;

public class th1_GetMaxIndexA {
    // Q3：单调栈
    // 附加一个条件，对于一个数组 [5, 3, 2, 1, 6, 7, 8, 4]
    // 1. 假设子数组必须包含5，并且数字5是最小值。
    // 2. 假设子数组必须包含3，并且数字3是最小值。
    // ... 以此类推
    // 对所有数组都求出指标A，那么我们需要的最大指标A就是里面的最大值
    /*
        需求：
            每个假设条件如果扩充到比自己小的值，那么这个值是没必要的
            因为在我们到达这个最小值的时候，它一定会计算出一个更大的范围
            计算的到的值一定比我们之前加上这个值后计算的值。
        1. 参数A：记录目前的指标A的最大值
        2. 参数B：记录当前遍历到此为止的值的最小值
        3. 参数C：记录栈里面的和
        滑动窗口解法：
        为了满足上面的需求，那么滑动窗口必须存储最小值为队列的头部，求最小值
        单调栈解法：
        为了满足需求，我们的扩充是，左边离自己近比自己小的就是我扩充不到的位置
        右边离自己最近比自己小的就是自己扩充给不到的位置，相当于是用单调栈来记录自己需要计算的范围是哪儿到哪儿
     */
    public static int getA(Integer[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;

        // 这个数组存的都是左边比自己小的第一个数字的索引
        // 右边比自己小的第一个数字的索引
        int[][] index = new MonotonicStack<>(arr, -1).getInfo();

        for (int i = 0; i < arr.length; i++) {
            if (i!=0 && Objects.equals(arr[i], arr[i - 1])){  // 去掉重复计算
                continue;
            }

            int start;
            int end;

            if (index[i][0] != -1) {
                start = index[i][0] + 1;
            } else {  // 如果左边没有比自己小的值，那么取头部
                start = 0;
            }
            if (index[i][1] != -1) {
                end = index[i][1] - 1;
            } else {  // 如果右边没有比自己小的值，那么取尾部
                end = arr.length-1;
            }

            int sum = arr[end];
            while (start < end) {
                sum += arr[start];
                start++;
            }

            max = Math.max(max, sum * arr[i]);
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(getA(new Integer[]{
                5,4,3,2,2,3
        }));
    }
}
