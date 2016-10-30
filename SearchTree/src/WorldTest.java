import student.TestCase;
/**
 * Test the methods in world
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 10/05/2016
 */
public class WorldTest extends TestCase {
    private World world;

    /**
     * set up the test
     */
    @Override
    public void setUp() {
        world = new World(100, 32);
    }


    /**
     * test the special cases which were not
     * happened in standard input & output
     */
    public void testSpecialCase() {
        String[] name1 = {"Bukka White", "Bukka White's first song"};
        world.insert(name1);
        String[] name2 = {"Bukka White", "Bukka White's second song"};
        world.insert(name2);
        String[] name3 = {"Bukka White", "Bukka White's third song"};
        world.insert(name3);
        String[] name4 = {"Bukka White", "Bukka White's fourth song"};
        world.insert(name4);
        String[] name5 = {"Another Bukka White", "Bukka White's first song"};
        String[] name6 = {"Another Bukka White", "Bukka White's second song"};
        String[] name7 = {"Another Bukka White", "Bukka White's third song"};
        world.insert(name5);
        world.insert(name6);
        world.insert(name7);
        String[] str = {"NineBytes", "SevenBy"};
        world.insert(str);
        world.printBlock();
        String output = "(160,0)\n";
        String myOutput = systemOut().getHistory();
        String lastLine = myOutput.substring(myOutput.length() - 8);
        assertEquals(lastLine, output);

    }

}
