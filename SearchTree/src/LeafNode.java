/**
 * leaf node class
 * a leaf node only has two values stored in it
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/27/2016
 */

public class LeafNode extends TreeNode {
    private LeafNode previous;
    private LeafNode next;
    /**
     * Constructor with no data
     */
    public LeafNode() {
        //create an empty leaf node
    }
    /**
     * Create a new LeafNode
     * @param firstData the first data stored in the node
     * @param secondData the second data stored in the node
     * @param previous the previous node linked to this node
     * @param next the next node linked to this node
     */
    public LeafNode(KVPair firstData, KVPair secondData,
            LeafNode previous, LeafNode next) {
        this.setFirstData(firstData);
        this.setSecondData(secondData);
        this.previous = previous;
        this.next = next;

    }


    /**
     * insert an entry to the node
     * @param entry the entry to insert
     * @return the node it self if is does not split,
     *          the parent of this node if it splits
     */
    @Override
    public TreeNode insert(KVPair entry) {
        if (this.getFirstData() == null) {
            this.setFirstData(entry);
            return this;
        }
        else if (this.getSecondData() == null) {
            if (this.getFirstData().compareTo(entry) < 0) {
                this.setSecondData(entry);
                return this;
            }
            else {
                this.setSecondData(this.getFirstData());
                this.setFirstData(entry);
                return this;
            }
        }
        else { // split
            LeafNode newNode;
            if (entry.compareTo(this.getFirstData()) < 0) {
                newNode = new LeafNode(this.getFirstData(),
                        this.getSecondData(), this, this.next);
                this.setFirstData(entry);
                this.setSecondData(null);
            }
            else if (entry.compareTo(this.getSecondData()) > 0) {
                newNode = new LeafNode(this.getSecondData(),
                        entry, this, this.next);
                this.setSecondData(null);
            }
            else {
                newNode = new LeafNode(entry,
                        this.getSecondData(), this, this.next);
                this.setSecondData(null);
            }
            if (this.next != null) {
                this.next.setPrevious(newNode);
            }
            this.setNext(newNode);
            InternalNode parent = new InternalNode(
                    newNode.getFirstData(), null,
                    this, newNode, null);
//            //
//            System.out.println("Previous node");
//            LeafNode pre = this.previous;
//            LeafNode nex = this.next;
//            while (pre != null) {
//                if (pre.getSecondData() != null) {
//                    System.out.print(pre.getSecondData().toString() + " ");
//                }
//                System.out.print(pre.getFirstData().toString() + " ");
//                pre = pre.previous;
//            }
//            System.out.println("\n"+this.printNode(0));
//            System.out.println("Next node");
//            while (nex != null) {
//                System.out.print(nex.printNode(0) + " ");
//                nex = nex.next;
//            }
//            System.out.println("");
//            //
            return parent;
        }
    }
    /**
     * Sets the node after this node
     *
     * @param n
     *            the node after this one
     */
    public void setNext(LeafNode n) {
        next = n;
    }

    /**
     * Sets the node before this one
     *
     * @param n
     *            the node before this one
     */
    public void setPrevious(LeafNode n) {
        previous = n;
    }

    /**
     * Gets the next node
     *
     * @return the next node
     */
    public LeafNode next() {
        return next;
    }

    /**
     * Gets the node before this one
     *
     * @return the node before this one
     */
    public LeafNode previous() {
        return previous;
    }
    /**
     * Check if this node is a LeafNode
     * @return true;
     */
    @Override
    public boolean isLeaf() {
        return true;
    }
    /**
     * Search if the tree contains one entry
     * @param entry the entry to search
     * @return the node itself if the entry was found,
     *          null if not
     */
    public LeafNode search(KVPair entry) {
        if (this.getFirstData() == null) {
            return null;
        }
        if (this.getFirstData().compareTo(entry) == 0) {
            return this;
        }
        if (this.getSecondData() == null) {
            return null;
        }
        if (this.getSecondData().compareTo(entry) == 0) {
            return this;
        }
        return null;
    }
    /**
     * Search if the tree contains one entry (Key)
     * @param entry the entry to search
     * @return the first match node if the entry was found,
     *          null if not
     */
    public LeafNode search(Handle entry) {
        if (this.getFirstData() == null) {
            return null;
        }
        if (this.getFirstData().compareTo(entry) == 0) {
            return this;
        }
        if (this.getSecondData() == null) {
            return null;
        }
        if (this.getSecondData().compareTo(entry) == 0) {
            return this;
        }
        return null;
    }


    /**
     * delete an entry in the node
     * @param e the entry to delete
     */
    @Override
    public LeafNode delete(KVPair e) {
        if (e.compareTo(getFirstData()) == 0 &&
                this.getFirstData() != null) {
            this.setFirstData(this.getSecondData());
            this.setSecondData(null);
        }
        else if (this.getSecondData() != null &&
                e.compareTo(getSecondData()) == 0) {
            this.setSecondData(null);
        }
        return this;
    }

}
