/** This class implements the Best fit memory allocation algorithm. */
import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {

    /**Class constructor.
     *@param availableBlockSizes is the block sizes of memory that the PC has to offer for the accommodation of processes*/
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    /**Method fitProcess searches through the memory blocks looking for an empty memory slot that fits a certain process
     *based on the Best Fit algorithm.
     * @param p is a process
     * @param currentlyUsedMemorySlots is an array list that contains all the memory slots that have been granted
     *for usage
     * @return the address that the process will be accommodated in or -1 if the process cannot fit
     * in any memory block*/
    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots)
    {
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        if (p != null)
        {
            boolean fit = false;
            int address = -1;

            int pSize = p.getMemoryRequirements();
            int i = 0;
            int blockNum = -1;
            int closestToMemory = 2147483647;

            while (i < currentlyUsedMemorySlots.size()) {

                if (currentlyUsedMemorySlots.get(i).getSizeAvail() >= pSize && currentlyUsedMemorySlots.get(i).getSizeAvail() < closestToMemory) {
                    closestToMemory = currentlyUsedMemorySlots.get(i).getSizeAvail();
                    address = currentlyUsedMemorySlots.get(i).getStart();
                    fit = true;
                    blockNum = i;
                }
                i++;
            }
            if (fit) {
                currentlyUsedMemorySlots.get(blockNum).setStart(pSize + currentlyUsedMemorySlots.get(blockNum).getStart());
            }
            return address;
        }
        return -1;
    }
    
}