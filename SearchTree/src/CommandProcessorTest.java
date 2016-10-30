import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import student.TestCase;
/**
  * test the method in command processor
  * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
  * @version 10/02/2016
 */
public class CommandProcessorTest extends TestCase {

    /**
     * Set up the test
     */
    @Override
    public void setUp() {
        //Nothing here
    }
    /**
     * test the class with standard input and output
     * from professor
     */
    public void testStandardOutput() {
        Scanner output = null;
        new CommandProcessor(10, 32, "P2_Input1_Sample.txt");
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
        assertEquals(systemOut().getHistory(), standardOutput);
    }

}
