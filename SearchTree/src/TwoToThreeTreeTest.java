import student.TestCase;

/**
 * test the methods in TwoToThreeTree
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/08/2016
 */
public class TwoToThreeTreeTest extends TestCase {
    private TwoToThreeTree tree1;
    private KVPair kv52;
    private KVPair kv46;
    private KVPair kv33;
    private KVPair kv22;
    private KVPair kv71;
    private KVPair kv65;
    /**
     * set up the test
     */
    @Override
    public void setUp() {
        kv52 = new KVPair(new Handle(52), new Handle(0));
        kv46 = new KVPair(new Handle(46), new Handle(0));
        kv33 = new KVPair(new Handle(33), new Handle(0));
        kv22 = new KVPair(new Handle(22), new Handle(0));
        kv71 = new KVPair(new Handle(71), new Handle(0));
        kv65 = new KVPair(new Handle(65), new Handle(0));
        tree1 = new TwoToThreeTree();
        tree1.insert(kv52);
        tree1.insert(kv46);
        tree1.insert(kv33);
        tree1.insert(kv22);
        tree1.insert(kv71);
        tree1.insert(kv65);
    }
    /**
     * test the insert method
     */
    public void testInsert() {
        String str = "52 0\n  46 0\n    22 0 33 0\n    "
                + "46 0\n  65 0\n    52 0\n    65 0 71 0\n";
        assertEquals(str, tree1.printTree());
        KVPair kv15 = new KVPair(new Handle(15), new Handle(0));
        KVPair kv47 = new KVPair(new Handle(47), new Handle(0));
        KVPair kv51 = new KVPair(new Handle(51), new Handle(0));
        KVPair kv70 = new KVPair(new Handle(70), new Handle(0));
        tree1.insert(kv15);
        System.out.println("First Tree");
        System.out.println(tree1.printTree());
        tree1.insert(kv47);
        System.out.println("Second Tree");
        System.out.println(tree1.printTree());
        tree1.insert(kv51);
        System.out.println("Third Tree");
        System.out.println(tree1.printTree());
        tree1.insert(kv70);
        System.out.println("Fourth Tree");
        System.out.println(tree1.printTree());

    }
    /**
     * test the search method
     */
    public void testSearch() {
        KVPair kv44 = new KVPair(new Handle(44), new Handle(0));
        assertNull(tree1.search(kv44));
        LeafNode test = tree1.search(kv33);
        assertEquals("22 0 33 0", test.printNode(0));
        test = tree1.search(kv22);
        assertEquals("22 0 33 0", test.printNode(0));
        test = tree1.search(kv46);
        assertEquals("46 0", test.printNode(0));
        test = tree1.search(kv52);
        assertEquals("52 0", test.printNode(0));
        test = tree1.search(kv65);
        assertEquals("65 0 71 0", test.printNode(0));
        test = tree1.search(kv71);
        assertEquals("65 0 71 0", test.printNode(0));

    }
    /**
     * test delete
     */
    public void testDelete() {
        KVPair kv15 = new KVPair(new Handle(15), new Handle(0));
        KVPair kv47 = new KVPair(new Handle(47), new Handle(0));
        KVPair kv51 = new KVPair(new Handle(51), new Handle(0));
        KVPair kv70 = new KVPair(new Handle(70), new Handle(0));
        KVPair kv89 = new KVPair(new Handle(89), new Handle(0));
        TwoToThreeTree t = new TwoToThreeTree();
        t.insert(kv15);
        t.insert(kv22);
        t.insert(kv51);
        t.insert(kv65);
        t.insert(kv71);
        t.insert(kv89);
        t.insert(kv33);
        t.insert(kv46);
        t.insert(kv47);
        t.insert(kv52);
        t.insert(kv70);
        assertEquals(t.printTree(), "33 0 51 0\n  22 0\n    15 0\n"
                + "    22 0\n  46 0\n    33 0\n    "
                + "46 0 47 0\n  65 0 71 0\n    51 0 52 0\n"
                + "    65 0 70 0\n    71 0 89 0\n");
        t.delete(kv51);
        t.delete(kv70);
        assertEquals(t.printTree(), "33 0 52 0\n  22 0\n    15 0\n"
                + "    22 0\n  46 0\n    33 0\n    "
                + "46 0 47 0\n  65 0 71 0\n    52 0\n"
                + "    65 0\n    71 0 89 0\n");
        t.delete(kv65);
        assertEquals(t.printTree(), "33 0 52 0\n  22 0\n    15 0\n"
                + "    22 0\n  46 0\n    33 0\n    "
                + "46 0 47 0\n  71 0 89 0\n    52 0\n"
                + "    71 0\n    89 0\n");
        t.delete(kv71);
        assertEquals(t.printTree(), "33 0 52 0\n  22 0\n    15 0\n"
                + "    22 0\n  46 0\n    33 0\n    "
                + "46 0 47 0\n  89 0\n    52 0\n"
                + "    89 0\n");
        t.delete(kv89);
        t.delete(kv15);
        t.delete(kv33);
        assertEquals(t.printTree(), "46 0 52 0\n  22 0\n  46 0 47 0\n"
                + "  52 0\n");
        t.delete(kv46);
        t.delete(kv52);
        t.delete(kv47);
        assertEquals(t.printTree(), "22 0\n");
        t.delete(kv22);
        assertEquals(t.printTree(), "null\n");
    }
    /**
     * test complicated delete after
     * a large number of insert
     */
    public void testMoreDelete() {
        TwoToThreeTree tree = new TwoToThreeTree();
        KVPair k0 = new KVPair(new Handle(0), new Handle(14));
        KVPair k14 = new KVPair(new Handle(14), new Handle(0));
        KVPair k28 = new KVPair(new Handle(28), new Handle(41));
        KVPair k41 = new KVPair(new Handle(41), new Handle(28));
        KVPair k66 = new KVPair(new Handle(66), new Handle(77));
        KVPair k77 = new KVPair(new Handle(77), new Handle(66));
        KVPair k96 = new KVPair(new Handle(96), new Handle(110));
        KVPair k110 = new KVPair(new Handle(110), new Handle(96));
        KVPair k121 = new KVPair(new Handle(121), new Handle(137));
        KVPair k137 = new KVPair(new Handle(137), new Handle(121));
        KVPair k146 = new KVPair(new Handle(146), new Handle(159));
        KVPair k159 = new KVPair(new Handle(159), new Handle(146));
        KVPair k176 = new KVPair(new Handle(176), new Handle(192));
        KVPair k192 = new KVPair(new Handle(192), new Handle(176));
        KVPair k207 = new KVPair(new Handle(207), new Handle(224));
        KVPair k224 = new KVPair(new Handle(224), new Handle(207));
        tree.insert(k0);
        tree.insert(k14);
        tree.insert(k28);
        tree.insert(k41);
        tree.insert(k66);
        tree.insert(k77);
        tree.insert(k96);
        tree.insert(k110);
        tree.insert(k121);
        tree.insert(k137);
        tree.insert(k146);
        tree.insert(k159);
        tree.insert(k176);
        tree.insert(k192);
        tree.insert(k207);
        tree.insert(k224);
        System.out.println("Web Cat tree");
        System.out.println(tree.printTree());
        tree.delete(k66);
        System.out.println("Delete 66");
        System.out.println(tree.printTree());
        tree.delete(k77);
        String s1 = "96 110 146 159\n  28 41\n    14 0\n      0 14\n"
                + "      14 0\n    41 28\n      28 41\n      41 28\n"
                + "  121 137\n    110 96\n      96 110\n      110 96\n"
                + "    137 121\n      121 137\n      137 121\n"
                + "  176 192\n    159 146\n      146 159\n      159 146\n"
                + "    192 176 207 224\n      176 192\n      192 176\n"
                + "      207 224 224 207\n";
        assertEquals(tree.printTree(), s1);
    }
}
