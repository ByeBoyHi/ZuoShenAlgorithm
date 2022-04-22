package Chapter2_Structure;

import java.util.LinkedList;
import java.util.Objects;

public class S6_WindowsUpdate {
    // 滑动窗口结构
    /*
        需求：在左边出一个值，右边入一个值的时候，
            我们要能以O(1)的时间复杂度获得当前L到R之间窗口的最大值或者最小值
        1. 有两个指针L和R，用来控制两边的入值和出值，保证窗口大小是随意动态的
        2. 有一个双端队列来存储至今未知遇到的值，保证顺序从大到小（假设我们需要max）
        3. 双端队列的头表示目前这个窗口的最大值
        4. 始终保持右指针大于等于左指针
     */
    static class WindowUpdate<E extends Comparable<E>> {
        int L;
        int R;
        int size;
        LinkedList<E> dQ;
        LinkedList<E> arr;

        public WindowUpdate() {
            L = -1;
            R = -1;
            size = 0;
            dQ = new LinkedList<>();
            arr = new LinkedList<>();
        }

        // 左边出队
        public boolean remove() {
            if (!isEmpty()) {
                if (Objects.equals(arr.peekLast(), dQ.peekFirst())) {
                    arr.pollLast();
                    dQ.pollFirst();
                }
                return true;
            }
            return false;
        }

        // 右边入队
        public void add(E key) {
            arr.addFirst(key);
            while (!dQ.isEmpty() && dQ.peekLast().compareTo(key) < 0) dQ.pollLast();
            dQ.addLast(key);
        }

        // 是否为空
        public boolean isEmpty() {
            return this.size == 0;
        }

    }
}
