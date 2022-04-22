package Chapter2_Structure;

public class S3_Tree<T> {
    T val;
    S3_Tree<T> left;
    S3_Tree<T> right;
    public S3_Tree(){}
    public S3_Tree(T val) {
        this.val = val;
    }
}
