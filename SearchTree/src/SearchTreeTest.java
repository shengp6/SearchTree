import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import student.TestCase;

/**
  * Test the main method, the entire project
  * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
  * @version 10/10/2016
 */
public class SearchTreeTest extends TestCase {
    /**
     * set up the test
     * empty
     */
    @Override
    public void setUp() {
        //nothing here
    }
    /**
     * test the main method with sample input and output
     */
    public void testMain() {
        SearchTree tree = new SearchTree();
        assertNotNull(tree);
        Scanner output = null;
        String[] args = new String[3];
        args[0] = "10";
        args[1] = "32";
        args[2] = "P2_Input1_Sample.txt";
        SearchTree.main(args);
        try {
            output = new Scanner(new File("P2_Output1_Sample.txt"));
        }
        catch (FileNotFoundException e) {
            System.out.println("File is not found");
        }
        StringBuilder sb = new StringBuilder();
        while (output.hasNextLine()) {
            sb.append(output.nextLine() + "\n") ;
        }
        String standardOutput = sb.toString();
        assertFuzzyEquals(systemOut().getHistory(), standardOutput);
    }
}
