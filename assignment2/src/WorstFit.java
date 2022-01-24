/** This class implements the Worst fit memory allocation algorithm. */
import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {

    /**Class constructor.
     *@param availableBlockSizes is the block sizes of memory that the PC has to offer for the accommodation of processes*/
    public WorstFit(int[] availableBlockSizes) {
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
        if (p!=null)
        {
            boolean fit = false;
            int address = -1;

            int pSize = p.getMemoryRequirements();

            int maxBlock = findMaxSlot(currentlyUsedMemorySlots);

            if (currentlyUsedMemorySlots.get(maxBlock).getSizeAvail() >= pSize) {
                address = currentlyUsedMemorySlots.get(maxBlock).getStart();
                fit = true;
                currentlyUsedMemorySlots.get(maxBlock).setStart(currentlyUsedMemorySlots.get(maxBlock).getStart() + pSize);
            }



            return address;
        }
        return -1;
    }

    /** This method finds the biggest slot contained in the memory.
     * @param currentlyUsedMemorySlots s an array list that contains all the memory slots that have been granted
     *for usage
     * @return is the block in which the biggest slot is located */
    private int findMaxSlot(ArrayList<MemorySlot> currentlyUsedMemorySlots){
        int maxBlock = 0;
        int maxSize = currentlyUsedMemorySlots.get(0).getSizeAvail();
        
        for(int i=1; i<currentlyUsedMemorySlots.size(); i++)
        {
            if(maxSize < currentlyUsedMemorySlots.get(i).getSizeAvail())
            {
                maxBlock = i;
                maxSize = currentlyUsedMemorySlots.get(i).getSizeAvail();
            }
        }
        return maxBlock;
    }
}