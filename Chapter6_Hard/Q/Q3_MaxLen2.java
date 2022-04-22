package Chapter6_Hard.Q;

public class Q3_MaxLen2 {
    /*
        求一个数组的连续最大长度，要求长度范围内的和小于等于指定的key。
        注意：这个数组里面有正有负还有零
        番外：求一个数组的连续最大长度，要求长度范围内的和大于等于指定的key。
     */

    /*
        思路：
            我们需要求的是最大长度，且这个长度内的总和小于等于给定值即可。
            1. 定义两个数组 minSum, minSumEnd
                1. minSum: 以 i 为起点的最小和是多少
                2. minSumEnd: 以 i 为起点的最小和的终点在哪里
            2. 得到上面两个数组，整个数组被分成了很多个块。从0开始遍历。
            3. 当前块小于等于key，更新len。
            4. 加上下一个块，如果小于等于key，更新len，否则 index++。
            5. 重复 4。
            6. 整个过程保护块的长度 [index, len+index]，重复过程的时候，是直接从下一块判断的。
            7. 整个块走完都不合标准，那么回到 3，重复 4。
     */

    public static void main(String[] args) {
        int[] arr = new int[]{100,-10,-20,-30,80,-70,200,300,400,10,10,1,1,1,10};
        System.out.println(process(arr, 40));

        int[] arr2 = new int[]{-10, 0, -20, -50, 40, -60, -3, -8, 30, -7};
        System.out.println(process2(arr2, 20));
    }

    public static int process(int[] arr, int key){
        int n = arr.length;

        int[] minSum = new int[n];
        int[] minSumEnd = new int[n];
        // 最后一个元素，他自己就是最小连续和
        // 他自己就是终点
        minSum[n-1] = arr[n-1];
        minSumEnd[n-1] = n-1;
        for (int i = n-2; i >= 0; i--) {
            if (minSum[i+1]<=0){  // 可取
                minSum[i] = arr[i]+minSum[i+1];
                minSumEnd[i] = minSumEnd[i+1];
            }else{  // 不可取
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }

        // 第一块的起点和终点
        int start = 0;
        int end = minSumEnd[0];
        int sum = minSum[0];  // 第一块的最小连续和
        int len = 0;
        if (sum<=key) len = end-start+1;
        while (start<n && end+1<n){ // 因为要通过end去加下一块，所以下一块有，我们才循环
            // 不断加下一块，直到不能加为止
            while (end+1<n && sum+minSum[end+1]<=key){
                sum += minSum[end+1];
                end = minSumEnd[end+1];
                len = Math.max(len, end-start+1);
            }
            // 遇到不能加的位置，其实位置往前挪，终点位置不变
            // 并且总和要减去起始位置
            sum-=arr[start++];
            if (start==end){
                // 当前块不合格
                while (start<n && minSum[start]>key){
                    start++;
                }
                end = minSumEnd[start];
            }
        }
        return len;
    }

    // 番外: 大于等于指定的key
    public static int process2(int[] arr, int key){
        int n = arr.length;

        return 1;
    }
}
