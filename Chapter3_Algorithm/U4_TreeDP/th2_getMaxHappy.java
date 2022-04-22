package Chapter3_Algorithm.U4_TreeDP;

import java.util.ArrayList;
import java.util.List;

public class th2_getMaxHappy {
    // Q2：给人发请柬，直接上司来的话，直接下属不来，但是简介下属不影响
    //      要求怎么发请柬，可以让快乐值最大。（多叉树DP问题）
    /*
        DP问题：以X为头的整棵树，最大快乐值是多少？
        1. X参与。X乐 + (下属A不来的情况下整棵树的最大值) + ...
        2. X不参与。X不乐 + max(下属A{来或不来}的情况下整棵树的最大值) + ...
        综上返回结构：
        1. 来的时候，整个树的最大快乐值
        2. 不来的时候，整个树的最大快乐值
     */

    static class ReturnType{
        public int laiMaxHappy;
        public int buMaxHappy;
        public ReturnType(int laiMaxHappy, int buMaxHappy) {
            this.laiMaxHappy = laiMaxHappy;
            this.buMaxHappy = buMaxHappy;
        }
    }
    static class Employee{
        int happy;
        List<Employee> employees;
        public Employee(int happy) {
            this.happy = happy;
            employees = new ArrayList<>();
        }
    }

    public static ReturnType process(Employee e){
        if (e.employees.isEmpty()){  // 基层员工
            return new ReturnType(e.happy, 0);
        }
        int lai = e.happy;
        int bu = 0;
        for (Employee employee: e.employees){
            ReturnType nextReturn = process(employee);
            lai+=nextReturn.buMaxHappy;
            bu+=Math.max(nextReturn.buMaxHappy, nextReturn.laiMaxHappy);
        }
        return new ReturnType(lai, bu);
    }

    public static int maxHappy(Employee boss){
        ReturnType info = process(boss);
        return Math.max(info.buMaxHappy, info.laiMaxHappy);
    }
}
