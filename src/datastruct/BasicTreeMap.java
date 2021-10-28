package datastruct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Concrete implementation of a basic Tree Map using a node-based, linked structure.
// No Self Balancing of the Tree Map


public class BasicTreeMap<K, V> {
    

    // Nested Node class : BST Node
    protected static class BSTNode<E> extends TreeNode<E> {
        int aux = 0;
        BSTNode(E e, TreeNode<E> parent, TreeNode<E> leftChild, TreeNode<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }
        public int getAux() { return aux; }
        public void setAux(int value) { aux = value; }    
    }


    // Factory function to create new node storing element e
    TreeNode<Entry<K,V>> createNode(Entry<K,V> e, TreeNode<Entry<K,V>> parent, 
                                    TreeNode<Entry<K,V>> left, TreeNode<Entry<K,V>> right) {
        return new TreeNode<Entry<K,V>>(e, parent, left, right);
    }


    // Instance Variables
    protected TreeNode<Entry<K,V>> root = null;
    private int size = 0;
    private Comparator<K> comp;


    // Constructors
    public BasicTreeMap(Comparator<K> comp){ 
        this.comp = comp;
    } 
    public BasicTreeMap() {
        this(new DefaultComparator<K>());
    }


    // Accessor Methods
    public int size() {
        return size;
    }
    public TreeNode<Entry<K,V>> root() {
        return root;
    }


    // Utility Methods
    public boolean isEmpty() {
        return size == 0;
    }
    public TreeNode<Entry<K,V>> addRoot(Entry<K,V> e) throws IllegalStateException {
        if(!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }
    public int numChildren(TreeNode<Entry<K,V>> node) {
        int count=0;
        if (node.getLeft() != null) count++;
        if (node.getRight() != null) count++;
        return count;
    }
    public Iterable<TreeNode<Entry<K,V>>> children(TreeNode<Entry<K,V>> node) {
        List<TreeNode<Entry<K,V>>> snapshot = new ArrayList<>(2);    // max capacity of 2
        if (node.getLeft() != null) snapshot.add(node.getLeft());
        if (node.getRight() != null) snapshot.add(node.getRight());
        return snapshot;
    }
    public boolean isInternal(TreeNode<Entry<K,V>> node) {
        return numChildren(node) > 0;
    }
    public boolean isExternal(TreeNode<Entry<K,V>> node) {
        return numChildren(node) == 0;
    }
    public boolean isRoot(TreeNode<Entry<K,V>> node) {
        return node == root();
    }
    public int depth(TreeNode<Entry<K,V>> node) throws IllegalArgumentException {
        if (isRoot(node))
          return 0;
        else
          return 1 + depth(node.getParent());
    }
    public int height(TreeNode<Entry<K,V>> node) throws IllegalArgumentException {
        int h = 0;                          // base case if node is external
        for (TreeNode<Entry<K,V>> c : children(node))
          h = Math.max(h, 1 + height(c));
        return h;
    }


    // Compare Methods
    protected int compare(Entry<K,V> a, Entry<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }
    protected int compare(K a, Entry<K,V> b) {
        return comp.compare(a, b.getKey());
    }
    protected int compare(Entry<K,V> a, K b) {
        return comp.compare(a.getKey(), b);
    }
    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }


    // TreeSearch Method - return the node in the tree having the given key
    private TreeNode<Entry<K,V>> treeSearch(TreeNode<Entry<K,V>> root, K key) {
        if (isExternal(root)) return root; // key not found, return final leaf
        
        int comp = compare(key, root.getElement());
        if (comp == 0) { // key found return entry
            return root;
        }
        else if (comp < 0 ) { // search left subtree
            return treeSearch(root.getLeft(), key);
        }
        else { // search right sub tree
            return treeSearch(root.getRight(), key);
        }
    }


    // Remove TreeNode 
    public Entry<K,V> remove(TreeNode<Entry<K,V>> node) throws IllegalArgumentException {
        if (numChildren(node) == 2) throw new IllegalArgumentException("P has two children");

        TreeNode<Entry<K,V>> child = ( node.getLeft() != null ? node.getLeft() : node.getRight());

        // child's grandparent becomes its parent
        if (child != null) { 
            child.setParent(node.getParent());
        }
        if (node == root) { 
            root = child;
        }
        else {
            // set left or right child for the new parent
            TreeNode<Entry<K,V>> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            }
            else{
                parent.setRight(child);
            }
        }
    
        size --;
        Entry<K,V> temp = node.getElement();
        node.setElement(null);                // help garbage collection
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);                 // our convention for defunct node
        return temp;
    }

    // Put Method
    public V put(K key, V value) throws IllegalArgumentException {
        Entry<K,V> newEntry = new Entry<>(key, value);

        TreeNode<Entry<K,V>> node = treeSearch

    }

}
