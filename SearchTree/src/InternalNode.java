/**
 * internal node class
 * a internal node has two values and three
 * pointers stored in it
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/08/2016
 *
 */
public class InternalNode extends TreeNode {


    private TreeNode left;
    private TreeNode center;
    private TreeNode right;

    /**
     * Create a new empty internalNode
     */
    public InternalNode() {
        //create an empty internal node
    }
    /**
     * Create a new InternalNode
     * @param firstData first data in the node
     * @param secondData second data in the node
     * @param left left child of the node
     * @param center center child of the node
     * @param right right child of the node
     */
    public InternalNode(KVPair firstData, KVPair secondData, TreeNode left,
            TreeNode center, TreeNode right) {
        this.setFirstData(firstData);
        this.setSecondData(secondData);
        this.left = left;
        this.center = center;
        this.right = right;
    }
    /**
     * Set the first child of the node
     * @param n the node to set to the first child of this node
     */
    public void setLeftChild(TreeNode n) {
        left = n;
    }

    /**
     * Get the first child of the node
     * @return the first child of the node
     */
    public TreeNode getLeftChild() {
        return left;
    }

    /**
     * Set the second child of the node
     * @param n the node to set to the second child of this node
     */
    public void setCenterChild(TreeNode n) {
        center = n;
    }

    /**
     * Get the second child of the node
     * @return the second child of the node
     */
    public TreeNode getCenterChild() {
        return center;
    }
    /**
     * Set the third child of the node
     * @param n the node to set to the third child of this node
     */
    public void setRightChild(TreeNode n) {
        right = n;
    }
    /**
     * Get the third child of the node
     * @return the third child of the node
     */
    public TreeNode getRightChild() {
        return right;
    }
    /**
     * insert an entry to the node
     * @param entry the entry to insert
     * @return the node it self if is does not split,
     *          the parent of this node if it splits
     */
    @Override
    public TreeNode insert(KVPair entry) {
        return insertHelp(this, entry);
    }
    /**
     * Recursive insert help method
     * @param root the root of the tree/subtree
     * @param e the entry to insert
     * @return the node it self if is does not split,
     *          the parent of this node if it splits
     */
    private TreeNode insertHelp(TreeNode root, KVPair e) {
        if (root.isLeaf()) {
            return root.insert(e);
        }
        else {
            TreeNode newNode;
            InternalNode root1 = (InternalNode)root;
            if (e.compareTo(root.getFirstData()) < 0) {
                newNode = insertHelp(
                        ((InternalNode)root).getLeftChild(), e);
                if (newNode == root1.getLeftChild()) {
                    return root;
                }
                else {
                    InternalNode new1 = (InternalNode)newNode;
                    if (root1.getRightChild() == null) {
                        root1.setRightChild(root1.getCenterChild());
                        root1.setCenterChild(new1.getCenterChild());
                        root1.setFirstData(min(root1.getCenterChild()));
                        root1.setLeftChild(new1.getLeftChild());
                        root1.setSecondData(min(root1.getRightChild()));
                        return (root);
                    }
                    else {
                        InternalNode split = new InternalNode(null, null,
                                root1.getCenterChild(),
                                root1.getRightChild(), null);
                        split.setFirstData(min(split.getCenterChild()));
                        root1.setRightChild(null);
                        root1.setSecondData(null);
                        root1.setLeftChild(new1.getLeftChild());
                        root1.setCenterChild(new1.getCenterChild());
                        root1.setFirstData(min(root1.getCenterChild()));
                        InternalNode parent = new InternalNode(min(split),
                                null, root, split, null);
                        return parent;
                    }
                }
            } // end if e.compareTo(root.getFirstData()) < 0
            else {
                if (root1.getRightChild() == null) {
                    newNode = insertHelp(
                            root1.getCenterChild(), e);
                    if (newNode == root1.getCenterChild()) {
                        return root;
                    }
                    else {
                        InternalNode new1 = (InternalNode)newNode;
                        root1.setRightChild(new1.getCenterChild());
                        root1.setCenterChild(new1.getLeftChild());
                        root1.setFirstData(min(root1.getCenterChild()));
                        root1.setSecondData(min(root1.getRightChild()));
                        return (root);
                    }
                } // end if right child null
                else { // root has 3 children and 2 data
                    // insert to center
                    if (e.compareTo(root.getSecondData()) < 0) {
                        newNode = insertHelp(
                                root1.getCenterChild(), e);
                        if (newNode == root1.getCenterChild()) {
                            return root;
                        }
                        else {
                            InternalNode new1 = (InternalNode)newNode;
                            InternalNode split = new InternalNode(
                                    null, null, new1.getCenterChild(),
                                    root1.getRightChild(), null);
                            split.setFirstData(min(split.getCenterChild()));
                            root1.setRightChild(null);
                            root1.setSecondData(null);
                            root1.setCenterChild(
                                    new1.getLeftChild());
                            root1.setFirstData(
                                    min(root1.getCenterChild()));
                            InternalNode parent = new InternalNode(
                                    min(split), null, root, split, null);
                            return parent;
                        }
                    }
                    else { // insert to right
                        newNode = insertHelp(
                                root1.getRightChild(), e);
                        if (newNode == root1.getRightChild()) {
                            return root;
                        }
                        else {
                            InternalNode new1 = (InternalNode)newNode;
                            InternalNode split = new InternalNode(
                                    null, null, new1.getLeftChild(),
                                    new1.getCenterChild(), null);
                            split.setFirstData(min(split.getCenterChild()));
                            root1.setRightChild(null);
                            root1.setSecondData(null);
                            InternalNode parent = new InternalNode(
                                    min(split), null, root, split, null);
                            return parent;
                        }
                    }
                }

            }
        }
    }
    /**
     * find the minimum value in the tree
     * @param n the root of the tree
     * @return the minimum value in the tree
     */
    private KVPair min(TreeNode n) {
        return minHelp(n).getFirstData();
    }
    /**
     * Recursive function help find the minimum value
     * @param n the root of the tree/subtree
     * @return the node contains the minimum value or
     *              its parent
     */
    private TreeNode minHelp(TreeNode n) {
        if (n.isLeaf()) {
            return n;
        }
        else {
            return minHelp(((InternalNode)n).getLeftChild());
        }
    }
    /**
     * Check if the node is LeafNode
     * @return false
     */
    @Override
    public boolean isLeaf() {
        return false;
    }
    /**
     * update the data in the node when its children changed
     */
    private void update() {
        if (center == null && right == null) {
            this.setFirstData(null);
            this.setSecondData(null);
        }
        else if (right == null) {
            this.setSecondData(null);
            this.setFirstData(min(center));
        }
        else {
            this.setFirstData(min(center));
            this.setSecondData(min(right));
        }
    }
    /**
     * delete an entry
     * @param entry the entry to delete
     * @return the new root after delete
     */
    @Override
    public TreeNode delete(KVPair entry) {
        TreeNode root = deleteHelp(this, entry);
        if (root.isLeaf()) {
            return root;
        }
        else if (((InternalNode)root).getFirstData() != null) {
            return root;
        }
        else {
            return ((InternalNode)root).getLeftChild();
        }
    }
    /**
     * recursive method helping delete an entry
     * @param node root of the tree/subtree
     * @param entry the entry to delete
     * @return the node just delete from
     */
    private TreeNode deleteHelp(TreeNode node, KVPair entry) {
        if (node.isLeaf()) {
            return ((LeafNode)node).delete(entry);
        }
        else { // if n is an internal node
            InternalNode n1 = (InternalNode)node;
            TreeNode newNode;
            if (entry.compareTo(n1.getFirstData()) < 0) {
                newNode = deleteHelp(n1.getLeftChild(), entry);
                if (newNode.isLeaf()) {
                    // if newNode is empty
                    if (newNode.getFirstData() == null) {
                        LeafNode c = (LeafNode)n1.getCenterChild();
                        // borrow value from center child
                        if (c.getSecondData() != null) {
                            newNode.setFirstData(c.getFirstData());
                            c.setFirstData(c.getSecondData());
                            c.setSecondData(null);
                        }
                        // cannot borrow
                        else {
                            n1.setLeftChild(c);
                            n1.setCenterChild(n1.getRightChild());
                            n1.setRightChild(null);
                            updateLink((LeafNode)newNode);
                        }
                    }
                    n1.update();
                    return n1;
                }
                else { // newNode is an internal node
                    InternalNode new1 = (InternalNode)newNode;
                    if (new1.getLeftChild() == null &&
                            new1.getCenterChild() != null) {
                        new1.setLeftChild(new1.getCenterChild());
                        new1.setCenterChild(null);
                        new1.update();
                    }
                    // newNode is empty
                    if (newNode.getFirstData() == null) {

                        InternalNode c =
                                (InternalNode)n1.getCenterChild();
                        // borrow a child from center sibling
                        if (c.getRightChild() != null) {
                            new1.setCenterChild(c.getLeftChild());
                            new1.update();
                            c.setLeftChild(c.getCenterChild());
                            c.setCenterChild(c.getRightChild());
                            c.setRightChild(null);
                            c.update();
                        }
                        // cannot borrow
                        else {
                            new1.setCenterChild(c.getLeftChild());
                            new1.setRightChild(c.getCenterChild());
                            new1.update();
                            n1.setCenterChild(n1.getRightChild());
                            n1.setRightChild(null);
                        }
                        //n1.update();
                    }
                    n1.update();
                    return n1;
                }
            }
            else { // e is greater than the first data
                // no right child, recursive call center child
                if (n1.getRightChild() == null) {
                    newNode = deleteHelp(n1.getCenterChild(), entry);
                    if (newNode.isLeaf()) {
                        // if newNode is empty
                        if (newNode.getFirstData() == null) {
                            LeafNode l = (LeafNode)n1.getLeftChild();
                            // borrow a value from left sibling
                            if (l.getSecondData() != null) {
                                newNode.setFirstData(l.getSecondData());
                                l.setSecondData(null);
                            }
                            // cannot borrow
                            else {
                                //l.setSecondData(newNode.getFirstData());
                                l.setSecondData(null);
                                n1.setCenterChild(null);
                                updateLink((LeafNode)newNode);
                            }
                        }
                        n1.update();
                        return n1;
                    }
                    else { // newNode is an internal node
                        // newNode is empty
                        if (newNode.getFirstData() == null) {
                            InternalNode new1 = (InternalNode)newNode;
                            InternalNode l = (InternalNode)n1.getLeftChild();
                            // borrow a child from left sibling
                            if (l.getRightChild() != null) {
                                new1.setCenterChild(new1.getLeftChild());
                                new1.setLeftChild(l.getRightChild());
                                new1.update();
                                l.setRightChild(null);
                                l.update();
                            }
                            // cannot borrow
                            else {
                                l.setRightChild(new1.getLeftChild());
                                l.update();
                                n1.setCenterChild(null);
                            }
                            //n1.update();
                        }
                        n1.update();
                        return n1;
                    }
                }
                else { // n1 has 3 children
                    // recursive call center child
                    if (entry.compareTo(n1.getSecondData()) < 0) {
                        newNode = deleteHelp(n1.getCenterChild(), entry);
                        if (newNode.isLeaf()) {
                         // if newNode is empty
                            if (newNode.getFirstData() == null) {
                                LeafNode l =
                                        (LeafNode)n1.getLeftChild();
                                LeafNode r =
                                        (LeafNode)n1.getRightChild();
                                // borrow a value from left
                                if (l.getSecondData() != null) {
                                    newNode.setFirstData(l.getSecondData());
                                    l.setSecondData(null);
                                }
                                // borrow a value from right
                                else if (r.getSecondData() != null) {
                                    newNode.setFirstData(r.getFirstData());
                                    r.setFirstData(r.getSecondData());
                                    r.setSecondData(null);
                                }
                                // cannot borrow
                                else {
                                    l.setSecondData(null);
                                    n1.setCenterChild(n1.getRightChild());
                                    n1.setRightChild(null);
                                    updateLink((LeafNode)newNode);
                                }
                            }
                            n1.update();
                            return n1;
                        }
                        else { // newNode is an internal node
                            // newNode is empty
                            if (newNode.getFirstData() == null) {
                                InternalNode new1 =
                                        (InternalNode)newNode;
                                InternalNode l =
                                        (InternalNode)n1.getLeftChild();
                                InternalNode r =
                                        (InternalNode)n1.getRightChild();
                                // borrow a child from left
                                if (l.getRightChild() != null) {
                                    new1.setCenterChild(new1.getLeftChild());
                                    new1.setLeftChild(l.getRightChild());
                                    new1.update();
                                    l.setRightChild(null);
                                    l.update();
                                }
                                // borrow a child from right
                                else if (r.getRightChild() != null) {
                                    new1.setCenterChild(r.getLeftChild());
                                    new1.update();
                                    r.setLeftChild(r.getCenterChild());
                                    r.setCenterChild(r.getRightChild());
                                    r.setRightChild(null);
                                    r.update();
                                }
                                // cannot borrow
                                else {
                                    l.setRightChild(new1.getLeftChild());
                                    l.update();
                                    n1.setCenterChild(n1.getRightChild());
                                    n1.setRightChild(null);
                                }
                                //n1.update();
                            }
                            n1.update();
                            return n1;
                        }
                    }
                    else { // recursive call right child
                        newNode = deleteHelp(n1.getRightChild(), entry);
                        if (newNode.isLeaf()) {
                            // if newNode is empty
                            if (newNode.getFirstData() == null) {
                                LeafNode c =
                                        (LeafNode)n1.getCenterChild();
                                // borrow a value from center
                                if (c.getSecondData() != null) {
                                    newNode.setFirstData(c.getSecondData());
                                    c.setSecondData(null);
                                }
                                // cannot borrow
                                else {
                                    n1.setRightChild(null);
                                    updateLink((LeafNode)newNode);
                                }
                            }
                            n1.update();
                            return n1;
                        }
                        else { // newNode is an internal node
                            // if newNode is empty
                            if (newNode.getFirstData() == null) {
                                InternalNode new1 =
                                        (InternalNode)newNode;
                                InternalNode c =
                                        (InternalNode)n1.getCenterChild();
                                // borrow a child from center
                                if (c.getRightChild() != null) {
                                    new1.setCenterChild(new1.getLeftChild());
                                    new1.setLeftChild(c.getRightChild());
                                    new1.update();
                                    c.setRightChild(null);
                                    c.update();
                                }
                                // cannot borrow
                                else {
                                    c.setRightChild(new1.getLeftChild());
                                    c.update();
                                    n1.setRightChild(null);
                                }
                                //n1.update();
                            }
                            n1.update();
                            return n1;
                        }
                    }
                }
            }

        }
    }
    /**
     * update the link of leaf node in the linked list
     * @param n the node to update
     */
    private void updateLink(LeafNode n) {
        if (n.previous() != null && n.next() != null) {
            n.previous().setNext(n.next());
            n.next().setPrevious(n.previous());
        }
        else if (n.previous() != null) {
            n.previous().setNext(null);
        }
        else if (n.next() != null) {
            n.next().setPrevious(null);
        }
    }

}
