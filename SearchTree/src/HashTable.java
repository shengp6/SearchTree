/**
 * Hash Table class
 * Store the handles
 * Strings(Keys) are logically stored in hash table
 * but actually stored in the bytes array
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/05/2016
 */
public class HashTable {
    private int size;
    private Handle[] table;
    private int numOfEntries;
    private MemManager memory;
    private boolean resize;

    /**
     * Constructor with customized size
     * @param size The initial size of hash table
     * @param memory The memory pool
     */
    public HashTable(int size, MemManager memory) {
        this.size = size;
        numOfEntries = 0;
        table = new Handle[size];
        this.memory = memory;
        resize = false;
    }
    /**
     * Accessor method
     * @return size The size of hash table
     */
    public int getSize() {
        return size;
    }
    /**
     * Accessor method
     * @return numOfEntries The number of entries in the hash table
     */
    public int getNumOfEntries() {
        return numOfEntries;
    }
    /**
     * insert the string into hash table
     * and then print the result
     * @param string The string we want to insert
     * @param handle The handle of the string
     */
    public void insert(String string, Handle handle) {
        this.resetResize();
        if (numOfEntries == size / 2) {
            resize();
            resize = true;
        }
        int pos = hashing(string, size);

        if (table[pos] != null) {
            pos = quadratic(pos);
        }

        table[pos] = handle;
        numOfEntries++;

    }
    /**
     * When the number of entries is equal to 50% of the size
     * we double the hash table
     */
    private void resize() {
        HashTable temp = new HashTable(size * 2, memory);
        for (int i = 0; i < this.size; i++) {
            if (table[i] != null) {
                temp.insert(this.byteToString(table[i]), table[i]);
            }
        }
        this.size = temp.size;
        this.table = temp.table;
    }

    /**
     * print all the entries in the hash table
     * @return all the strings in the memory pool
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                sb.append("|" + this.byteToString(table[i])
                    + "| " + i + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * If the collision occurs, we find out a new position to
     * insert the data
     * @param pos The home slot we want to insert
     * @return The available position
     */
    private int quadratic(int pos) {
        for (int i = 0; i < size; i++) {
            int index = (pos + i * i) % size;
            if (table[index] == null) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Check if the data is already in the database
     * @param str The string we intend to insert
     * @return true If duplicate, false if not duplicate
     */
    public boolean checkDuplicate(String str) {
        return this.search(str) != -1;
    }

    /**
     * Search if the hash table already contains the string
     * @param str The string to check
     * @return The position in hash table if exists, null if not
     */
    private int search(String str) {
        int pos = hashing(str, size);
        if (table[pos] != null
                && str.equals(this.byteToString(table[pos]))) {
            return pos;
        }
        for (int i = 0; i < size; i++) {
            int index = (pos + i * i) % size;
            if (table[index] != null
                    && str.equals(this.byteToString(
                            table[index]))) {
                return index;
            }
        }
        return -1;
    }
    /**
     * Search the hash table to see if the string
     *         exists
     * @param str The target string
     * @return The handle of the string, null if not found
     */
    public Handle searchHandle(String str) {
        int index = search(str);
        if (index == -1) {
            return null;
        }
        return table[index];
    }
    /**
     * Remove the handle in the hash table
     * @param str The string you want to remove from memory pool
     * @return The handle you removed, or null if not found
     */
    public Handle remove(String str) {
        int removePos = this.search(str);
        if (removePos != -1) {
            Handle removeItem = table[removePos];
            table[removePos] = null;
            numOfEntries--;
            return removeItem;
        }
        return null;
    }
    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     * The string that we are hashing
     * @param m
     * The size of the hash table
     * @return The home slot for that string
     */
//    public int hashing(String s, int m)
    private int hashing(String s, int m)
    {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % m);
    }
    /**
     * reset the resize to false
     */
    private void resetResize() {
        resize = false;
    }
    /**
     * Check if the hash table resized or not
     * @return true if the hash table resize, otherwise false
     */
    public boolean resized() {
        return resize;
    }
    /**
     * Transfer the record from byte array to string
     * @param handle The handle of string
     * @return The string corresponding to the handle
     */
    public String byteToString(Handle handle) {
        byte[] entry = new byte[65536];
        int length = memory.getEntry(entry, handle);
        //transfer the byte array to string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char)entry[i]);
        }
        return sb.toString();
    }
}
