package Chapter3_Algorithm.U7_DualPoints;

import java.util.ArrayList;
import java.util.List;

/**
 * 同向双指针：去除有序数组中的重复元素 <br>
 * Time: 2022/6/2 <br>
 * User: HeyBoy <br>
 */
public class th1_SameDirection {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(5);
        print(deduplication(list));
    }

    public static void print(int[] arr) {
        for (int a : arr) {
            if (a == 0) {
                break;
            } else {
                System.out.print(a + " ");
            }
        }
    }

    public static int[] deduplication(List<Integer> list) {
        int left = 0;
        int right = 0;
        int[] arr = new int[list.size()];
        arr[left] = list.get(left);
        int index = 1;
        while (right < list.size()) {
            if (!list.get(right).equals(list.get(left))) {
                left = right;
                arr[index++] = list.get(right);
            }
            right++;
        }
        return arr;
    }

}
