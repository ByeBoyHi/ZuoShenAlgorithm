package Chapter3_Algorithm.U1_Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class th1_meeting {
    /*
        贪心策略：Q1 以最早结束时间为准，不断的找符合条件的会议
     */
    static class Program{
        int start;  // 开始时间
        int end;    // 结束时间

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 比较器：以结束时间早进行排序
    static class ProgramComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end-o2.end;
        }
    }

    /**
     * 对一个会议清单进行会议安排，返回今天可以安排的会议最多数量
     * @param programs   所有的会议列表
     * @param timePoint  目前来到的时间点，初始值是一天的最爱是
     * @return           返回可以安排的最大会议数量
     */
    public static int getMaxNumberOfMeet(Program[] programs, int timePoint){
        // 按照最早结束时间排序
        Arrays.sort(programs, new ProgramComparator());
        int ans = 0;
        for (Program program : programs) {
            if (program.start >= timePoint) {
                ans++;
                timePoint = program.end;
            }
        }
        return ans;
    }
}
