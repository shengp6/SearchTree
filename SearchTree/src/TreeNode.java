/**
 * Node class
 * Some abstract method & some implemented method
 * Used to make Leaf Node and Internal Node
 * @author {Sheng Peng <shengp6> & Wenjia Song <wenjia7>}
 * @version {09/27/2016}
 *
 */
public abstract class TreeNode {
    private KVPair data;
    private KVPair data2;

    /**
     * Accessor method
     * The first data
     * @return The first data in the node
     */
    public KVPair getFirstData() {
        return data;
    }

    /**
     * Accessor method
     * The second data
     * @return The second data in the node
     */
    public KVPair getSecondData() {
        return data2;
    }

    /**
     * Set method
     * Set the first data
     * @param entry The first data
     */
    public void setFirstData(KVPair entry) {
        this.data = entry;
    }

    /**
     * Set method
     * Set the second data
     * @param entry The second data
     */
    public void setSecondData(KVPair entry) {
        this.data2 = entry;
    }

    /**
     * Abstract method
     * insert an entry to the node
     * @param entry The entry client want to insert
     * @return The parent of the inserted node if split
     *             Itself if not split
     */
    public abstract TreeNode insert(KVPair entry);

    /**
     * Abstract method
     * Check if it is a leaf node or not
     * @return true when it's leaf
     */
    public abstract boolean isLeaf();

    /**
     * Print the data in the node
     * @param degree The degree of this node in the tree
     * @return The string contains the data of this node
     */
    public String printNode(int degree) {
        if (data == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (data != null && data2 == null) {
            for (int i = 0; i < degree; i++) {
                sb.append("  ");
            }
            sb.append(data.toString());
            return sb.toString();
        }
        else {
            for (int i = 0; i < degree; i++) {
                sb.append("  ");
            }
            sb.append(data.toString() + " " + data2.toString());
            return sb.toString();
        }
    }
    /**
     * delete an entry
     * @param e the entry to delete
     * @return the node just delete from
     */
    public abstract TreeNode delete(KVPair e);


}
