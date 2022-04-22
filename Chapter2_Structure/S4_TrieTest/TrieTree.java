package Chapter2_Structure.S4_TrieTest;

import com.sun.source.tree.Tree;

public class TrieTree {
    static class TrieNode{
        int pass;          // 经过这个字母多少次
        int end;           // 多少字符串以这个字母结尾
        // 字符种类特别多的时候，可以用哈希表 HashMap<Char, Node>
        // 如果希望有序，也可以用有序表：TreeMap<Char, Node>
        TrieNode[] nexts;  // a-z
        public TrieNode(){
            pass = 0;
            end = 0;
            nexts = new TrieNode[26];
        }
    }

    /*
        根节点的 Pass 代表有多少个字符串
        根节点的 End  代表有多少个空串
     */
    TrieNode root;
    public TrieTree(){
        root = new TrieNode();
    }

    // 插入字符串
    public boolean insert(String word){
        if(word==null){
            return false;
        }
        TrieNode node = root;
        node.pass++;
        char[] chs = word.toCharArray();
        int index = 0;
        for (int i=0; i<word.length(); i++){
            index = chs[i]-'a';
            if (node.nexts[index]==null){
                node.nexts[index] = new TrieNode();
            }
            node = node.nexts[index];
            node.pass++;
        }
        // 最后一个字母
        node.end++;
        return true;
    }

    // 查询word这个单词出现过几次
    public int search(String word){
        if (word==null){
            return 0;
        }
        int index = 0;
        TrieNode node = root;
        char[] chs = word.toCharArray();
        for (int i=0; i<word.length(); i++){
            index = chs[i]-'a';
            if (node.nexts[index]==null){  // 这个字符串中间有一个字母断掉了，说明这个字符串没有出现过
                return 0;
            }
            node = node.nexts[index];
        }
        // 上面所有字母都出现过，返回最后一个字母结尾的次数
        return node.end;
    }

    // 查询以preStr为前缀的字符串的个数
    public int searchPre(String pre){
        if (pre==null){
            return 0;
        }
        int index = 0;
        TrieNode node = root;
        char[] chs = pre.toCharArray();
        for (int i=0; i<pre.length(); i++){
            index = chs[i]-'a';
            if (node.nexts[index]==null){
                return 0;
            }
            node = node.nexts[index];
        }
        return node.pass;  // 经过多少次，就有多少个前缀
    }

    // 删除指定的字符串
    public boolean delete(String word){
        if (search(word)==0){
            return false;
        }
        int index = 0;
        TrieNode node = root;
        char[] chs = word.toCharArray();
        for (char ch : chs) {  // 上面已经search过了，说明必然有，不需要null判断了
            index = ch - 'a';
            node = node.nexts[index];
            node.pass--;
            if (node.pass==0){  // 如果删除当前节点后，pass已经没有了，就把这个后面的都清空
                node=null;  // JVM会去释放无用空间
                break;
            }
        }
        if (node!=null) {
            node.end--;
        }

        return true;
    }
}
