import student.TestCase;
/**
 * Test the methods in memory pool
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/06/2016
 */
public class MemManagerTest extends TestCase {
    private MemManager mp1;
    /**
     * set up the test
     */
    @Override
    public void setUp() {
        mp1 = new MemManager(32);
    }
    /**
     * test all the public methods in Memory Pool
     */
    public void testAll() {
        byte[] byte1 = "Blind Lemon Jefferson".getBytes();
        Handle handle1 = mp1.insert(byte1);
        assertEquals(mp1.printBlocks(), "(23,9)");
        assertEquals(mp1.size(), 32);
        assertEquals(mp1.resized(), 0);
        byte[] byte2 = "Ma Rainey".getBytes();
        Handle handle2 = mp1.insert(byte2);
        assertEquals(mp1.resized(), 1);
        byte[] byte3 = "Charley Patton".getBytes();
        Handle handle3 = mp1.insert(byte3);
        String longStr = "abcdefghijklmnopqrstuvwxyz"
                + "abcdefghijklmnopqrstuvwxyz";
        byte[] byte4 = longStr.getBytes();
        Handle handle4 = mp1.insert(byte4);
        assertEquals(mp1.printExpandMemory(),
                "Memory pool expanded to be 96 bytes.\n"
                + "Memory pool expanded to be 128 bytes.\n");
        assertEquals(mp1.size(), 128);
        assertEquals(mp1.resized(), 2);
        byte[] entry = new byte[100];
        assertEquals(mp1.getEntry(entry, handle1), 21);
        assertEquals(mp1.getEntry(entry, handle2), 9);
        assertEquals(mp1.getEntry(entry, handle3), 14);
        mp1.remove(handle1);
        mp1.remove(handle2);
        mp1.remove(handle3);
        mp1.remove(handle4);
        assertEquals(mp1.printBlocks(), "(0,128)");

    }
}
