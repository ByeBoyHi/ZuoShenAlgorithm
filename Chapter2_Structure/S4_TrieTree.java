package Chapter2_Structure;

public class S4_TrieTree {
    static class TrieNode{  // 节点
        int pass;  // 经过当前节点多少次
        int end;   // 以当前节点结尾的字母有多少个
        TrieNode[] nexts;
        public TrieNode(){
            pass = 0;
            end = 0;
            nexts = new TrieNode[26];  // a-z
        }
    }
}
