import student.TestCase;

/**
 * Test the method in free block list
 * @author Sheng Peng<shengp6> && Wenjia Song<wenjia7>
 * @version 09/08/2016
 */
public class FreeBlockListTest extends TestCase {
    private FreeBlockList list;    
    /**
     * set up the test
     */
    public void setUp() {
        list = new FreeBlockList(32);        
    }
    /**
     * test all the methods in free block list class
     */
    public void testAll() {
        list.addBlock(new FreeBlock(32, 5));        
        assertEquals(list.printBlocks(), "(0,37)");
        list.addBlock(new FreeBlock(59, 10));
        assertEquals(list.printBlocks(), "(0,37) -> (59,10)");
        list.addBlock(new FreeBlock(39, 3));
        assertEquals(list.printBlocks(), "(0,37) -> (39,3) -> (59,10)");
        assertEquals(list.checkFreeBlock(9), 2);
        assertEquals(list.checkFreeBlock(3), 1);
        assertEquals(list.checkFreeBlock(50), -1);
        list.addBlock(new FreeBlock(46, 5));
        assertEquals(list.printBlocks(),
                "(0,37) -> (39,3) -> (46,5) -> (59,10)");
        list.shrinkBlock(2, 5);
        list.shrinkBlock(1, 3);
        assertEquals(list.printBlocks(), "(0,37) -> (59,10)");
        list.shrinkBlock(0, 32);
        assertEquals(list.printBlocks(), "(32,5) -> (59,10)");
        list.shrinkBlock(1, 8);
        assertEquals(list.printBlocks(), "(32,5) -> (67,2)");
        list.shrinkBlock(0, 5);
        list.shrinkBlock(0, 2);
        assertNull(list.printBlocks());
        list.addBlock(new FreeBlock(0, 32));
        assertEquals(list.printBlocks(), "(0,32)");
        list.addBlock(new FreeBlock(42, 5));
        list.addBlock(new FreeBlock(39, 3));
        assertEquals(list.printBlocks(), "(0,32) -> (39,8)");        
    }

}
