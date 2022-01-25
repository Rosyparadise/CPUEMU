public class MemorySlot {

    /**This class represents a memory slot.*/
    private int start;
    private int end;
    private final int blockStart;
    private final int blockEnd;
    /* The following should always hold true:
     * start >= blockStart
     * end <= blockEnd */

    /**Class constructor.
     * @param start is the address where the memory slot starts
     * @param end is the address where the memory slot ends
     * @param blockStart is the address of where the block starts
     * @param blockEnd address of where the block ends
     * */
    public MemorySlot(int start, int end, int blockStart, int blockEnd) {
        if ((start < blockStart) || (end > blockEnd)) {
            throw new java.lang.RuntimeException("Memory access out of bounds.");
        }
        this.start = start;
        this.end = end;
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }

    /**This method is a getter for the size of the slot that remains available.
     * @return the size of the slot that remains available
     * */
    public int getSizeAvail(){
        return (this.end - this.start+1);
    }

    /**This method is a getter for the block start.
     * @return the address of where the block starts
     */
    public int getBlockStart() {
        return blockStart;
    }

    /**This method is a getter for the block end.
     * @return the address of where the block ends
     */
    public int getBlockEnd() {
        return blockEnd;
    }

    /**This method is a getter for the slot start.
     * @return the address of where the slot starts
     */
    public int getStart() {
        return start;
    }

    /**This method is a setter for the slot start.
     * @param start  the address of where the slot starts
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**This method is a getter for the slot end.
     * @return the address of where the slot ends
     */
    public int getEnd() {
        return end;
    }

    /**This method is a setter for the slot end.
     * @param end  the address of where the slot ends
     */
    public void setEnd(int end) {
        this.end = end;
    }
    
}