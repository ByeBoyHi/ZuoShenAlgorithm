package Chapter3_Algorithm.U2_Recursion;

import java.util.Stack;

public class th5_ReverseStack {
    /*
        对一个栈逆序输出，不能使用额外的数据结构，只能递归
     */
    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()){
            return;
        }
        /*
            不断的递归获得最后一个元素，如果最开始元素顺序是 1 2 3
            那么获得的顺序就是 3 2 1
            然后从递归的底层不断的压栈，压栈顺序是 1 2 3，那么栈里面元素的顺序是 3 2 1。
         */
        int last = f(stack);
        reverse(stack);
        stack.push(last);
    }
    public static int f(Stack<Integer> stack){
        int result = stack.pop();
        if (stack.isEmpty()){
            return result;
        }else{
            // 去不断的递归，获得栈底元素
            // 获得栈底元素之后，把之前拿出来的result按照原来的顺序压回去，最终会得到一个栈底的元素值
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }
}
