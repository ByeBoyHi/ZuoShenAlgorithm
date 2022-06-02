package Chapter3_Algorithm.U7_DualPoints;

import java.util.HashMap;
import java.util.Map;

/**
 * 滑动窗口：定长滑动窗口和不定长滑动窗口 <br>
 * Time: 2022/6/2 <br>
 * User: HeyBoy <br>
 */
public class th3_SlideWindow {

    public static void main(String[] args) {
        int[] arr = {5, 3, 12, 4, 6, -8, 8, -6, -11, 8, 9, 10};
        System.out.println(f1(arr, 3));
        String s = "1234561234";
        System.out.println(f2(s));
        Info info = f3(arr, 16);
        System.out.println(
                "less than: " + info.lessThan + "\n" +
                "greater than: " + info.greatThan + "\n" +
                "equal: " + info.equal
        );
    }

    // 定长滑动窗口：解决长度为n的数组中，长度为k的最大子数组之和
    public static int f1(int[] arr, int k) {
        int max = 0;
        // max初始化的值为前k个元素和
        for (int i = 0; i < k; i++) {
            max += arr[i];
        }
        int window = max;
        for (int i = k; i < arr.length; i++) {
            window = window - arr[i - k] + arr[i];
            max = Math.max(max, window);
        }
        return max;
    }

    // 不定长滑动窗口：解决字符串中无重复字符子串的最大长度
    public static int f2(String s) {
        if (s.length() == 0) return 0;
        if (s.length() == 1) return 1;

        Map<Character, Integer> map = new HashMap<>();
        int res = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            if (!map.containsKey(c)) {
                map.put(c, right);
                right++;
                res = Math.max(res, right - left);
            } else {
                map.put(c, right);
                right++;
                while (s.charAt(left) != c) {
                    map.remove(s.charAt(left++));
                }
                left++;
            }
        }
        return res;
    }

    /**
     * 不定长滑动窗口：给出一个非负整数数组，解决如下问题 <br>
     * 1. 判断其中是否存在和为k的子数组，若存在则返回true，否则返回false； <br>
     * 2. 返回其中和 <= k的子数组的个数； <br>
     * 3. 返回其中和 >= k的子数组的个数。 <br>
     */
    public static Info f3(int[] arr, int k) {
        int left = 0;
        int sum = 0;
        Info info = new Info();
        for (int i : arr) {
            sum += i;
            while (left < arr.length && sum > k) {
                sum -= arr[left];
                left++;
            }
            if (sum == k) {
                info.equal++;
                System.out.println("* "+i+" *");
            } else {
                info.lessThan++;
            }
        }

        sum = 0;
        left = 0;
        for (int i : arr) {
            sum += i;
            while (left <= arr.length && sum > k) {
                info.greatThan++;
                sum -= arr[left];
                left++;
            }
        }

        return info;
    }

    private static class Info {
        private int lessThan;  // 小于k的子数组个数
        private int greatThan;  // 大于k的子数组个数
        private int equal;  // 等于k的子数组个数

        public int getLessThan() {
            return lessThan;
        }

        public void setLessThan(int lessThan) {
            this.lessThan = lessThan;
        }

        public int getGreatThan() {
            return greatThan;
        }

        public void setGreatThan(int greatThan) {
            this.greatThan = greatThan;
        }

        public int getEqual() {
            return equal;
        }

        public void setEqual(int equal) {
            this.equal = equal;
        }
    }
}
