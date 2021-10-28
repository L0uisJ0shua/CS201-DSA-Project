package datastruct;

public class TreeNode<E> {
    
    int aux = 0;
    private E element;          // an element stored at this node
    private TreeNode<E> parent;     // a reference to the parent node (if any)
    private TreeNode<E> left;       // a reference to the left child (if any)
    private TreeNode<E> right;      // a reference to the right child (if any)

    public TreeNode(E e, TreeNode<E> parent, TreeNode<E> left, TreeNode<E> right) {
        this.element = e;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public E getElement() {return element;}
    public TreeNode<E> getParent() { return parent; }
    public TreeNode<E> getLeft() { return left; }
    public TreeNode<E> getRight() { return right; }

    public void setElement(E e) { element = e; }
    public void setParent(TreeNode<E> parentNode) { parent = parentNode; }
    public void setLeft(TreeNode<E> left) { this.left = left; }
    public void setRight(TreeNode<E> right) { this.right = right; }
}
