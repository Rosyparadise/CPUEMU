import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {
    int currentBlock;
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        this.currentBlock = 0;
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;

        int pSize= p.getMemoryRequirements();
        //updateProcessState
        int i = currentBlock;

        while(i < currentlyUsedMemorySlots.size()){
            if (currentlyUsedMemorySlots.get(i).getSizeAvail() -1 >= pSize && !fit){
                currentBlock++;
                fit=true;
                address=currentlyUsedMemorySlots.get(i).getStart();
                currentlyUsedMemorySlots.get(i).setStart(currentlyUsedMemorySlots.get(i).getStart()+pSize-1);
            }else{
                i++;
            }
        }

        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        return address;
    }

}
