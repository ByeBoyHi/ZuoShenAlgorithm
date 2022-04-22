package Chapter3_Algorithm.U1_Greedy;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class th4_IPO {
    // Q3
    // 小根堆：按照花费
    public static class minCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    // 大根堆：按照利润
    public static class maxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    // 项目类
    static class Program {
        int cost;
        int profit;

        public Program() {}

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    /**
     * 给了个项目列表，只能做k个项目，返回能获得的最大利润
     * @param programs  项目清单，有花费和利润两个属性
     * @param k         最多可以做多少个项目
     * @param W         手上的初始金额
     * @return          返回可以获得的最大利润
     */
    public static int findMaximizedCapital(Program[] programs, int k, int W){
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new maxProfitComparator());
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new minCostComparator());
        Collections.addAll(minCostQ, programs);
        int n = 0;
        int curW = W;
        while (n<=k){
            while (minCostQ.size()>0){  // 把所有消费小于手持金额curW的加入到大根堆里面
                Program cur = minCostQ.poll();
                if (cur.cost>curW){
                    minCostQ.add(cur);
                    break;
                }
                maxProfitQ.add(cur);
            }
            if (maxProfitQ.size()==0){  // 手上的钱一个项目也做不了，直接退出
                break;
            }
            curW+=maxProfitQ.poll().profit;
            n++;
            // 然后把大根堆的项目全部倒回小根堆
            while (maxProfitQ.size()>0){
                minCostQ.add(maxProfitQ.poll());
            }
        }
        return curW;
    }

    // 左神代码
    public static int findMaximizedCapitalZ(Program[] programs, int k, int W) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new minCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new maxProfitComparator());
        Collections.addAll(minCostQ, programs);
        for (int i=0; i<k; i++){  // 进行k轮
            // 把能买的项目放进大根堆
            while (!minCostQ.isEmpty() && minCostQ.peek().cost<W){
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()){  // 如果没有项目可做
                return W;
            }
            W+=maxProfitQ.poll().profit;
        }
        return W;
    }
}
