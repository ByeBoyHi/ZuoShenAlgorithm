package Chapter2_Structure.S5_HashTest;

public class th2_BitMap {
    public static void main(String[] args) {
        int a = 0;

        // 信息表
        int[] arr = new int[10];  // 32bit*10 --> 320bit
        // arr[0]  0~31
        // arr[1]  32~63

        //----------------------------------
        // 想要获得第178位的bit信息
        int i = 178;

        int numIndex = 178 / 32;  // 找到在第几个格子
        int bitIndex = 178 % 32;  // 在这个格子的第几个

        // 取到第numIndex个格子
        // 右移bitIndex位置，这个位置就到了最低位
        // 和1&，如果结果是1，那么这一位是1，否则这一位是0
        int s = ((arr[numIndex] >> bitIndex) & 1);

        //-----------------------------------
        /*
            把第bitIndex位置的值变成1，和原来的数据或一下，就会把这个数据变成1
         */
        // 把第178位的状态改为1
        arr[numIndex] = arr[numIndex] | (1 << bitIndex);
        /*
            把第bitIndex位置的值变为1，然后取反，保证这一位是0，其他是1，
            然后和原来的数据&，可以保留其他位的数据，然后让当前位置为0
         */
        // 把第178位的状态改为0
        arr[numIndex] = arr[numIndex] & (~(1 << bitIndex));

        //------------------------------------
        // 把第178位置的状态拿出来
        int bit = (arr[i / 32] >> (i % 32)) & 1;

    }

}
