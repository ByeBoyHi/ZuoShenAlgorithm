package Chapter3_Algorithm.U2_Recursion;

public class th4_CardGameFL {
    /*
        Q4：一系列卡牌，只能拿最左边或者最右边的牌，分先手和后手
        一次先手一次后手。
     */

    public static int win1(int[] arr){
        if (arr==null || arr.length==0){
            return 0;
        }
        // First是玩家A，先手
        // Second是玩家B，后手
        return Math.max(processFirst(arr, 0, arr.length-1),
                processSecond(arr, 0, arr.length-1));
    }
    // 先手函数
    public static int processFirst(int[] arr, int left, int right){
        if (left==right){
            return arr[left];
        }
        // 我拿走left
        int L = arr[left] + processSecond(arr, left+1, right);
        // 我拿走right
        int R = arr[right] + processSecond(arr, left, right-1);
        // 这是我决定的值，我要最大化我的分数
        return Math.max(L, R);
    }
    // 后手函数
    public static int processSecond(int[] arr, int left, int right){
        if (left==right) // 因为我们是后手
            return 0;
        int L = processFirst(arr, left+1, right); // 别人拿走left，我们就在剩下的先手
        int R = processFirst(arr, left, right-1); // 别人拿走right，我们就在剩下的先手
        // 我们后手，这是别人决定的，所以最小值是我们的答案，别人会把最差的给我们
        return Math.min(L, R);
    }


}
