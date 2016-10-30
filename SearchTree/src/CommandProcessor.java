import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Process the command
 * Print standard output
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/05/2016
 */
public class CommandProcessor {

    private World world;
    /**
     * Constructor
     * @param hashSize The size of hash tables
     * @param blockSize The initial size of the memory pool
     * @param fileName The input file
     */
    public CommandProcessor(int hashSize, int blockSize, String fileName) {
        world = new World(hashSize, blockSize);
        Scanner scan;
        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
                String command = scan.nextLine();
                parse(command);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * Parse a line of command, call methods in the world to
     * execute it
     * @param command A line of input command
     */
    private void parse(String command) {
        String content;

        //insert
        if (command.contains("insert")) {
            content = command.substring(7);
            String[] names = content.split("<SEP>");
            world.insert(names);
        }
        // print
        else if (command.contains("print")) {
            content = command.substring(6);
            if (content.contains("song")) {
                world.printSong();
            }
            else if (content.contains("artist")) {
                world.printArtist();
            }
            //print blocks
            else if (content.contains("block")) {
                world.printBlock();
            }
            //print 2 - 3 tree
            else {
                world.printTree();
            }
        }
        //remove
        else if (command.contains("remove")) {
            content = command.substring(7);
            //remove song
            if (content.contains("song")) {
                content = content.substring(5);
                world.removeSong(content);
            }
            //remove artist
            else {
                content = content.substring(7);
                world.removeArtist(content);
            }
        }
        //list
        else if (command.contains("list")) {
            content = command.substring(5);
            //list artist
            if (content.contains("artist")) {
                content = content.substring(7);
                world.listArtist(content);
            }
            //list song
            else {
                content = content.substring(5);
                world.listSong(content);
            }
        }
        //delete
        else {
            content = command.substring(7);
            String[] names = content.split("<SEP>");
            world.delete(names);
        }
    }
}
