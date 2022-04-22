package Chapter3_Algorithm.U3_KMP;

import java.util.*;

public class th3_WindowsUpdate {
    // Q2
    /*
        1. 输入数组arr
        2. 窗口大小w
        窗口不断往右边滚动，最多有len-w+1个窗口，返回每个窗口的最大值的数组
        10 -- 5  -- 10-5+1=6个窗格
     */
    public int[] windowsUpdate(int[] arr, int w){
        int len = arr.length;
        int[] ans = new int[len-w+1];
        // TreeMap是基于红黑树的，自然有序，并且要求K不重复，可以加比较器
        // 就像一个可以调整的堆
        TreeMap<Integer, Integer> set = new TreeMap<>((x,y)->y-x);
        ans[0] = Integer.MIN_VALUE;
        for (int i=0; i<w; i++){
            set.put(arr[i], set.getOrDefault(arr[i], 0)+1);
            if (arr[i]>ans[0]){
                ans[0] = arr[i];
            }
        }

        for (int i=1; i<len-w+1; i++){
            set.put(arr[i-1],set.get(arr[i-1])-1);
            if (set.get(arr[i-1])==0){
                set.remove(arr[i-1]);
            }
            set.put(arr[w+i-1], set.getOrDefault(arr[w+i-1], 0)+1);
            ans[i] = set.firstKey();
        }

        return ans;
    }

    /*
        对于Q2：
            有两个指针L 和 R，L和R都往右动，并且保证 L<=R。
            每次移动可以让L动，也可以让R动，只需要保证L<=R即可。
            每次形成新的窗口，都要获取这个窗口的最小值或者最大值，以极小的代价。
     */
    // 双端队列：里面放下标，因为放下标可以知道的信息更多
    // 可以知道是哪个位置的值，也可以知道值是多少
    // 双端队列从头到尾，要保证数据是由大到小的顺序
    /*
        在入值的时候，从队尾不断的弹出值，直到符合当前值大于等于进入值，保证依然符合从大到小的规则为之
        在L移动到进入值之前，这些值的弹出，不影响当前窗口的最大值，因为最大值是目前的进入值

        在出值的时候，过期的位置如果是双端队列的头部节点，那么让头部节点弹出
        如果不是，那么不管。

        因为我们要的是最大值，至于其他值就不需要管理。

        双端队列维持的信息是：
            当让R不断往右动的时候，存储的降序的信息
            当让L不断往右动的时候，每移动一次，目前窗口的最大值是什么
     */
    public int[] getMaxWindow(int[] arr, int w){
        if (arr==null || w<1 || w>arr.length){
            return null;
        }

        int[] ans = new int[arr.length-w+1];

        // 双端队列
        LinkedList<Integer> dQ = new LinkedList<>();
        // 先入队w个，窗口大小
        for (int i=0; i<w; i++){
            while (!dQ.isEmpty() && dQ.peekFirst()<arr[i]) dQ.pollLast();
            dQ.add(arr[i]);
        }

        if (!dQ.isEmpty()) ans[0] = dQ.peekFirst();

        for (int i=w; i<arr.length; i++){
            if (!dQ.isEmpty() && arr[i-w]==dQ.peekFirst()){
                dQ.pollFirst();
            }
            while (!dQ.isEmpty() && dQ.peek()<arr[i]) dQ.pollLast();
            dQ.add(arr[i]);
            ans[i-w+1] = dQ.peekFirst();
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new th3_WindowsUpdate().getMaxWindow(
                new int[]{4,3,5,4,3,3,6,7},
                3
        )));
    }
}
