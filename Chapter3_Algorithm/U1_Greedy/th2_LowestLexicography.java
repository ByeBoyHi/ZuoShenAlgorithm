package Chapter3_Algorithm.U1_Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class th2_LowestLexicography {
    // 对字符串之间进行拼接，获得一个字符串，使得其有最小字典序和最大字符串长度

    // 贪心策略
    // 如果 a+b<b+a(拼接)，那么返回a在前，b在后
    public static class StrComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1+o2).compareTo(o2+o1);
        }
    }

    public static String lowestString(String[] str){
        if(str==null || str.length==0){
            return "";
        }
        Arrays.sort(str, new StrComparator());
        StringBuilder sb = new StringBuilder();
        for (String s: str){
            sb.append(s);
        }
        return sb.toString();
    }
}
