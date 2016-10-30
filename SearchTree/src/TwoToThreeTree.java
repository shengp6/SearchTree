 /**
 * A 2-3+ tree
 * Store comparable objects
 * Internal nodes store the keys
 * Leaf nodes store the actual objects
 * Leaf nodes are in a doubly linked list
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/02/2016
 */
public class TwoToThreeTree {
    private TreeNode root;
    /**
     * Constructor
     * Create an empty node, which is
     * the root of the tree
     */
    public TwoToThreeTree() {
        root = new LeafNode();
    }
    /**
     * insert an entry to the tree
     * @param entry the entry to insert
     */
    public void insert(KVPair entry) {
        if (root.isLeaf()) {
            root = root.insert(entry);
        }
        else {
            root = root.insert(entry);
        }
    }
    /**
     * Return the root of the tree
     * @return the root of the tree
     */
    public TreeNode getTree() {
        return root;
    }
    /**
     * print the tree
     * @return the string representing the tree
     */
    public String printTree() {
        return printTreeHelp(root, 0);
    }
    /**
     * Recursive print function
     * @param node the root of the tree to print
     * @param degree the degree of the node in the tree
     * @return the string representing the tree
     */
    private String printTreeHelp(TreeNode node, int degree) {
        StringBuilder sb = new StringBuilder();
        sb.append(node.printNode(degree) + "\n");
        if (!node.isLeaf()) {
            InternalNode nodeCopy = (InternalNode)node;
            sb.append(printTreeHelp(nodeCopy.getLeftChild(),
                    degree + 1));
            if (nodeCopy.getCenterChild() != null) {
                sb.append(printTreeHelp(nodeCopy.getCenterChild(),
                        degree + 1));
            }
            if (nodeCopy.getRightChild() != null) {
                sb.append(printTreeHelp(nodeCopy.getRightChild(),
                        degree + 1));
            }
        }
        return sb.toString();
    }
    /**
     * Search if the tree contains the entry (KVPair)
     * @param entry the entry to search
     * @return the node contains the entry if the entry
     *          exists, null if not
     */
    public LeafNode search(KVPair entry) {
        return (LeafNode)this.findHelp(this.root, entry);
    }
    /**
     * Recursive search function helping search (KVPair)
     * @param node the root of the tree/subtree
     * @param entry the entry to search
     * @return the node contains the entry if the entry
     *          exists, null if not
     */
    private TreeNode findHelp(TreeNode node, KVPair entry) {
        if (node.isLeaf()) {
            return ((LeafNode)node).search(entry);
        }
        else {
            InternalNode root1 = (InternalNode)node;
            if (entry.compareTo(root1.getFirstData()) < 0) {
                return findHelp(root1.getLeftChild(), entry);
            }
            else {
                if (root1.getRightChild() == null) {
                    return findHelp(root1.getCenterChild(), entry);
                }
                else {
                    if (entry.compareTo(root1.getSecondData()) < 0) {
                        return findHelp(root1.getCenterChild(), entry);
                    }
                    else {
                        return findHelp(root1.getRightChild(), entry);
                    }
                }
            }
        }
    }
    /**
     * Search if the tree contains the entry (Key)
     * @param entry the entry to search
     * @return the first node contains the entry if the entry
     *          exists, null if not
     */
    public LeafNode search(Handle entry) {
        LeafNode result = (LeafNode)this.findHelp(this.root, entry);
        if (result == null) {
            return null;
        }
        while (result.previous() != null &&
                (result.previous().getFirstData().compareTo(entry) == 0
                || (result.previous().getSecondData() != null
                        && result.previous().getSecondData()
                        .compareTo(entry) == 0))) {
            result = result.previous();
        }
//        while (result.previous() != null) {
//            LeafNode pre = result.previous();
//            if (pre.getSecondData() != null
//                 && pre.getSecondData().compareTo(entry) == 0) {
//                    result = pre;
//            }
//            else if (pre.getFirstData().compareTo(entry) == 0) {
//                result = pre;
//            }
//            else {
//                return result;
//            }
//        }
        return result;
    }
    /**
     * Recursive search function helping search (Key)
     * @param node the root of the tree/subtree
     * @param entry the entry to search
     * @return the node contains the entry if the entry
     *          exists, null if not
     */
    private TreeNode findHelp(TreeNode node, Handle entry) {
        if (node.isLeaf()) {
            return ((LeafNode)node).search(entry);
        }
        else {
            InternalNode root1 = (InternalNode)node;
            if (root1.getFirstData().compareTo(entry) > 0) {
                return findHelp(root1.getLeftChild(), entry);
            }
            else {
                if (root1.getRightChild() == null) {
                    return findHelp(root1.getCenterChild(), entry);
                }
                else {
                    if (root1.getSecondData().compareTo(entry) > 0) {
                        return findHelp(root1.getCenterChild(), entry);
                    }
                    else {
                        return findHelp(root1.getRightChild(), entry);
                    }
                }
            }
        }
    }
    /**
     * delete an entry
     * @param entry the entry to delete
     */
    public void delete(KVPair entry) {
        this.deleteHelp(root, entry);
    }
    /**
     * help delete
     * @param node root of the tree/subtree
     * @param entry the entry to delete
     */
    private void deleteHelp(TreeNode node, KVPair entry) {
        if (node.isLeaf()) {
            root = node.delete(entry);
        }
        else {
            root = node.delete(entry);
        }
    }

}
