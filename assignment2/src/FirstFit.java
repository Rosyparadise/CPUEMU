import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {
    
    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        boolean fit = false;
        int address = -1;
        int pSize= p.getMemoryRequirements();
        //updateProcessState
        for (MemorySlot s:currentlyUsedMemorySlots){
            if(s.getSizeAvail()-1>=pSize && !fit){
                fit=true;
                address=s.getStart();
                s.setStart(s.getStart()+pSize-1);
            }
        }
        return address;
    }

}
