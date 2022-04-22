package Chapter2_Structure.S2_TreeTest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class th2_GetMaxWidth {

    public static void main(String[] args) {
        MyNode node1 = new MyNode(1);
        MyNode node2 = new MyNode(1);
        MyNode node3 = new MyNode(1);
        MyNode node4 = new MyNode(1);
        MyNode node5 = new MyNode(1);
        MyNode node6 = new MyNode(1);
        MyNode node7 = new MyNode(1);

        node1.left = node2;
//        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
//        node3.left = node6;
//        node6.left = node7;

        System.out.println(getMaxWidth2(node1));

    }

    static class MyNode{
        int val;
        MyNode left;
        MyNode right;
        public MyNode(int val){
            this.val = val;
        }
    }

    /**
     * 记录这个二叉树的最大宽度：也就是哪一层的节点数最多
     * 算法思路：哈希表实现
     *      我们可以用一个Map去记录每一个节点所在的层数；
     *      然后让头节点入队列，并且用一个变量来记录当前所在的层数；
     *      让头节点出队列后，记录以下出来的节点是否是当前层，如果是的话，用一个变量记录当前层的节点数，也就是++；
     *      在这个节点出队列后，还要让这个节点的孩子入队列，并且同时记录他们孩子的层数，也就是当前层数+1；
     *      每一次走完一层节点，都要更新变量max，记录目前已经记录过的层的节点个数，保持最大。
     *  由思路可以得出，需要以下节点：
     *      1. map --> <Node,Integer>：记录该节点所在层数
     *      2. curLevel --> 记录当前遍历的层数
     *      3. curLevelNode --> 当前层的节点个数
     *      4. max --> 记录之前所有层的某一层的最多节点数
     */
    public static int getMaxWidth(MyNode head){
        int max = 0;
        if (head!=null){
            // 双向链表用作队列
            Queue<MyNode> queue = new LinkedList<>();
            queue.add(head);
            Map<MyNode, Integer> map = new HashMap<>();
            int curLevel = 1; // 当前第一层
            int curLevelNodes = 0;  // 当前层的节点数
            max = 1;
            map.put(head, 1);
            while (!queue.isEmpty()){
                MyNode cur = queue.poll();
                // 如果弹出节点是当前层
                if (map.get(cur)==curLevel){
                    curLevelNodes++;
                }else {  // 如果不是当前层
                    max = Math.max(max, curLevelNodes);
                    curLevel = map.get(cur);
                    curLevelNodes = 1; // 已经发现一个节点
                }

                if (cur.left!=null){
                    queue.add(cur.left);
                    map.put(cur.left, curLevel+1);
                }
                if (cur.right!=null){
                    queue.add(cur.right);
                    map.put(cur.right, curLevel+1);
                }
            }
            // 遍历结束出来后，最后一层还没有进行比较更新
            max = Math.max(max, curLevelNodes);
        }
        return max;
    }


    // 不用哈希表进行计算最大层的节点数
    /**
     * 思路：我们可以用一个变量记录当前层的最后一个节点，用另一个变量记录下一层的最后一个节点。
     * 我们可以在当前层进来节点的时候，不断让他的左右孩子入队列，然后让最后一个入队列的作为nextEnd，也就是下一层的结束节点。
     * 当前层的最后一个节点初始值为head，因为只有一个节点。
     */
    public static int getMaxWidth2(MyNode head){
        int max = 0;
        if (head!=null){
            max = 1;
            Queue<MyNode> queue = new LinkedList<>();
            MyNode curEnd = head;
            MyNode nextEnd = null;
            int curLevelNodes = 1;
            queue.add(head);
            while (!queue.isEmpty()){
                MyNode cur = queue.poll();
                if (cur.left!=null){
                    queue.add(cur.left);
                    nextEnd = cur.left;
                }
                if (cur.right!=null){
                    queue.add(cur.right);
                    nextEnd = cur.right;
                }
                if (cur==curEnd){ // 走到了当前层的最后一个节点
                    curEnd = nextEnd;
                    nextEnd = null;
                    max = Math.max(max, curLevelNodes+1);
                    curLevelNodes = 0;
                }else {  // 还在当前层
                    curLevelNodes++;
                }
            }
            // 因为在while循环里面，最后一层会走到一个终点节点，这时候我们会进行判断，然后对max进行更新，所以这里就不需要了
        }
        return max;
    }
}
