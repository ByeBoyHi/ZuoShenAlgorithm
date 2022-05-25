package Chapter2_Structure.S8_SegmentTree;

/**
 * <线段树的实现> <br>
 * Time: 2022/5/21 <br>
 * User: HeyBoy <br>
 * <br>
 * 一个线段树具有以下性质：'*'代表某种运算<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;1. 封闭性：x属于S，y属于S，那么 (x*y)属于S<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;2. 结合性：(x*y)*z=x*(y*z)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;3. 存在幺元：存在e属于S，有e*x=x，e则称为左幺元；x*e=x，e则称为右幺元。当左右幺元都是同一个的时候，统称"幺元"。<br>
 * 如：max(x,y)就满足上面的性质，其中 -∞ 就是幺元。<br>
 */
public class MySegmentTree {

    // final关键字可以保护我们的成员不会指向别的对象
    private final int[] segment;  // 用来建立线段树
    private final int[] isAddVal;  // 用来记录是否有过更新值
    private final int[] isSetVal;  // 用来记录是否有过指定值
    private final int len;
    private boolean flag;


    public MySegmentTree(int[] arr) {
        flag = true;
        len = arr.length;
        segment = new int[4 * len];
        isAddVal = new int[4 * len];
        isSetVal = new int[4 * len];
    }

    /**
     * 假设实现一个线段树，每个节点维护的是区间和，具体树结构如T1。 <br>
     * 对于节点di，假设记录的区间[start,tail]的区间和，<br>
     * 那么左孩子就是d(2*i)，记录[start,(start+tail)/2]的区间和，右孩子就是d(2*i+1)，记录[(start+tail)/2+1,tail]的区间和<br>
     * <br>
     * 实现线段树的建立
     *
     * @param arr      数据数组
     * @param start    起始位置
     * @param tail     终止位置
     * @param curPoint 当前线段树的建立位置
     */
    public void build(int[] arr, int start, int tail, int curPoint) {
        if (start == tail) {
            segment[curPoint] = arr[start];
            return;
        }

        int m = start + ((tail - start) >> 1);  // 中点
        build(arr, start, m, curPoint * 2);  // 建立左子树
        build(arr, m + 1, tail, curPoint * 2 + 1);  // 建立右子树
        segment[curPoint] = segment[2 * curPoint] + segment[2 * curPoint + 1];  // core:对上面的建立，求和，建立当前节点
    }

    /**
     * 进行区间和的递归查询，我们查询到的区间可能是由不同的块组成，因此需要递归往下走
     *
     * @param left     我们需要查询的区间和的左边界
     * @param right    我们需要查询的区间和的右边界
     * @param start    当前遍历到的区间和的左边界
     * @param tail     当前遍历到的区间和的右边界
     * @param curPoint 当前线段树的位置
     * @return 返回我们查询到的区间和
     */
    public int getSumAboutAdd(int left, int right, int start, int tail, int curPoint) {
        if (left <= start && tail <= right) {  // 当前区间为带查询区间和的子区间，直接返回给上一层
            return segment[curPoint];
        }

        int m = start + ((tail - start) >> 1);
        // 判断惰性标记
        if (isAddVal[curPoint] != 0) {  // 加过标记
            // 下面一定还有孩子节点
            // 因为根据线段树的构造，如果没有孩子节点，那么当前节点一定是单一的点，如果是单一的点，一定会被上面的if语句拦住
            funcAdd(curPoint, start, m, tail);
        }

        int sum = 0;
        if (left <= m) {  // 说明左儿子的区间和[start,m]有交集
            sum += getSumAboutAdd(left, right, start, m, 2 * curPoint);
        }
        if (right > m) {  // 说明右儿子的区间和和[m+1,tail]有交集
            sum += getSumAboutAdd(left, right, m + 1, tail, 2 * curPoint + 1);
        }
        return sum;
    }

    /**
     * 在求指定区间上的和
     *
     * @param left     指定区间的左边界
     * @param right    指定区间的右边界
     * @param start    当前区间的左边界
     * @param tail     当前区间的右边界
     * @param curPoint 当前节点
     * @return 返回最终求和
     */
    private int getSumAboutSet(int left, int right, int start, int tail, int curPoint) {
        // 遇到子区间，直接返回区间的值
        if (left <= start && right >= tail) return segment[curPoint];

        int mid = start + ((tail - start) >> 1); // 区间中点
        // 说明之前有重置过
        if (isSetVal[curPoint] != 0) funcSet(curPoint, left, mid, right);

        int sum = 0;
        if (left <= mid) sum += getSumAboutSet(left, right, start, mid, 2 * curPoint);
        if (right >= mid) sum += getSumAboutSet(left, right, mid + 1, tail, 2 * curPoint + 1);

        return sum;
    }

    /**
     * 对指定区间的所有值都加上指定的值
     *
     * @param left     更新区间的左边界
     * @param right    更新区间的右边界
     * @param start    当前节点包含的区间的左边界
     * @param tail     当前节点包含的区间的右边界
     * @param curPoint 当前节点
     * @param val      更新区间的变化量
     */
    public void addVal(int left, int right, int start, int tail, int val, int curPoint) {
        flag = true;
        // 当前区间是修改区间的子区间
        if (left <= start && right >= tail) {
            segment[curPoint] += (tail - start + 1) * val;  // 加上变化量
            isAddVal[curPoint] += val;  // 打上惰性标记
            return;
        }
        int m = start + ((tail - start) >> 1);
        // 在进行更新操作的时候，发现了之前的更新标记
        // 那么就进行标记传递下去，便于和本次更新的标记整合
        if (isAddVal[curPoint] != 0 && start != tail) funcAdd(curPoint, start, m, tail);

        if (left <= m) addVal(left, right, start, m, val, 2 * curPoint);
        if (right >= m) addVal(left, right, m + 1, tail, val, 2 * curPoint + 1);
        segment[curPoint] = segment[2 * curPoint] + segment[2 * curPoint + 1];  // 递归回来的时候更新当前节点
    }

    /**
     * 对指定区间的所有值都修改为某一个指定的值
     *
     * @param left     更新区间的左边界
     * @param right    更新区间的右边界
     * @param start    当前节点包含的区间的左边界
     * @param tail     当前节点包含的区间的右边界
     * @param curPoint 当前节点
     * @param val      区间的最终修改值
     */
    public void modifyToVal(int left, int right, int start, int tail, int val, int curPoint) {
        flag = false;
        // 当前区间为修改区间的子区间
        if (left <= start && right >= tail) {
            segment[curPoint] = (tail - start + 1) * val;
            isSetVal[curPoint] = val;
            return;
        }

        int mid = start + ((tail - start) >> 1);
        if (isSetVal[curPoint] != 0) funcSet(curPoint, start, mid, tail);

        if (left <= mid) modifyToVal(left, right, start, mid, val, 2 * curPoint);
        if (right >= mid) modifyToVal(left, right, mid + 1, tail, val, 2 * curPoint + 1);
        // 更新当前节点的值
        segment[curPoint] = segment[2 * curPoint] + segment[2 * curPoint + 1];
    }

    /**
     * 进行向下更新（add）
     */
    private void pushDownAdd(int left, int right, int curPoint) {
        if (left == right) {
            isAddVal[curPoint] = 0;  // 叶子节点的惰性标记我们再上一层已经加过了，这里只需要做清楚操作即可
            return;
        }

        int mid = left + ((right - left) >> 1);
        if (isAddVal[curPoint] != 0) funcAdd(curPoint, left, mid, right);

        pushDownAdd(left, mid, 2 * curPoint);
        pushDownAdd(mid + 1, right, 2 * curPoint + 1);
    }

    /**
     * 进行向下更新(Set)
     */
    private void pushDownSet(int left, int right, int curPoint) {
        if (left == right) {  // 叶子
            isSetVal[curPoint] = 0;
            return;
        }

        int mid = left + ((right - left) >> 1);
        if (isSetVal[curPoint] != 0) funcSet(curPoint, left, mid, right);

        pushDownSet(left, mid, 2 * curPoint);
        pushDownSet(mid + 1, right, 2 * curPoint + 1);
    }

    public int getSum(int left, int right) {
        // 如果为true，说明最近一次的更新操作是add；否则是set
        if (flag) {
            pushDownSet(0, len - 1, 0);  // 清理所有的set标记，然后取add之后的值
            return getSumAboutAdd(left, right, 0, len, 0);
        } else {
            pushDownAdd(0, len - 1, 0);  // 清理所有的add标记，然后取set之后的值
            return getSumAboutSet(left, right, 0, len, 0);
        }
    }


    private void funcAdd(int curPoint, int start, int mid, int tail) {
        segment[curPoint * 2] += isAddVal[curPoint] * (mid - start + 1);   // 左孩子的值进行总和更新
        segment[curPoint * 2 + 1] += isAddVal[curPoint] * (tail - mid);   // 有孩子的值进行总和更新

        isAddVal[curPoint * 2] += isAddVal[curPoint];       // 左孩子标记更新
        isAddVal[curPoint * 2 + 1] += isAddVal[curPoint];   // 右孩子标记更新

        isAddVal[curPoint] = 0;               // 清除当前节点的标记
    }

    private void funcSet(int curPoint, int left, int mid, int right) {
        segment[2 * curPoint] = isSetVal[curPoint] * (mid - left + 1);
        segment[2 * curPoint + 1] = isSetVal[curPoint] * (right - mid);

        isSetVal[2 * curPoint] = isSetVal[2 * curPoint + 1] = isSetVal[curPoint];

        isSetVal[curPoint] = 0;
    }
}
