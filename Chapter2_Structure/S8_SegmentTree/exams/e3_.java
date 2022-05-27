package Chapter2_Structure.S8_SegmentTree.exams;

/**
 * 假设货架上从左到右摆放了N种商品，并且依次标号为1到N，其中标号为i的商品的价格为Pi。 <br>
 * 小 Hi 的每次操作分为两种可能，<br>
 * 第一种是修改价格：小 Hi 给出一段区间[L, R]和一个新的价格NewP，所有标号在这段区间中的商品的价格都变成NewP。<br>
 * 第二种操作是询问：小 Hi 给出一段区间[L, R]，而小 Ho 要做的便是计算出所有标号在这段区间中的商品的总价格，然后告诉小 Hi。<br>
 * <br>
 * Time: 2022/5/27 <br>
 * User: HeyBoy <br>
 */
public class e3_ {
    public static void main(String[] args) {
        int[] price = {10, 11, 12, 13, 14};
        Store store = new Store(price);
        store.build();
        System.out.println(store.getSum(0, 2));
        store.setVal(0, 2, 5);
        System.out.println(store.getSum(0, 2));
        store.setVal(3, 4, 6);
        System.out.println(store.getSum(0, 4));
    }
}

class Store {
    // 记录区间和
    private final int[] sum;
    // 记录修改标记
    private final int[] update;
    // 总价格单
    private final int[] price;

    public Store(int[] price) {
        this.price = price;
        sum = new int[4 * price.length];
        update = new int[4 * price.length];
    }

    // 建立线段树
    public void build() {
        build(0, price.length - 1, 1);
    }

    private void build(int left, int right, int p) {
        if (left == right) {
            sum[p] = price[left];
            return;
        }
        int mid = left + ((right - left) >> 1);
        build(left, mid, 2 * p);
        build(mid + 1, right, 2 * p + 1);
        sum[p] = sum[2 * p] + sum[2 * p + 1];
    }

    // 把区间内所有值都设定为统一值
    public void setVal(int left, int right, int val) {
        setVal(left, right, 0, price.length - 1, 1, val);
    }

    private void setVal(int left, int right, int start, int end, int p, int val) {
        if (left <= start && right >= end) {
            sum[p] = (end - start + 1) * val;
            update[p] = val;
            return;
        }
        int mid = start + ((end - start) >> 1);
        if (update[p] != 0) {
            sum[2 * p] = update[p] * (mid - start + 1);
            sum[2 * p + 1] = update[p] * (end - mid);
            update[2 * p] = update[2 * p + 1] = update[p];
            update[p] = 0;
        }
        if (mid >= left) setVal(left, right, start, mid, 2 * p, val);
        if (mid < right) setVal(left, right, mid + 1, end, 2 * p + 1, val);
        sum[p] = sum[2 * p] + sum[2 * p + 1];  // 可能某个位置更新了，但是他上面的没更新，这里是递归往上走，把上面的更新，下面的用惰性标记保留了
    }

    // 求区间和
    public int getSum(int left, int right) {
        return getSum(left, right, 0, price.length - 1, 1);
    }

    private int getSum(int left, int right, int start, int end, int p) {
        if (left <= start && right >= end) return sum[p];
        int mid = start + ((end - start) >> 1);
        if (update[p] != 0) {
            sum[2 * p] = update[p] * (mid - start + 1);
            sum[2 * p + 1] = update[p] * (end - mid);
            update[2 * p] = update[2 * p + 1] = update[p];
            update[p] = 0;
        }
        int sum = 0;
        if (mid >= left) sum += getSum(left, right, start, mid, 2 * p);
        if (mid < right) sum += getSum(left, right, mid + 1, end, 2 * p + 1);
        return sum;
    }

}
