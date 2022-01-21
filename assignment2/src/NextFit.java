import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {
    int currentBlock;
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        this.currentBlock = 0;
    }
    
    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots)
    {
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


            /* TODO: you need to add some code here
             * Hint: this should return the memory address where the process was
             * loaded into if the process fits. In case the process doesn't fit, it
             * should return -1. */

            return address;
        }
        return -1;
    }
    
}