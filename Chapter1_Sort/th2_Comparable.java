package Chapter1_Sort;

import java.util.Comparator;

public class th2_Comparable implements Comparator<Integer> {

    // 比较器
    // 如果小于，在前面；如果大于在后面；否则前后无所谓。
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1>o2){
            return 1;
        }
        if (o1<o2){
            return -1;
        }
        return 0;
        // 等价：return o1-o2;
        // 等价：return o1.compareTo(o2);
    }
}
