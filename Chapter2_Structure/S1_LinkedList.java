package Chapter2_Structure;

public class S1_LinkedList {
    // 单链表
    static class Node<V>{
        V value;
        Node<V> next;
    }
    // 双链表
    static class doubleNode<V>{
        V value;
        doubleNode<V> next;
        doubleNode<V> pre;
    }
}
