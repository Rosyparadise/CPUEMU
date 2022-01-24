/** This abstract class represents a Memory Allocation Algorithm*/
import java.util.ArrayList;

public abstract class MemoryAllocationAlgorithm {

    protected final int[] availableBlockSizes;

    /**Class constructor.
     * @param availableBlockSizes is the block sizes of memory that the PC has to offer for the accommodation of processes
     */
    public MemoryAllocationAlgorithm(int[] availableBlockSizes) {
        this.availableBlockSizes = availableBlockSizes;
    }

    /**Method fitProcess searches through the memory blocks looking for an empty memory slot that fits a certain process.
     * @param p is a process
     * @param currentlyUsedMemorySlots is an array list that contains all the memory slots that have been granted
     *for usage
     * @return the address that the process will be accommodated in or -1 if the process cannot fit
     * in any memory block*/
    public abstract int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots);
}
