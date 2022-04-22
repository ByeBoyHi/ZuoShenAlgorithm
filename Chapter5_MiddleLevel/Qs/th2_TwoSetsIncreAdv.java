package Chapter5_MiddleLevel.Qs;

import java.util.Arrays;
import java.util.HashSet;

public class th2_TwoSetsIncreAdv {

    public static int maxOps(int[] arr1, int[] arr2) {
        double sum1 = 0;
        for (int j : arr1) {
            sum1 += j;
        }
        double sum2 = 0;
        for (int j : arr2) {
            sum2 += j;
        }
        if (avg(sum1, arr1.length) == avg(sum2, arr2.length)) {
            return 0;  // 两个集合的平均值一样的话，无法magic
        }

        // 平均值不等的时候
        int[] arrMore = null;
        int[] arrLess = null;
        double sumMore = 0;
        double sumLess = 0;
        if (avg(sum1, arr1.length) > avg(sum2, arr2.length)) {
            arrMore = arr1;
            arrLess = arr2;
            sumMore = sum1;
            sumLess = sum2;
        } else {
            arrMore = arr2;
            arrLess = arr1;
            sumMore = sum2;
            sumLess = sum1;
        }
        // 因为要从较大的集合里面取合理的数字放进较小的集合里面
        // 所以要看取的数字是否在小集合里面存在，如果存在就不取，因为取了也没用，两个集合不能存放重复的数字
        // 对大集合排序是为了从小往大选数字放，可以让影响最小化，magic次数最大化
        Arrays.sort(arrMore);
        HashSet<Integer> setLess = new HashSet<>();
        for (int num : arrLess) {
            setLess.add(num);
        }
        int moreSize = arrMore.length;  // 大集合剩余的个数
        int lessSize = arrLess.length;  // 小集合剩余的个数
        int ops = 0; // 操作的次数
        for (int cur : arrMore) {  // 在大集合里面从 小—>大 依次选
            if (       cur < avg(sumMore, moreSize)
                    && cur > avg(sumLess, lessSize)
                    && !setLess.contains(cur)
            ) {
                // 大于小集合平均值，小于大集合平均值，且在小集合中不存在，则为合理数字
                // 进行数据更新：大 -> 小
                sumMore -= cur;
                sumLess += cur;
                moreSize--;
                lessSize++;
                setLess.add(cur);
                ops++;
            }
        }
        return ops;
    }

    public static double avg(double sum, int n) {
        return sum / n;
    }
}
