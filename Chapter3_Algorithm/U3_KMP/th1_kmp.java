package Chapter3_Algorithm.U3_KMP;

public class th1_kmp {
    /*
        最长前缀和最长后缀：
        当前位置的前面。
        前缀和后缀，什么时候是相等的，也就是前缀和后缀的最大长度，那么当前位置就填几，见图kmp1。
        如果前面有多个相等，取最大的位当前位置的值，见图kmp2。
        这个值是根据str2求的，就是短的那个字符串。
        会根据这个规则生成str2的nextArr数组，这个数组记录的值就像是str2的平移一样。
        会把一样的毫发无损的平移过来，避开了判断，然后从一个不确定的位置开始判断，生成数组见图nextArr。
        平移的过程见图nextArrMove。
        ------------------
        对于这个nextArr数组还可以进行优化：
        比如当前字符不合格，但是跳跃过去的字符和当前字符不一样，那么我们也没必要判断，因为肯定不合格。
        这时候我们再接着进行跳跃即可，根据这个规则，就可以对nextArr再次进行优化。
        并且我们的优化是从前往后的，所以前面如果跳跃出现多个字母一样的，其实不需要用while不断往前。
        因为最近的相同字符取的值就是前面最早的值，只需要一次就可以优化完成。
     */
    public static void main(String[] args) {

    }

    public static int KMP(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }
        char[] sc = s.toCharArray();
        char[] mc = m.toCharArray();
        int[] nextArr = getNextArr(mc);
        int i1 = 0, i2 = 0;
        while (i1 < s.length() && i2 < s.length()) {
            if (sc[i1] == mc[i2]) {  // 当前字符相等，往后移动
                i1++;
                i2++;
                // 当前不相等，并且i2走到了第一个位置，那么i1往后挪一步，比较下一个
            } else if (nextArr[i2] == -1) {
                i1++;
            } else { // 当前不相等，跳到最长相等前缀后缀的下一个位置
                i2 = nextArr[i2];
            }
        }
        // 假如匹配成功，举个栗子
        /*
            aaaabcd'a'aa
               abcd
            在i1=7的时候，匹配完成，也就是那个分割出来的a，此时i2=4
            那么这个匹配成功的字符串，就是当前位置的索引减去m的长度，也就是减去i2
         */
        return i2 == m.length() ? i1 - i2 : -1;
    }

    public static int[] getNextArr(char[] str) {
        if (str.length==1){
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        // cn记录前缀， i记录后缀
        // cn表示我们要拿哪一个字符和i-1比较
        int i = 2;
        int cn = 0;
        while (i<str.length){
            if (str[i-1]==str[cn]){
                next[i++] = ++cn;
            }else if (cn>0){
                // 当前位置不匹配的时候，利用前面已经得到的结论求出来
                // cn就是getNextArrImg2的那个前面字符串的最后一个e位置，出现不匹配
                // 然后去前面找最长匹配串的位置，因为这个和后面的前面相等
                // 所以这时候就拿新的最长匹配串的下一个和当前位置比较
                // 如此往复
                cn = next[cn];
            }else {
                // 匹配的同时走到了第一个位置，得到了-1，那么我们需要让当前位置置为0
                // cn往前走一直没有匹配上
                next[i++] = 0;
            }
        }
        return next;
    }

    // nextArr优化
    public static int[] nextArrOptimizer(char[] ms, int[] next){
        int i=2;
        for (; i<next.length; i++){
            if (ms[i]==ms[next[i]]){
                next[i] = next[next[i]];
            }
        }
        return next;
    }
}
