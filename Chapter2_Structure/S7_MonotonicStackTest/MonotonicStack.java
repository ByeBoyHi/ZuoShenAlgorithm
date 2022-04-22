package Chapter2_Structure.S7_MonotonicStackTest;

import java.util.Stack;

public class MonotonicStack<E extends Comparable<E>> {
    Stack<Integer> stack;
    E[] es;
    int asc = 1;


    public MonotonicStack(E[] es, int val) {
        stack = new Stack<>();
        this.es = es;
        this.asc = val;
    }

    public MonotonicStack(E[] es) {
        stack = new Stack<>();
        this.es = es;
    }

    // 获得这个数组所有元素的左右比自己大的第一个元素
    /*
        二维数组，每一行有2个元素
        [0]：表示左边第一个比自己大的值的索引
        [1]：表示右边第一个比自己大的值的索引
        如果有比自己大的值，那么存入索引值，否则存入-1。
     */
    public int[][] getInfo(){
        if (es==null || es.length==0){
            return null;
        }
        stack.add(0);
        int[][] ans = new int[es.length][2];

        for (int i=1; i<es.length; i++){
            while (stack.size()!=0
                    &&
                    (asc>0
                    ?
                    es[stack.peek()].compareTo(es[i])<0  // 最大值
                    :
                    es[stack.peek()].compareTo(es[i])>0)
            ){ // 最小值
                int index = stack.pop();
                if(!stack.isEmpty()) {
                    // 左边比自己大的第一个值就是自己压着的这个值
                    ans[index][0] = stack.peek();
                }else {
                    // 如果没有，则存入-1
                    ans[index][0] = -1;
                }
                // 右边比自己大的值就是当前值
                ans[index][1] = i;
            }
            stack.add(i);
        }
        /*
           清算：如果栈里面还有剩下的元素
           说明这些元素都是都没有右边比自己大的元素，并且左边比自己大的元素是压着的元素
         */
        while (stack.size()>1){
            int index = stack.pop();
            ans[index][0] = stack.peek();
            ans[index][1] = -1;
        }
        // 最后都会剩下至少一个元素，这个元素左右都没有比自己大的值
        int index = stack.pop();
        ans[index][0] = -1;
        ans[index][1] = -1;

        return ans;
    }

    // 重载方法：用于返回确切的值
    public Object[][] getInfoE(){
        int[][] index = getInfo();
        Object[][] ans = new Object[index.length][2];
        for (int i=0; i<index.length; i++){
            ans[i][0] = es[index[i][0]];
            ans[i][1] = es[index[i][1]];
        }
        return ans;
    }
}
