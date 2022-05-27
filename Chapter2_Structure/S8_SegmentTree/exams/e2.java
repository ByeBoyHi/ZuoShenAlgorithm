package Chapter2_Structure.S8_SegmentTree.exams;

/**
 * 1. 对某个区间乘以某个数k <br>
 * 2. 对某个区间加上某个数k <br>
 * 3. 返回区间和 <br>
 * <br>
 * Time: 2022/5/27 <br>
 * User: HeyBoy <br>
 */
public class e2 {
    public static void main(String[] args) {
        int[] arr = new int[]{10,11,12,13,14};
        STree tree = new STree(arr);
        tree.build();
        System.out.println(tree.getSum(0,2));  // 33
        tree.addVal(0,2,5);
        System.out.println(tree.getSum(0,2));  // 48
        tree.multiVal(0,2,2);
        System.out.println(tree.getSum(0,2));  // 96
        tree.multiVal(0,2,3);
        System.out.println(tree.getSum(0,2));  // 288
    }
}

class STree{
    // 线段树
    private final int[] tree;
    // 记录加法
    private final int[] add;
    // 记录乘法
    private final int[] multi;
    // 记录传入数组
    private final int[] arr;
    // 取一个值用来记录最近的操作
    boolean flag = false;

    public STree(int[] arr){
        int len = arr.length;
        this.arr = arr;
        tree = new int[len*4];
        add = new int[len*4];
        multi = new int[len*4];
    }

    // 建立线段树
    public void build(){
        build(0, arr.length-1, 1);
    }

    private void build(int left, int right, int p){
        if (left==right){
            tree[p] = arr[left];
            return;
        }
        int mid = left + ((right-left)>>1);
        build(left, mid, 2*p);
        build(mid+1, right, 2*p+1);
        tree[p] = tree[2*p] + tree[2*p+1];
    }

    // 加法
    public void addVal(int left, int right, int val){
        flag = true;
        addVal(left, right, 0, arr.length-1, 1, val);
    }

    private void addVal(int left, int right, int start, int end, int p, int val){
        if (left<=start && right>=end){
            tree[p]+=val*(end-start+1);
            add[p]+=val;
            return;
        }
        int mid = start + ((end-start)>>1);
        if (add[p]!=0){  // 把惰性标记往下传
            tree[2*p] += add[p]*(mid-start+1);
            tree[2*p+1] += add[p]*(end-mid);
            add[2*p] = add[2*p+1] = add[p];
            add[p] = 0;
        }
        if (left<=mid) addVal(left, right, start, mid, 2*p, val);
        if (mid<right) addVal(left, right, mid+1, end, 2*p+1, val);
        tree[p] = tree[2*p] + tree[2*p+1];
    }

    // 乘法
    public void multiVal(int left, int right, int val){
        flag = false;
        multiVal(left, right, 0, arr.length-1, 1, val);
    }

    private void multiVal(int left, int right, int start, int end, int p, int val){
        if (left<=start && right>=end){
            tree[p]*=val;
            multi[p]+=val;
            return;
        }
        int mid = start + ((end-start)>>1);
        if (multi[p]!=0){  // 把惰性标记往下传
            tree[2*p] *= multi[p];
            tree[2*p+1] *= multi[p];
            multi[2*p] = multi[2*p+1] = multi[p];
            multi[p] = 0;
        }
        if (left<=mid) multiVal(left, right, start, mid, 2*p, val);
        if (mid<right) multiVal(left, right, mid+1, end, 2*p+1, val);
        tree[p] = tree[2*p] + tree[2*p+1];
    }

    public int getSum(int left, int right){
        if (flag){  // 最近一次操作是加
            pushMulti(0, arr.length-1, 1);
            pushAdd(0, arr.length-1, 1);
        }else { // 最近一次操作是乘
            pushAdd(0, arr.length-1, 1);
            pushMulti(0, arr.length-1, 1);
        }
        return getSum(left, right, 0, arr.length-1, 1);
    }

    private int getSum(int left, int right, int start, int end, int p){
        if (left<=start && right>=end){
            return tree[p];
        }
        int mid = start + ((end-start)>>1);
        int sum = 0;
        if (mid>=left) sum += getSum(left, right, start, mid, 2*p);
        if (mid<right) sum+= getSum(left, right, mid+1, end, 2*p+1);
        return sum;
    }

    private void pushAdd(int left, int right, int p){
        if (left==right){
            add[p] = 0;
            return;
        }
        int mid = left + ((right-left)>>1);
        if (add[p]!=0){
            tree[2*p]+=add[p]*(mid-left+1);
            tree[2*p+1]+=add[p]*(right-mid);
            add[2*p] = add[2*p+1] = add[p];
            add[p] = 0;
        }
        pushAdd(left, mid, 2*p);
        pushAdd(mid+1, right, 2*p+1);
    }

    private void pushMulti(int left, int right, int p){
        if (left==right){
            multi[p] = 0;
            return;
        }
        int mid = left + ((right-left)>>1);
        if (multi[p]!=0){
            tree[2*p]*=multi[p];
            tree[2*p+1]*=multi[p];
            multi[2*p] = multi[2*p+1] = multi[p];
            multi[p] = 0;
        }
        pushMulti(left, mid, 2*p);
        pushMulti(mid+1, right, 2*p+1);
    }
}
