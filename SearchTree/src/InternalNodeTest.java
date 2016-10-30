import student.TestCase;

/**
 * test internal node class
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/08/2016
 */
public class InternalNodeTest extends TestCase {
    private KVPair kv1;
    private KVPair kv2;
    private KVPair kv3;

    /**
     * set up the test
     */
    @Override
    public void setUp() {
        kv1 = new KVPair(new Handle(1), new Handle(0));
        kv2 = new KVPair(new Handle(2), new Handle(2));
        kv3 = new KVPair(new Handle(3), new Handle(0));
    }

    /**
     * test every method in node class
     */
    public void testEverything() {
        InternalNode in = new InternalNode();
        in.setFirstData(kv1);
        assertEquals(kv1, in.getFirstData());
        InternalNode three = new InternalNode();
        three.setFirstData(kv3);
        InternalNode one = new InternalNode();
        one.setFirstData(kv1);
        InternalNode two = new InternalNode();
        two.setFirstData(kv2);
        in.setSecondData(kv2);
        assertEquals(kv2, in.getSecondData());
        in.setLeftChild(one);
        assertEquals(kv1, in.getLeftChild().getFirstData());
        in.setCenterChild(two);
        assertEquals(kv2, in.getCenterChild().getFirstData());
        in.setRightChild(three);
        assertEquals(kv3, in.getRightChild().getFirstData());
    }
    /**
     * test the insert method
     */
    public void testInsertAndDelete() {
        KVPair kv52 = new KVPair(new Handle(52), new Handle(0));
        KVPair kv46 = new KVPair(new Handle(46), new Handle(0));
        KVPair kv33 = new KVPair(new Handle(33), new Handle(0));
        KVPair kv22 = new KVPair(new Handle(22), new Handle(0));
        KVPair kv71 = new KVPair(new Handle(71), new Handle(0));
        KVPair kv65 = new KVPair(new Handle(65), new Handle(0));
        TreeNode root = new LeafNode();
        root = root.insert(kv52);
        assertEquals(root.printNode(0), "52 0");
        root = root.insert(kv46);
        assertEquals(root.printNode(0), "46 0 52 0");
        root = root.insert(kv33);
        assertEquals(root.printNode(0), "46 0");
        InternalNode root1 = (InternalNode)root;
        assertEquals(root1.getLeftChild().printNode(0), "33 0");
        assertEquals(root1.getCenterChild().printNode(0), "46 0 52 0");
        root = root.insert(kv22);
        root1 = (InternalNode)root;
        assertEquals(root.printNode(0), "46 0");
        assertEquals(root1.getLeftChild().printNode(0), "22 0 33 0");
        assertEquals(root1.getCenterChild().printNode(0), "46 0 52 0");
        root = root.insert(kv71);
        root1 = (InternalNode)root;
        assertEquals(root.printNode(0), "46 0 52 0");
        assertEquals(root1.getLeftChild().printNode(0), "22 0 33 0");
        assertEquals(root1.getCenterChild().printNode(0), "46 0");
        assertEquals(root1.getRightChild().printNode(0), "52 0 71 0");
        root = root.insert(kv65);
        root1 = (InternalNode)root;
        assertEquals(root.printNode(0), "52 0");
        assertEquals(root1.getLeftChild().printNode(0), "46 0");
        assertEquals(root1.getCenterChild().printNode(0), "65 0");
        assertNull(root1.getRightChild());
        InternalNode left = (InternalNode)root1.getLeftChild();
        InternalNode center = (InternalNode)root1.getCenterChild();
        assertEquals(left.getLeftChild().printNode(0), "22 0 33 0");
        assertEquals(left.getCenterChild().printNode(0), "46 0");
        assertEquals(center.getLeftChild().printNode(0), "52 0");
        assertEquals(center.getCenterChild().printNode(0), "65 0 71 0");

        root = root.delete(kv33);
        assertEquals(left.getLeftChild().printNode(0), "22 0");
        root = root.delete(kv65);
        assertEquals(root.printNode(0), "52 0");
        assertEquals(center.getCenterChild().printNode(0), "71 0");
        root = root.delete(kv52);
        root1 = (InternalNode)root;
        assertEquals(root.printNode(0), "46 0 71 0");
        assertEquals(root1.getLeftChild().printNode(0), "22 0");
        assertEquals(root1.getCenterChild().printNode(0), "46 0");
        assertEquals(root1.getRightChild().printNode(0), "71 0");
        root = root.delete(kv46);
        assertEquals(root.printNode(0), "71 0");
        assertEquals(root1.getLeftChild().printNode(0), "22 0");
        assertEquals(root1.getCenterChild().printNode(0), "71 0");
        root = root.delete(kv71);
        assertEquals(root.printNode(0), "22 0");
        assertTrue(root.isLeaf());
        root = root.delete(kv22);
        assertNull(root.getFirstData());
    }

}
