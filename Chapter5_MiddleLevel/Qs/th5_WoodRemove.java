package Chapter5_MiddleLevel.Qs;

public class th5_WoodRemove {
    /*
        有一串长度连续的木头，问，最少去掉多少根木头，可以使得任意三个木头都无法组成三角形
     */
    public static int remove(int n){
        if (n<4) return 0; // 小于4的时候，一根木头都不用取出
        int one = 2;
        int two = 3;
        int ans = 0;
        for (int cur=4; cur<=n; cur++){
            if (cur<one+two){
                ans++;
                System.out.print(cur+" ");
            }else {
                one = two;
                two = cur;
            }
        }
        System.out.println();
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(remove(17));
    }
}
