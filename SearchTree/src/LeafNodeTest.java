import student.TestCase;

/**
 * test leaf node class
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/08/2016
 */
public class LeafNodeTest extends TestCase {
    private LeafNode ln1;
    private KVPair kv1;

    /**
     * set up the test
     */
    @Override
    public void setUp() {
        ln1 = new LeafNode();
        kv1 = new KVPair(new Handle(1), new Handle(10));
        ln1.insert(kv1);
    }

    /**
     * test accessor and set method in node class
     */
    public void testAccessorAndGet() {
        assertEquals(1, ln1.getFirstData().key().pos());
        ln1.setFirstData(new KVPair(new Handle(11), new Handle(110)));
        assertEquals(11, ln1.getFirstData().key().pos());
        ln1.setSecondData(new KVPair(new Handle(2), new Handle(20)));
        assertEquals(2, ln1.getSecondData().key().pos());
        LeafNode second = new LeafNode();
        LeafNode third = new LeafNode();
        second.setPrevious(ln1);
        second.setNext(third);
        assertEquals(second.next(), third);
        assertEquals(second.previous(), ln1);
    }
    /**
     * test insert method
     */
    public void testInsert() {
        // no split
        assertEquals(1, ln1.getFirstData().key().pos());
        KVPair kv2 = new KVPair(new Handle(2), new Handle(20));
        assertTrue(ln1.insert(kv2).equals(ln1));
        assertEquals(2, ln1.getSecondData().key().pos());
        ln1.setSecondData(null);
        KVPair kv0 = new KVPair(new Handle(0), new Handle(20));
        assertTrue(ln1.insert(kv0).equals(ln1));
        assertEquals(0, ln1.getFirstData().key().pos());
        assertEquals(1, ln1.getSecondData().key().pos());
        // split
        InternalNode ln2 =
                (InternalNode) ln1.insert(kv2);
        assertEquals(ln2.getFirstData().key().pos(), 1);
        assertEquals(ln2.getCenterChild().getFirstData().key().pos(), 1);
        assertEquals(ln1.getFirstData().key().pos(), 0);
        // link
        LeafNode c2 = (LeafNode)ln2.getCenterChild();
        InternalNode ln3 = (InternalNode)c2.insert(
                new KVPair(new Handle(3), new Handle(30)));
        assertEquals(ln3.getCenterChild().getFirstData().key().pos(), 2);
        assertEquals(ln1.previous(), null);
        assertEquals(ln1.next(), c2);
        assertEquals(c2.next(), ln3.getCenterChild());
        assertEquals(c2, ((LeafNode)ln3.getCenterChild()).previous());
    }
    /**
     * test search method
     */
    public void testSearch() {
        KVPair kv2 = new KVPair(new Handle(2), new Handle(20));
        ln1.insert(kv2);
        assertTrue(ln1.equals(ln1.search(kv1)));
        assertTrue(ln1.equals(ln1.search(kv2)));
        assertNull(ln1.search(new KVPair(new Handle(0), new Handle(20))));
    }
}
