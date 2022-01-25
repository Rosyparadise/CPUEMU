import java.util.ArrayList;

/** This class implements the First fit memory allocation algorithm. */
public class FirstFit extends MemoryAllocationAlgorithm {

    /**Class constructor.
     *@param availableBlockSizes is the block sizes of memory that the PC has to offer for the accommodation of processes*/
    public FirstFit(int[] availableBlockSizes) {
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
            for (MemorySlot s : currentlyUsedMemorySlots) {
                if (s.getSizeAvail() >= pSize) {
                    fit = true;
                    address = s.getStart();
                    s.setStart(s.getStart() + pSize);
                    break;
                }
            }
            return address;
        }
        return -1;
    }
    
}