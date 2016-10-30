/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 * This project allows client to insert artist names and songs
 * Can delete one single record
 * Can remove all the songs of one artist by using remove command
 * Can list all the songs by one artist by using list command
 * The same for the artists
 * @author {Sheng Peng <shengp6> & Wenjia Song <wenjia7>}
 * @version {09/26/2016}
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class SearchTree {
    /**
     * @param args
     *      Command line parameters.
     */
    public static void main(String[] args) {
        new CommandProcessor(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]), args[2]);
    }
}
