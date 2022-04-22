package Chapter5_MiddleLevel.Qs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class th1_DiffKOfNumPairs {
    //给一个数组arr，求差值为k的去重数字对。
    public static List<List<Integer>> getGroups(int[] arr, int k){
        List<List<Integer>> ans = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int n: arr){
            set.add(n);
        }
        for (int n: arr){
            if (set.contains(n+k)){
                // 这个添加是进行去重，如果两个元素一模一样的时候，会因为equals相同而不添加进去，返回false
                ans.add(Arrays.asList(n, k + n));
            }
        }
        return ans;
    }
}
