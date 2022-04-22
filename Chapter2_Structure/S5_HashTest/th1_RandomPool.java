package Chapter2_Structure.S5_HashTest;

import java.util.HashMap;

public class th1_RandomPool {
    // Q1
    // 对于哈希表的应用
    /*
        map1: str->index    map2: index->str
     */

    public static class RandomPool<K>{
        HashMap<K, Integer> KeyIndexMap;
        HashMap<Integer, K> IndexKeyMap;
        int size;
        public RandomPool(){
            this.KeyIndexMap = new HashMap<>();
            this.IndexKeyMap = new HashMap<>();
            this.size = 0;
        }

        public void insert(K k){
            if (!KeyIndexMap.containsKey(k)) {
                KeyIndexMap.put(k, size);
                IndexKeyMap.put(size, k);
                size++;
            }
        }

        public K getRandom(){
            if (size==0){
                return null;
            }
            int rand = (int)(Math.random()*this.size); // 0~size-1
            return this.IndexKeyMap.get(rand);
        }

        public void delete(K k){
            if (KeyIndexMap.containsKey(k)){  // 包含的情况下，进行删除
                int deleteIndex = KeyIndexMap.get(k);
                K lastKey = IndexKeyMap.get(--size);
                KeyIndexMap.put(lastKey, deleteIndex);  // 表一把坐标换成要删除的位置的坐标
                IndexKeyMap.put(deleteIndex, lastKey);  // 表二把删除位置的坐标值换成最后一个
                KeyIndexMap.remove(k);   // 删除指定的k的坐标信息
                IndexKeyMap.remove(size); // 删除最后一个不需要的值
            }
        }
    }
}
