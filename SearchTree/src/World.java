/**
 * The class communicate with hash table, memory pool,
 * and 2-3+ Tree
 * Called by command processor
 * @author Sheng Peng <shengp6> & Wenjia Song <wenjia7>
 * @version 09/26/2016
 *
 */
public class World {
    private MemManager memory;
    private HashTable songTable;
    private HashTable artistTable;
    private TwoToThreeTree tree;
    /**
     * Constructor
     * create memory pool, artist table,
     * song table, and tree
     * @param hashSize The initial size of hash table
     * @param blockSize The initial size of memory pool
     */
    public World(int hashSize, int blockSize) {
        memory = new MemManager(blockSize);
        artistTable = new HashTable(hashSize, memory);
        songTable = new HashTable(hashSize, memory);
        tree = new TwoToThreeTree();
    }
    /**
     * Insert the artist and song into memory pool,
     * and insert handle to hash table, create the
     * KV pairs, and insert them to the tree
     * Standard output to indicate the process
     * @param names The name array contains artist and song
     */
    public void insert(String[] names) {
        Handle artistHandle = null;
        Handle songHandle = null;
        //insert artist
        //check duplicate
        if (artistTable.checkDuplicate(names[0])) {
            //get handle for this artist from table when duplicate
            artistHandle = artistTable.searchHandle(names[0]);
            System.out.println("|" + names[0] +
                    "| duplicates a record "
                    + "already in the artist database.");
        }
        else {
            //convert string to byte array
            byte[] entry = names[0].getBytes();
            //insert to memory pool, return handle
            artistHandle = memory.insert(entry);
            //insert to hash table
            artistTable.insert(names[0], artistHandle);
            if (artistTable.resized()) {
                System.out.println("Artist hash table size doubled.");
            }
            if (memory.resized() > 0) {
                System.out.print(memory.printExpandMemory());
            }
            System.out.println("|" + names[0] +
                    "| is added to the artist database.");
        }
        //insert song
        if (songTable.checkDuplicate(names[1])) {
            //get handle for this song from table when duplicate
            songHandle = songTable.searchHandle(names[1]);
            System.out.println("|" + names[1] +
                    "| duplicates a record already in the song database.");
        }
        else {
            //convert from string to byte array
            byte[] entry = names[1].getBytes();
            //insert to memory pool, return handle
            songHandle = memory.insert(entry);
            //insert to hash table
            songTable.insert(names[1], songHandle);
            if (songTable.resized()) {
                System.out.println("Song hash table size doubled.");
            }
            if (memory.resized() > 0) {
                System.out.print(memory.printExpandMemory());
            }
            System.out.println("|" + names[1] +
                    "| is added to the song database.");
        }
        //create two KVPair
        KVPair pair = new KVPair(artistHandle, songHandle);
        KVPair pair2 = new KVPair(songHandle, artistHandle);
        //check duplicate
        if (tree.search(pair) != null) {
            System.out.println("The KVPair (|" + names[0] +
                    "|,|" + names[1] + "|),(" + artistHandle.toString()
                    + "," + songHandle.toString() + ")"
                            + " duplicates a record already in the tree.");
            System.out.println("The KVPair (|" + names[1] +
                    "|,|" + names[0] + "|),(" + songHandle.toString()
                    + "," + artistHandle.toString() + ")"
                            + " duplicates a record already in the tree.");
        }
        else {
            //insert KVPair to tree
            tree.insert(pair);
            tree.insert(pair2);
            System.out.println("The KVPair (|" + names[0] +
                    "|,|" + names[1] + "|),(" + artistHandle.toString()
                    + "," + songHandle.toString() + ") is added to the "
                    + "tree.");
            System.out.println("The KVPair (|" + names[1] +
                    "|,|" + names[0] + "|),(" + songHandle.toString()
                    + "," + artistHandle.toString() + ") is added to the "
                    + "tree.");
        }

    }
    /**
     * Delete the entry in the tree
     * Delete the entry in hash table and memory
     * if this is the last entry contains the same string
     * @param names The string array contains the artist
     *              name and song name
     */
    public void delete(String[] names) {
        Handle songHandle;
        Handle artistHandle;
        artistHandle = artistTable.searchHandle(names[0]);
        songHandle = songTable.searchHandle(names[1]);
        if (artistHandle == null) {
            System.out.println("|" + names[0]
                    + "| does not exist in the artist database.");
        }
        else if (songHandle == null) {
            System.out.println("|" + names[1]
                    + "| does not exist in the song database.");
        }
        if (songHandle != null && artistHandle != null) {
            KVPair pair = new KVPair(artistHandle, songHandle);
            KVPair pair2 = new KVPair(songHandle, artistHandle);
            //if KVPair found in the tree, delete
            if (tree.search(pair) != null) {
                tree.delete(pair);
                tree.delete(pair2);
                System.out.println("The KVPair (|" + names[0] +
                        "|,|" + names[1] + "|) is deleted from the tree." );
                System.out.println("The KVPair (|" + names[1] +
                        "|,|" + names[0] + "|) is deleted from the tree." );
                //if there is no more record of this artist
                //delete in hash table and memory
                if (tree.search(artistHandle) == null) {
                    artistTable.remove(names[0]);
                    memory.remove(artistHandle);
                    System.out.println("|" + names[0]
                            + "| is deleted from the artist database.");
                }
                //if there is no more record of this song
                //delete in hash table and memory
                if (tree.search(songHandle) == null) {
                    songTable.remove(names[1]);
                    memory.remove(songHandle);
                    System.out.println("|" + names[1]
                            + "| is deleted from the song database.");
                }
            }
            //KVPair not found in the tree
            else {
                System.out.println("The KVPair (|" + names[0] + "|,|"
                        + names[1] + "|) was not found in the database.");
                System.out.println("The KVPair (|" + names[1] + "|,|"
                        + names[0] + "|) was not found in the database.");
            }

        }
    }
    /**
     * list all the songs sung by the artist
     * @param str The name of the artist
     */
    public void listArtist(String str) {
        Handle artistHandle = artistTable.searchHandle(str);
        Handle songHanlde;
        String song;
        if (artistHandle == null) {
            System.out.println("|" + str
                    + "| does not exist in the artist database.");
        }
        else {
            LeafNode node = tree.search(artistHandle);
            do {
                if (node.getFirstData().compareTo(artistHandle) == 0) {
                    songHanlde = node.getFirstData().value();
                    song = songTable.byteToString(songHanlde);
                    System.out.println("|" + song + "|");
                }
                if (node.getSecondData() != null
                        && node.getSecondData().compareTo(artistHandle) == 0) {
                    songHanlde = node.getSecondData().value();
                    song = artistTable.byteToString(songHanlde);
                    System.out.println("|" + song + "|");
                }
                node = node.next();
            } while (node != null &&
                    (node.getFirstData().compareTo(artistHandle) == 0));
        }

    }
    /**
     * list all the artist who sang this song
     * @param str The name of the song
     */
    public void listSong(String str) {
        Handle songHandle = songTable.searchHandle(str);
        Handle artistHanlde;
        String artist;
        if (songHandle == null) {
            System.out.println("|" + str
                    + "| does not exist in the song database.");
        }
        else {
            LeafNode node = tree.search(songHandle);
            do {
                if (node.getFirstData().compareTo(songHandle) == 0) {
                    artistHanlde = node.getFirstData().value();
                    artist = artistTable.byteToString(artistHanlde);
                    System.out.println("|" + artist + "|");
                }
                if (node.getSecondData() != null
                        && node.getSecondData().compareTo(songHandle) == 0) {
                    artistHanlde = node.getSecondData().value();
                    artist = artistTable.byteToString(artistHanlde);
                    System.out.println("|" + artist + "|");
                }
                node = node.next();
            } while (node != null &&
                    (node.getFirstData().compareTo(songHandle) == 0));
        }
    }
    /**
     * Remove all the songs sung by this artist
     * @param str The name of the artist
     */
    public void removeArtist(String str) {
        Handle artistHandle = artistTable.searchHandle(str);
        if (artistHandle == null) {
            System.out.println("|" + str + "| does not exist "
                    + "in the artist database.");
        }
        else {
            do {
                LeafNode node = tree.search(artistHandle);
                KVPair deleteItem;
                if (node.getFirstData().compareTo(artistHandle) == 0) {
                    deleteItem = node.getFirstData();
                }
                else {
                    deleteItem = node.getSecondData();
                }
                //create another KVPair
                Handle songHandle = deleteItem.value();
                KVPair deleteItem2 = new KVPair(songHandle, artistHandle);
                //delete two items
                tree.delete(deleteItem);
                tree.delete(deleteItem2);
                String s1 = artistTable.byteToString(artistHandle);
                String s2 = songTable.byteToString(songHandle);
                System.out.println("The KVPair (|" + s1 +
                        "|,|" + s2 + "|) is deleted from the tree." );
                System.out.println("The KVPair (|" + s2 +
                        "|,|" + s1 + "|) is deleted from the tree." );
                //if there is no more record of this artist
                //delete in hash table and memory
                if (tree.search(artistHandle) == null) {
                    artistTable.remove(s1);
                    memory.remove(artistHandle);
                    System.out.println("|" + s1
                            + "| is deleted from the artist database.");
                }
                //if there is no more record of this song
                //delete in hash table and memory
                if (tree.search(songHandle) == null) {
                    songTable.remove(s2);
                    memory.remove(songHandle);
                    System.out.println("|" + s2
                            + "| is deleted from the song database.");
                }
            } while (artistTable.searchHandle(str) != null);
        }

    }
    /**
     * Remove all the artist who sang this song
     * @param str The name of this song
     */
    public void removeSong(String str) {
        Handle songHandle = songTable.searchHandle(str);
        if (songHandle == null) {
            System.out.println("|" + str + "| does not exist "
                    + "in the song database.");
        }
        else {
            do {
                LeafNode node = tree.search(songHandle);
                KVPair deleteItem;
                if (node.getFirstData().compareTo(songHandle) == 0) {
                    deleteItem = node.getFirstData();
                }
                else {
                    deleteItem = node.getSecondData();
                }
                //create another KVPair
                Handle artistHandle = deleteItem.value();
                KVPair deleteItem2 = new KVPair(artistHandle, songHandle);
                //delete two items
                tree.delete(deleteItem);
                tree.delete(deleteItem2);
                String s1 = songTable.byteToString(songHandle);
                String s2 = artistTable.byteToString(artistHandle);
                System.out.println("The KVPair (|" + s1 +
                        "|,|" + s2 + "|) is deleted from the tree." );
                System.out.println("The KVPair (|" + s2 +
                        "|,|" + s1 + "|) is deleted from the tree." );
                //if there is no more record of this artist
                //delete in hash table and memory
                if (tree.search(songHandle) == null) {
                    songTable.remove(s1);
                    memory.remove(songHandle);
                    System.out.println("|" + s1
                            + "| is deleted from the song database.");
                }
                //if there is no more record of this song
                //delete in hash table and memory
                if (tree.search(artistHandle) == null) {
                    artistTable.remove(s2);
                    memory.remove(artistHandle);
                    System.out.println("|" + s2
                            + "| is deleted from the artist database.");
                }
            } while (songTable.searchHandle(str) != null);
        }

    }
    /**
     * print all the songs in the memory
     */
    public void printSong() {
        System.out.print(songTable.toString());
        System.out.println("total songs: "
                + songTable.getNumOfEntries());
    }
    /**
     * print all the artists in the memory
     */
    public void printArtist() {
        System.out.print(artistTable.toString());
        System.out.println("total artists: "
                + artistTable.getNumOfEntries());
    }
    /**
     * print the free blocks
     */
    public void printBlock() {
        if (memory.printBlocks() == null) {
            System.out.println("(" + memory.size() + ",0)");
        }
        else {
            System.out.println(memory.printBlocks());
        }
    }
    /**
     * print the tree
     */
    public void printTree() {
        System.out.println("Printing 2-3 tree:");
        if (!tree.printTree().equals("null\n")) {
            System.out.print(tree.printTree());
        }
    }
}
