package Chapter3_Algorithm.U3_KMP;

public class th2_Manacher {
    public char[] manacherString(String str) {
        char[] chs = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < chs.length; i++) {
            chs[i] = (i & 1) == 1 ? '#' : str.charAt(index++);
        }
        return chs;
    }

    public int manacher(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chs = manacherString(str);
        int[] pArr = new int[chs.length];
        int R = -1; // 这个R会放在最右边界的下一个位置
        int C = -1;
        int max = 1;

        for (int i=0; i<chs.length; i++){
            /*
                如果i在R外面，那么直接暴力扩，目前最小距离就是1
                如果i在R里面，
                    如果i'的半径在R的外面，那么取R-i
                    如果i'的半径在R里面，那么就是 pArr[i]
                    如果i'的半径压线了，那么就是R-i
                这三种情况综合起来，取里面的最小值，这个最小值就是我们至少不用验证的半径范围
             */
            pArr[i] = R>i?Math.min(pArr[2*C-i], R-i):1;

            // 我们得到了至少不用验证的区域，那么我们可以从至少不用验证的区域往外部扩
            // 就可以把这几种都合在一起了
            // i+pArr[i]表示这个位置的索引值，可以取chs对应位置的值
            // R会走到len
            while (i+pArr[i]<str.length()&& i-pArr[i]>-1){
                if (chs[i+pArr[i]]==chs[i-pArr[i]]){
                    // 这里每一次比较了两个值，但是我们值加了1
                    // 相当于我们虽然用#对字符串做了处理
                    // 但是我们每次都少加1，就相当于只加了一半，比如：
                    // #1#2#1#2#1#
                    // 对于最中间的1，两边相等，我们+1
                    // 当走到最后的时候，我们加了5，但是当pArr=0的时候，我们也加了1，就是把当前位置加了
                    // 根据对称性，那么我们加的信息就是一半的信息多1。
                    // 如果中间是#，那么我们也多了1，就是多了那个#。
                    pArr[i]++;
                }else {
                    break;
                }
            }

            // 扩充完了后，判断R和C是否需要更新
            if (i+pArr[i]>R){
                R = i+pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }

        // 根据信息多加了1，所以要-1
        return max-1;
    }
}
