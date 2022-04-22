package Chapter3_Algorithm.U1_Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

public class th5_UseHeapGetMiddle {
    // 堆的应用题：和贪心无关
    // Q4 从一个数据流里面快速的获得中位数。

    public static class maxHeapComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }

    // 初始heap
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new maxHeapComparator());
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 默认小根堆

    // 每次数据流都传入一个数据
    public double getMiddle(){
        if (!maxHeap.isEmpty()){
            if (minHeap.isEmpty() || maxHeap.size()>minHeap.size()){
                return maxHeap.peek();
            }else {
                if (maxHeap.size()<minHeap.size()){
                    return minHeap.peek();
                }else{
                    return (maxHeap.peek()+minHeap.peek())/2.0;
                }
            }
        }else {
            if (minHeap.isEmpty()){
                return 0.0;
            }else {
                return minHeap.peek();
            }
        }
    }

    // 每次都往数据流添加一个元素
    /*
        如果加入的元素大于大根堆的堆顶，那么放入小根堆，否则放入大根堆
        这样可以保证大根堆的元素都是小于小根堆的元素，并且大根堆的堆顶和小根堆的堆顶大小关系是相邻的
        在放完元素后，判断两个堆的元素大小关系
        当一个堆的元素个数超过另一个的元素的个数的2个单位，那么多的那个堆弹出一个值放入另一个堆里面
     */
    public void process(int num){
        if (maxHeap.isEmpty()){
            maxHeap.add(num);
        }
        if (!maxHeap.isEmpty() && num>this.maxHeap.peek()){
            minHeap.add(num);
        }else {
            maxHeap.add(num);
        }
        int div = minHeap.size()-maxHeap.size();
        // 如果差值的绝对值是2，那么调整一下
        if (div==2){
            maxHeap.add(minHeap.poll());
        }
        if (div==-2){
            minHeap.add(maxHeap.poll());
        }
    }
}
