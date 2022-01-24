/** This class implements the Next fit memory allocation algorithm. */
import java.util.ArrayList;


public class NextFit extends MemoryAllocationAlgorithm {
    int currentBlock;

    /**Class constructor.
     *@param availableBlockSizes is the block sizes of memory that the PC has to offer for the accommodation of processes*/
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        this.currentBlock = 0;
    }

    /**Method fitProcess searches through the memory blocks looking for an empty memory slot that fits a certain process
     *based on the Next Fit algorithm.
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
        if (p!=null) {
            boolean fit = false;
            int address = -1;

            int pSize = p.getMemoryRequirements();
            int i = currentBlock;
            boolean flag = false;
            while (i < currentlyUsedMemorySlots.size()) {

                if (currentlyUsedMemorySlots.get(i).getSizeAvail() >= pSize) {
                    currentBlock = i;
                    fit = true;
                    address = currentlyUsedMemorySlots.get(i).getStart();
                    currentlyUsedMemorySlots.get(i).setStart(currentlyUsedMemorySlots.get(i).getStart() + pSize);
                    break;
                }
                i++;
                if (i > currentlyUsedMemorySlots.size() - 1) {
                    i = 0;
                    flag = true;
                }
                if (flag && i == currentBlock)
                    break;
            }

            return address;
        }
        return -1;
    }
}