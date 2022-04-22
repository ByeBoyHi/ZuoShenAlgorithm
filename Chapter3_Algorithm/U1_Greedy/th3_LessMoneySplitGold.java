package Chapter3_Algorithm.U1_Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

public class th3_LessMoneySplitGold {
    // Q2
    /*
        按照哈夫曼树的思想，利用小根堆建立哈夫曼树
     */
    public static class MinHeapComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    }

    public static int lessMoney(int[] arr){
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // 把所有数字放入小根堆，每次弹出两个数建立哈夫曼树节点，然后放回去
        for (int num: arr){
            minHeap.add(num);
        }
        int sum = 0;
        // 最后如果只剩下一个数字，说明哈夫曼树建立完毕，只剩下根节点了
        while (minHeap.size()>1){
            int cur = minHeap.poll()+minHeap.poll();
            sum+=cur;
            minHeap.add(sum);
        }
        return sum;
    }
}
