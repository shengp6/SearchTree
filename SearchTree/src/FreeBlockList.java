/**
 * The double link list of free block
 * This class extends DLList
 * @author Sheng Peng<shengp6> && Wenjia Song<wenjia7>
 * @version 09/08/2016
 *
 */
public class FreeBlockList extends DLList<FreeBlock> {
    /**
     * Constructor, create a new free block list
     * Add one block to the list, which means the 
     * byte array is empty right now, so the free
     * block is the entire array
     * @param blockSize The initial size of the byte array
     */
    public FreeBlockList(int blockSize) {
        super();
        this.add(new FreeBlock(0, blockSize));
    }
    /**
     * Add one free block   
     * @param newBlock The block we want to add
     */
    public void addBlock(FreeBlock newBlock) {
        int listPos = 0; 
        if (!this.isEmpty()) {
            FreeBlock current = this.getLastEntry();
            if (current.getStartPos() < newBlock.getStartPos()) {
                listPos = this.size();
            }
            else {
                current = this.get(listPos);
                while (current.getStartPos() < newBlock.getStartPos() 
                        && listPos < this.size() - 1) {
                    current = this.get(++listPos);
                }
            } 
        }
        this.add(listPos, newBlock);
        this.update(listPos);
        //return listPos;
        
    }
    /**
     * Merge the adjacent blocks
     * Always keep the first block, and remove the second block
     * @param listPos The position in the list
     */
    private void merge(int listPos) {
        FreeBlock first = this.get(listPos);
        FreeBlock second = this.get(listPos + 1);
        first.addLength(second.getLength());
        this.remove(second);
    }
    /**
     * Merge the adjacent free blocks if needed
     * @param listPos The position of the new added free block in the list
     */
    private void update(int listPos) {
        FreeBlock current = this.get(listPos);
        if (listPos > 0) {
            FreeBlock previous = this.get(listPos - 1);
            if (previous.getStartPos() 
                + previous.getLength() == current.getStartPos()) {
                this.merge(listPos - 1);
                current = previous;
                listPos--;
            }                    
        }
        if (listPos < this.size() - 1) {
            FreeBlock next = this.get(listPos + 1);
            if (current.getLength() 
                    + current.getStartPos() == next.getStartPos()) {
                this.merge(listPos);
            }
        }
    }
    /**
     * Find out the best available free block
     * @param inputLength The length of input entry
     * @return minPos The position of the best available free block
     *          -1 when no available block
     */
    public int checkFreeBlock(int inputLength) {
        int minResult = 9999999;
        int minPos = -1;
        for (int i = 0; i < this.size(); i++) {
            int result = this.get(i).getLength() - inputLength;
            if ((result >= 0) && result < minResult) {
                minResult = result;
                minPos = i;
            }
        }
        return minPos;
    }
    /**
     * Print the free blocks (start position, length)
     * @return The string contains the start position and length of 
     *          free blocks
     *          null if list is empty
     */
    public String printBlocks() {        
        if (this.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size() - 1; i++) {            
            sb.append("(" + this.get(i).getStartPos() + ",");
            sb.append(this.get(i).getLength() + ") -> ");            
        }
        sb.append("(" + this.getLastEntry().getStartPos() + ",");
        sb.append(this.getLastEntry().getLength() + ")");
        return sb.toString();
    }
    /**
     * Shrink the block after insert a string
     * Remove the block once the length become 0
     * @param listPos The position of block in the list
     * @param inputLength The length of the input entry
     */
    public void shrinkBlock(int listPos, int inputLength) {
        FreeBlock current = this.get(listPos);
        current.addLength(0 - inputLength);
        current.increaseStartPos(inputLength);
        if (current.getLength() == 0) {
            this.remove(current);
        }
    }
}
