package Chapter2_Structure.S2_TreeTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class th9_TreeAndStr {
    // 把一棵树变成字符串
    // 然后又从字符串恢复成一棵树
    // 生成一个字符串：下划线_表示结束，#表示null

    // 以head为头的树，先序的方式序列化成字符串返回
    public static String serialByPre(Node head){
        if (head == null){
            return "#_";
        }
        String res = head.val+"_";
        res += serialByPre(head.left);
        res += serialByPre(head.right);
        return res;
    }
    // 反序列化
    // 对于字符串进行特殊字符的拆分，然后对每一个值进行入队列，用户后续出队列的判断
    // 在进入先序递归取队列的每一个值，在遇到值的时候建立节点，在遇到#的时候，返回null
    public static Node reconByPreString(String preStr){
        String[] str = preStr.split("-");
        Queue<String> queue = new LinkedList<>(Arrays.asList(str));
        return process(queue);
    }
    public static Node process(Queue<String> queue){
        String str = queue.poll();
        if (Objects.equals(str, "#")){  // 遇到特殊符号
            return null;
        }
        // 遇到值，建立当前节点
        Node node = new Node(Integer.parseInt(Objects.requireNonNull(str)));
        node.left = process(queue);  // 建立左子树
        node.right = process(queue);  // 建立右子树
        return node;  // 把当前建立好的子树返回给上一层
    }
}
