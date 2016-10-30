/**
 * The class of free blocks
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/06/2016
 *
 */
public class FreeBlock {
    private int startPos;
    private int length;
    /**
     * Constructor
     * @param startPos The start position of free block
     * @param length The length of the free block
     */
    public FreeBlock(int startPos, int length) {
        this.startPos = startPos;
        this.length = length;
    }
    /**
     * Accessor method
     * @return startPos The start position of free block
     */
    public int getStartPos() {
        return startPos;
    }
    /**
     * Set the start position of free block
     * @param startPos The start position of free block
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
    /**
     * Accessor method
     * @return length The length of the free block
     */
    public int getLength() {
        return length;
    }
    /**
     * Set the length of the free block
     * @param length The length of the free block
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * Reset the length by adding a number
     * @param num Add num to the length
     */
    public void addLength(int num) {
        this.length += num;
    }
    /**
     * increase the start position by a number
     * @param num Add num to the startPos
     */
    public void increaseStartPos(int num) {
        this.startPos += num;
    }
    
}
