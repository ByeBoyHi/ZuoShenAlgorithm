package Chapter3_Algorithm.U2_Recursion;

/*
    暴力递归就是不断的尝试。
    自己定一个大的标准，不断的去尝试求解，不行就换一个。
 */
public class th1_Hanoi {
    /**
     * 打印从from挪到to的所有步骤
     * @param from   出发地柱子
     * @param to     目的地柱子
     * @param other  另一个柱子
     * @param n      最开始有n个圆盘
     */
    public static void hanoi(char from, char to, char other, int n){
        if (n<1){
            return;
        }
        if (n==1){ // 只有一个的时候，直接到to，然后返回
            System.out.println(from+"-->"+to);
            return;
        }
        hanoi(from, other, to, n-1); // 把上面的盘子都挪走
        System.out.println(from+"-->"+to); // 上面都走了，把这一个挪到to
        hanoi(other, to, from, n-1); // 再把之前的盘子挪到to
    }

    public static void main(String[] args) {
        hanoi('A', 'C', 'B', 5);
    }
}
