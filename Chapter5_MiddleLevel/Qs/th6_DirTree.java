package Chapter5_MiddleLevel.Qs;

import java.util.TreeMap;

public class th6_DirTree {
    static class Node{
        String str;
        TreeMap<String, Node> next;
        public Node(String str){
            this.str = str;
            next = new TreeMap<>();
        }
    }

    public static Node generateFolderTree(String[] folderPaths){
        Node head = new Node("");  // 根目录
        for (String curPath:folderPaths){
            String[] paths = curPath.split("\\\\");
            Node cur = head;
            for (String path: paths){
                if (!cur.next.containsKey(path)){
                    cur.next.put(path, new Node(path));
                }
                cur = cur.next.get(path);
            }
            // cur用来建立树结构，head始终记录头部
        }
//        printProcess(head, 0);
        return head;
    }

    // 深度优先的顺序打印
    public static void printProcess(Node head, int level){
        if (head==null) return;
        if (!head.str.equals("")){
            for (int x=level; x>0; x--){
                System.out.print("  ");
            }
            System.out.print(head.str);
            System.out.println();
        }
        for (Node cur : head.next.values()){
            printProcess(cur, level+1);
        }
    }

    public static void main(String[] args) {
        String[] paths = new String[]{
                "a\\b\\c", "a\\d\\e", "b\\f"
        };
        Node node = generateFolderTree(paths);
        printProcess(node, 0);
    }
}
