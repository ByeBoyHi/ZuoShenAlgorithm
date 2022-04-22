package Chapter3_Algorithm.U2_Recursion;

import java.util.ArrayList;

public class th3_AllArranged {

    public static void process(char[] str, int i, ArrayList<String> res) {
        // 也可以在这里进行去重处理，或者对字符数组进行排序，相同的字符会挨在一起
        // 相同的字符的尝试肯定是想相同的，可以剪去不必要的路
        // 这两种优化对于指标都没有影响，只是对常数的优化上，后者会更好
        if (i == str.length) {
            res.add(String.valueOf(str));
            return;
        }
        boolean[] visited = new boolean[26]; // 记录26个字母是否尝试过
        for (int j = i; j < str.length; j++) {
            if (!visited[str[j] - 'a']) {  // 剪枝
                visited[str[j] - 'a'] = true;
                swap(str, i, j);  // 后面的任意一个字符都可以放到i的位置，然后递归尝试
                process(str, i + 1, res);
                swap(str, i, j);  // 尝试完了后，交换回来，去尝试下一个字母的可能性
            }
        }
    }

    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }
}
