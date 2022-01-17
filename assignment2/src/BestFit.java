import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {

    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;

        int pSize = p.getMemoryRequirements();
        int i = 0;
        int blockNum = -1;
        int closestToMemory = 2147483647 ;

        while(i < currentlyUsedMemorySlots.size()) {
           
            if(currentlyUsedMemorySlots.get(i).getSizeAvail() >= pSize && currentlyUsedMemorySlots.get(i).getSizeAvail() < closestToMemory){
                closestToMemory = currentlyUsedMemorySlots.get(i).getSizeAvail();
                address = currentlyUsedMemorySlots.get(i).getStart();
                fit = true;
                blockNum = i;
            }
            i++;
        }

        if(address > -1) {
            currentlyUsedMemorySlots.get(blockNum).setStart(pSize + currentlyUsedMemorySlots.get(blockNum).getStart() - 1);
        }

        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        return address;
    }

}
