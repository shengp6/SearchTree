import student.TestCase;
/**
 * Test the methods in FreeBlock class
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/06/2016
 *
 */
public class FreeBlockTest extends TestCase {
    private FreeBlock fb1;
    /**
     * Set up for test
     */
    public void setUp() {
        fb1 = new FreeBlock(0, 10);
    }
    /**
     * test the accessor and set method
     */
    public void testAll() {
        assertEquals(fb1.getLength(), 10);
        assertEquals(fb1.getStartPos(), 0);        
        fb1.setLength(5);
        fb1.setStartPos(4);       
        assertEquals(fb1.getLength(), 5);
        assertEquals(fb1.getStartPos(), 4);  
        fb1.addLength(1);
        assertEquals(fb1.getLength(), 6);
        fb1.increaseStartPos(1);
        assertEquals(fb1.getStartPos(), 5);
    }
}
