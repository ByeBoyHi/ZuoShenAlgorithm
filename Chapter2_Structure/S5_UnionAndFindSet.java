package Chapter2_Structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class S5_UnionAndFindSet {
    // 元素包裹之后，在进行寻找的时候，就是判断的地址，而不是简单的数值判断
    public static class Element<V> {
        V value;

        public Element(V value) {
            this.value = value;
        }
    }

    // 并查集结构
    public static class UnionFindSet<V> {
        // 表一：记录当前元素和对应的包装元素
        public Map<V, Element<V>> elementMap;
        // 表二：记录当前元素对应的父元素
        public Map<Element<V>, Element<V>> fatherMap;
        // 表三：记录最上面节点所在的图右多少个节点
        public Map<Element<V>, Integer> sizeMap;

        // 构造方法传入一系列的节点
        public UnionFindSet(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : list) {
                Element<V> element = new Element<>(v);
                elementMap.put(v, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        // 找自己的最顶部，并且扁平化————关于扁平化，用一个数据结构存一下，再吐出来设置一下就行
        private Element<V> findHead(Element<V> element) {
            Stack<Element<V>> pool = new Stack<>();
            while (element != fatherMap.get(element)) {  // 只有最顶部，自己是自己的父亲
                pool.add(element);
                element = fatherMap.get(element);
            }
            // 扁平化处理
            while (!pool.isEmpty()) {
                fatherMap.put(pool.pop(), element);
            }
            return element;
        }

        // 是否是同一个集合
        public boolean isSameSet(V v1, V v2) {
            if (elementMap.containsKey(v1) && elementMap.containsKey(v2)) {
                return findHead(elementMap.get(v1)) == findHead(elementMap.get(v2));
            }
            return false;
        }

        // 合并两个集合
        public boolean Union(V v1, V v2) {
            if (!isSameSet(v1, v2)) {
                // 取到两个头节点
                Element<V> a = findHead(elementMap.get(v1));
                Element<V> b = findHead(elementMap.get(v2));
                // 分出一大一小两个节点
                Element<V> big = sizeMap.get(a) > sizeMap.get(b) ? a : b;
                Element<V> small = big == a ? b : a;
                // 把小的链接到大的上面
                fatherMap.put(small, big);
                sizeMap.put(big, sizeMap.get(big)+sizeMap.get(small));
                sizeMap.remove(small);
                return true;
            }
            return false;
        }

        /*
            考虑怎么用多个CPU让岛问题速度运算变快？

            对数据区域进行划分，让每个CPU负责其中一个区域，统计里面的岛的数量。
            在统计岛进行感染的时候，对其进行标记，表示他们是哪个CPU感染的。
            统计完各自的数量后，再使用一个CPU对边界区域进行扫荡。
            将边界紧邻但不是一个岛屿的归属到一起，每一次归属，都让之前统计的总数-1，
            最后的数量就是整个图的岛屿数量。
         */
    }
}
