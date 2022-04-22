package Chapter2_Structure.S3_GraphTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySets {
    // 实现并查集的效果
    /*
        1. 判断两个集合是不是同一个集合
        2. 合并两个集合
     */
    // 记录当前节点所属集合
    public HashMap<Node, List<Node>> setMap = new HashMap<>();

    public MySets(List<Node> nodes){
        for (Node node: nodes){  // 最开始每个节点都是独自一个集合
            List<Node> cur = new ArrayList<>();
            cur.add(node);
            setMap.put(node, cur);
        }
    }

    // 是否是同一个集合：判断两个节点所在的集合的地址空间是否是同一个
    public boolean isSameSet(Node n1, Node n2){
        return setMap.get(n1)==setMap.get(n2);
    }

    // 合并
    public void union(Node n1, Node n2){
        if (!isSameSet(n1, n2)){
            List<Node> from = setMap.get(n1);
            List<Node> to = setMap.get(n2);
            for (Node node : to ){  // 把所有的to集合都放进from里面
                from.add(node);  // 节点集合加进去
                setMap.put(node, from); // 映射也要修改过去
            }
            to.clear();
        }
    }
}
