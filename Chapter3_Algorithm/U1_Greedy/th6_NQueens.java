package Chapter3_Algorithm.U1_Greedy;

public class th6_NQueens {

public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    System.out.println("直接法结果："+num1(4));
    long end1 = System.currentTimeMillis();
    System.out.println("消耗时间："+(end1-start1)+"ms");
    System.out.println("===========================");
    start1 = System.currentTimeMillis();
    System.out.println("位运算加速结果："+num2(4));
    end1 = System.currentTimeMillis();
    System.out.println("消耗时间："+(end1-start1)+"ms");
}

    // n皇后，生成n*n的矩阵
    public static int num1(int n){
        if (n<1){
            return 0;
        }
        // 因为一行必然只有一个皇后，所以我们只需要记录第i行的皇后在哪一列
        int[] record = new int[n]; // record[i]-->第i行的皇后，放在了第几列
        return process1(0, record, n);
    }

    /**
     * 计算n*n矩阵里面，有多少种合理的摆法可以摆放n皇后
     * record[0...i-1]的皇后一定不共行、不共列、不共斜线
     * @param i        目前来到了第i行
     * @param record   之前摆放的皇后都在record里面
     * @param n        总共需要摆放多少个皇后
     * @return         返回所有合理摆法的总个数
     *
     * 时间复杂度 n!
     */
    public static int process1(int i, int[] record, int n){
        if (i==n){  // 因为是从0到n-1的，所以到n的时候，所有皇后都放在合理位置了，一种可能性出现了，返回1
            return 1;
        }
        int res = 0;
        for (int j=0; j<n; j++){
            /*
                当前i行的皇后，放在j列，会不会和之前的(0,...,i-1)的皇后共行共列共斜线
                如果是，认为无效
                如果不是，则有效
             */
            if (isValid(record, i, j)){  // 判断(i, j)这个位置是否可以放皇后
                record[i]=j;
                res+=process1(i+1,record, n);
            }
        }
        return res;
    }

    /**
     * 判断皇后放在第(i,j)位置是否合法
     * @param record   记录的之前已经放入的皇后的位置
     * @param i        当前皇后的横坐标
     * @param j        当前皇后的纵坐标
     * @return         返回是否有效
     */
    public static boolean isValid(int[] record, int i, int j){
        for (int k=0; k<i; k++){
            // 不会共行，因为都是一行一行下去的

            // 如果共列，则这个位置不能放
            if (j==record[k]){
                return false;
            }
            // 共斜线：当前节点record[k]下移i行，左移i列之后，和(i,j)重合
            // 列坐标之差的绝对值等于行坐标之差的绝对值，说明这条线是45°或者135°
            // 一定共斜线！！
            if (Math.abs(record[k]-j)==Math.abs(k-i)){
                return false;
            }
        }
        return true;
    }


    // n皇后的时间级别无法降低，但是可以对常数项进行优化：用位运算进行加速
    // 请不要超过 32 皇后，因为这里位运算加速最多只有32，如果超过了，可以化成long
    public static int num2(int n){
        if (n<1 || n>32){
            return 0;
        }
        int limit = n==32?-1:(1<<n)-1;
        return process2(limit, 0, 0, 0);
    }

    /**
     *
     * @param limit         生成一个数字，后n位是1，前面都是0。如果是32，直接取-1，也就是全是1
     *                      limit是永远不变的，就是告诉我们，哪些位置是可以放皇后的
     *                      因为不同的皇后个数，1的范围是不一样的，我们不能放出界外了
     *                      （1的位置可以放皇后，也范围内）
     *
     * @param colLim        列的限制，标记为1的位置不能放皇后，标记为0的位置可以
     *
     * @param leftDiaLim    左斜线限制，标记为1的位置不能放皇后，标记为0的位置可以
     *
     * @param rightDiaLim   右斜线限制，标记为1的位置不能放皇后，标记为0的位置可以
     *
     * @return              process1的常数项优化
     *
     * 当前列放皇后的限制这里使用的是位信息。第i列是不是1，也就是能不能填皇后。
     *
     * 假如八皇后问题，在某个位置上我们填入一个皇后后： 0 0 0 1 0 0 0 0
     * 那么 colLim、leftDiaLim、rightDiaLim的信息分别为：
     * 0 0 0 1 0 0 0 、 0 0 1 0 0 0 0 、 0 0 0 0 1 0 0 0
     * 对者三个值取或运算： 0 0 1 1 1 0 0，那么我们就知道了下一行哪些列不能放皇后，因为标记1的位置对于下一行来说不是共列就是共斜线
     * 然后取反，就变成了： 1 1 0 0 0 1 1，就变成了我们上面制定的规则，标记为1的可以放皇后，0不可以。
     *
     * 左斜线位置就是向左移动一位，右斜线位置就是向右移动一位
     */
    public static int process2(int limit,
                               int colLim,
                               int leftDiaLim,
                               int rightDiaLim){
        if (limit==colLim){  // 列限制等于limit了，说明所有皇后所有列放完了，一种合理摆法出现了
            return 1;
        }

        /*
            colLim | leftDiaLim | rightDiaLim ：总限制，这时候0可以放皇后，1不可以放皇后
            ~：取反之后，放皇后条件调反，即1可以放皇后，0不可以
            limit &：相当于把返回控制在了limit范围，范围外的1全部去掉，
            保留了limit范围内的1(把左侧多余的1截掉)。
            所有候选皇后的位置
         */
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));

        // 保存候选皇后位置中最右侧的1的位置
        // 即从最右侧的1开始尝试放皇后，挨个往左移动
        int mostRightOne;

        int res = 0;
        while (pos!=0) {
            // 获得有效位置的最右侧的1
            mostRightOne = pos & (~pos + 1);

            // 把用过的皇后位置去掉，也就是把1去掉
            pos = pos - mostRightOne;
            /*
                colLim | mostRightOne：因为新放了一个皇后进去，
                                      那么列限制对应位置需要加上一个1，也就是或运算
                (leftDiaLim | mostRightOne) << 1：就是对下一行的左侧斜线的限制
                            比如当前行是 0 0 0 1 0 0 0 0
                            那么对于下一行的斜线限制就是 0 0 1 0 0 0 0 0
                            再下一行的限制就是 0 1 0 0 0 0 0 0
                            依此类推，因为走的是斜线，且在不断下降
                (rightDiaLim | mostRightOne) >>> 1：就是对下一行的右侧斜线的限制
             */
            res += process2(limit,
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        /*
            这里的位运算加速优化是对判断是否有效进行了加速判断
            但是对于整个n皇后来讲，每一个皇后都会进行每一列的判断
            所以大局上的时间复杂度依然不变：n!。
         */
        return res;
    }
}
