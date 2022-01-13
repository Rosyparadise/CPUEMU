import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes; //MEMORY
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    
    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();

        int i=1;
        int previousEnd = availableBlockSizes[0] - 1;
        currentlyUsedMemorySlots.add(new MemorySlot(0, previousEnd, 0, previousEnd));

        while(i < availableBlockSizes.length){
            currentlyUsedMemorySlots.add(new MemorySlot(previousEnd + 1, previousEnd + 1 + availableBlockSizes[i], previousEnd, previousEnd + 1 + availableBlockSizes[i])) ;
            previousEnd += availableBlockSizes[i];

            i++;
        }
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;

        if(algorithm.fitProcess(p, currentlyUsedMemorySlots) != -1)
            fit = true;

        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PREPEI NA KOITAME TERMATISMENES DIERGASIES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * */
        
        return fit;
    }
}
