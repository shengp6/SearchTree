/**
 * The class of memory pool
 * It's a byte array, which store the strings
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/25/2016
 *
 */
public class MemManager {
    private FreeBlockList freeBlockList;
    private byte[] byteArray;
    private int resize;
    private int initSize;
    /**
     * Constructor
     * Create a new byte array
     * Create a new free block list
     * Add a node to free block list
     * @param blockSize The size of memory pool
     */
    public MemManager(int blockSize) {
        initSize = blockSize;
        byteArray = new byte[initSize];
        freeBlockList = new FreeBlockList(initSize);
        resize = 0;
    }
    /**
     * Insert a string into the memory pool
     * @param entry The byte array we want to insert
     *          into the memory pool
     * @return The handle store the start
     *          position in the memory pool
     */
    public Handle insert(byte[] entry) {
        this.resetResize();
        int length = entry.length;
        byte[] input = new byte[length + 2];
        input[0] = (byte)(length >> 8);
        input[1] = (byte)(length);
        System.arraycopy(entry, 0, input, 2, length);
        int inputPos;
        int listPos = freeBlockList.checkFreeBlock(input.length);
        while ( listPos == -1) { //no available block
            resizeMemory();
            resize++;
            listPos = freeBlockList.checkFreeBlock(input.length);
        }
        inputPos = freeBlockList.get(listPos).getStartPos();
        //shrink the free block
        freeBlockList.shrinkBlock(listPos, length + 2);
        //copy the bytes array into memory pool
        System.arraycopy(input, 0, byteArray, inputPos, length + 2);
        return new Handle(inputPos);
    }
    /**
     * Free a block at the position specified by theHandle.
     * Merge adjacent free blocks.
     * @param handle The handle indicate the start position
     *          of record
     */
    public void remove(Handle handle) {
        int pos = handle.pos();
        //Calculate the length of the string
        int length1 = byteArray[pos] << 8;
        int length2 = byteArray[pos + 1];
        int length = length1 + length2;
        //remove the data in byteArray
        byte[] emptyArray = new byte[length + 2];
        System.arraycopy(emptyArray, 0, byteArray, pos, length + 2);
        //deal with the free block list
        //add a free block into free block list
        freeBlockList.addBlock(new FreeBlock(pos, length + 2));
    }

    /**
     * add 32 bytes to the memory pool
     * update the free block list
     */
    private void resizeMemory() {
        //add new block into list
        freeBlockList.addBlock(
                new FreeBlock(byteArray.length, initSize));

        //resize the memory pool
        byte[] temp = new byte[byteArray.length + initSize];
        System.arraycopy(byteArray, 0, temp, 0, byteArray.length);
        byteArray = temp;

    }

    /**
     * Return the record corresponding to the handle
     * @param handle The handle store the start position of record
     * @param entry An empty byte array
     * @return entry The copy of record (byte array)
     */
    public int getEntry(byte[] entry, Handle handle) {
        int startPos = handle.pos();
        int first = byteArray[startPos] << 8;
        int second = byteArray[startPos + 1];
        int length = first + second;
        System.arraycopy(byteArray, startPos + 2, entry, 0, length);
        return length;
    }
    /**
     * Print the information about expand memory
     * @return The string shows that the memory pool expanded
     */
    public String printExpandMemory() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resize; i++) {
            sb.append("Memory pool expanded to be "
                    + (this.size() - initSize * (resize
                            - i - 1) + " bytes.\n"));
        }
        return sb.toString();
    }

    /**
     * Reset the resize to false
     */
    private void resetResize() {
        resize = 0;
    }
    /**
     * The memory pool resized or not
     * @return resize The memory pool resized or not
     */
    public int resized() {
        return resize;
    }
    /**
     * Get the size of memory pool
     * @return The length of the byte array
     */
    public int size() {
        return byteArray.length;
    }
    /**
     * To string the free blocks
     * @return The string lists all the free blocks
     */
    public String printBlocks() {
        return freeBlockList.printBlocks();
    }
}
