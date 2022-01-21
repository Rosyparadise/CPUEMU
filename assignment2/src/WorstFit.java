import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {
    
    public WorstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }
    
    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots)
    {
        if (p!=null)
        {
            boolean fit = false;
            int address = -1;

            int pSize = p.getMemoryRequirements();

            int maxBlock = findMaxSlot(currentlyUsedMemorySlots);

            if (maxBlock >= pSize) {
                address = currentlyUsedMemorySlots.get(maxBlock).getStart();
                fit = true;
                currentlyUsedMemorySlots.get(maxBlock).setStart(currentlyUsedMemorySlots.get(maxBlock).getStart() + pSize + 1);
            }

            /* TODO: you need to add some code here
             * Hint: this should return the memory address where the process was
             * loaded into if the process fits. In case the process doesn't fit, it
             * should return -1. */

            return address;
        }
        return -1;
    }
    
    private int findMaxSlot(ArrayList<MemorySlot> currentlyUsedMemorySlots){
        int maxBlock = 0;
        int maxSize = currentlyUsedMemorySlots.get(0).getSizeAvail();
        
        for(int i=1; i<currentlyUsedMemorySlots.size(); i++){
            if(maxSize < currentlyUsedMemorySlots.get(i).getSizeAvail()){
                maxBlock = i;
                maxSize = currentlyUsedMemorySlots.get(i).getSizeAvail();
            }
        }
        return maxBlock;
    }
}