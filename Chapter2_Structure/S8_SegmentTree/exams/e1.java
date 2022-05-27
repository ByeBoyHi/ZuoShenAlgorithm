package Chapter2_Structure.S8_SegmentTree.exams;

/**
 * 1. 将某区间加上某一个数k <br>
 * 2. 求出某区间的和 <br>
 * <br>
 * 本次总结： <br>
 * 1. 建立线段树的起始点从0开始，这样才能通过2p和2p+1往下走，如果是0，左子树最开始则一直再原地 <br>
 * 2. 进行区间累加的时候，不要忘记乘以区间的长度 <br>
 * 3. 在进行区间更新的时候，最开始进去的时候就进行累加更新，所以这里只需要做个惰性标记，下次再来的时候，只需要把标记传下去，这一层不需要做操作<br>
 * <br>
 * Time: 2022/5/27 <br>
 * User: HeyBoy
 */
public class e1 {
    public static void main(String[] args) {
        int[] arr = new int[]{10, 11, 12, 13, 14};
        SegmentTree tree = new SegmentTree(arr);
        tree.build();
//        for (int i = 0; i < tree.segment.length; i++) {
//            System.out.print(tree.segment[i] + " ");
//        }
        tree.addVal(3,4,5);
        System.out.println(tree.getSum(0,4));
    }
}

class SegmentTree {
    public final int[] segment;
    private final int[] add;
    private final int[] arr;

    public SegmentTree(int[] arr) {
        int len = arr.length;
        this.arr = arr;
        segment = new int[len * 4];
        add = new int[len * 4];
    }

    public void build() {
        build(0, this.arr.length - 1, 1);
    }

    // 建立线段树
    private void build(int left, int right, int p) {
        if (left == right) {
            segment[p] = arr[left];
            return;
        }
        int mid = left + ((right - left) >> 1);
        build(left, mid, 2 * p);
        build(mid + 1, right, 2 * p + 1);
        segment[p] = segment[2 * p] + segment[2 * p + 1];
    }

    public void addVal(int left, int right, int val){
        addVal(left, right, 0, arr.length-1, 1, val);
    }

    /**
     * 给某个区间加上一个数字
     *
     * @param left   指定区间的左边界
     * @param right  指定区间的右边界
     * @param start  当前区间的左边界
     * @param end    当前区间的右边界
     * @param pIndex 当前节点位置
     * @param val    添加值
     */
    private void addVal(int left, int right, int start, int end, int pIndex, int val) {
        // 到达子区间
        if (left <= start && right >= end) {
            segment[pIndex] += val*(end-start+1);  // 这里把本次更新加上了
            add[pIndex] += val; // 把本次修改加上去，给下一次的传下去
            return;
        }
        int mid = start + ((end - start) >> 1);
        if (add[pIndex] != 0) {  // 把之前的传下去
            f(pIndex, start, mid, end);
        }
        if (left <= mid) addVal(left, right, start, mid, 2 * pIndex, val);
        if (mid < right) addVal(left, right, mid + 1, end, 2 * pIndex + 1, val);
        segment[pIndex] = segment[2 * pIndex] + segment[2 * pIndex + 1];
    }

    /**
     * 获取区间和
     *
     * @param left  指定区间的左边界
     * @param right 指定区间的右边界
     * @return 区间和
     */
    public int getSum(int left, int right) {
        return getSum(left, right, 0, this.arr.length - 1, 1);
    }

    private int getSum(int left, int right, int start, int end, int pIndex) {
        if (left <= start && right >= end) {
            return segment[pIndex];
        }
        int mid = start + ((end - start) >> 1);
        if (add[pIndex] != 0) {  // 清除更新
            f(pIndex, start, mid, end);
        }
        int sum = 0;
        if (left <= mid) sum = getSum(left, right, start, mid, 2 * pIndex);
        if (mid < right) sum += getSum(left, right, mid + 1, end, 2 * pIndex + 1);
        return sum;
    }

    private void f(int pIndex, int start, int mid, int end) {
        segment[2 * pIndex] += add[pIndex]*(mid-start+1);
        segment[2 * pIndex + 1] += add[pIndex]*(end-mid);
        add[2 * pIndex] = add[2 * pIndex + 1] = add[pIndex];
        add[pIndex] = 0;
    }
}
