package Chapter3_Algorithm.U2_Recursion;

import java.util.ArrayList;
import java.util.List;

public class th2_PrintAllSubSequence {
    public static void main(String[] args) {
        char[] str = new char[]{0,0,0,0,0};
        System.out.println(String.valueOf(str));
        printAll("abc");
    }

    /*
        打印一个字符串的全部子序列，每一个字符都可以考虑要或者不要两条路。
     */
    public static void printAll(String str){
        print(str.toCharArray(), 0);
    }

    // 基于第二种的空间优化，时间复杂度是一样的
    public static void print(char[] chs, int i){
        if (i==chs.length){
            printChars(chs);
            return;
        }
        print(chs, i+1); // 要当前字符
        char tmp = chs[i];
        chs[i] = 0; // 不要当前字符
        print(chs, i+1);
        chs[i] = tmp;  // 再把之前的字符补回来
    }
    public static void printChars(char[] chs){
        for (char c: chs){
            if (c!=0){
                System.out.print(c);
            }
        }
        System.out.println();
    }


    // 下面这种更好理解
    public static void process(char[] chs, int i, List<Character> res){
        if (i==chs.length){
            printList(res);
            return;
        }

        // 下面的复制是为了保证操作的空间不一样，如果都用res，好像逻辑正确，但是内容会一直被修改
        // 要当前字符串
        List<Character> resKeep = copyList(res);
        resKeep.add(chs[i]);
        process(chs, i+1, resKeep);
        // 不要当前字符
        List<Character> NoKeep = copyList(res);
        process(chs, i+1, NoKeep);
    }

    public static void printList(List<Character> res){
        for (char c: res){
            System.out.print(c);
        }
    }

    public static List<Character> copyList(List<Character> res){
        return new ArrayList<>(res);
    }
}
