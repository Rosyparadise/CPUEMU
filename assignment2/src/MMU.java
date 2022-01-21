import java.util.ArrayList;

public class MMU {
    
    private final int[] availableBlockSizes; //MEMORY
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private ArrayList<Process> runningProcesses;
    
    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
        this.runningProcesses= new ArrayList<Process>();
        
        int i=1;
        int previousEnd = availableBlockSizes[0] - 1;
        currentlyUsedMemorySlots.add(new MemorySlot(0, previousEnd, 0, previousEnd));
        
        while(i < availableBlockSizes.length){
            // !!!!!!!this maybe? dunno!!!!!!!!
            currentlyUsedMemorySlots.add(new MemorySlot(previousEnd + 1, previousEnd + 1 + availableBlockSizes[i], previousEnd+1, previousEnd + 1 + availableBlockSizes[i])) ;
            previousEnd += availableBlockSizes[i];
            i++;
        }
    }

    public static void selectionSort(ArrayList<Process> array) {
        for (int i = 0; i < array.size(); i++) {
            int min = array.get(i).getAddress();
            int minId = i;
            for (int j = i+1; j < array.size(); j++) {
                if (array.get(j).getAddress() < min) {
                    min = array.get(j).getAddress();
                    minId = j;
                }
            }
            Process temp = array.get(i);
            array.set(i,array.get(minId));
            array.set(minId,temp);
        }
    }
    
    public boolean loadProcessIntoRAM(Process p) {
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not
         * */
        boolean fit = false;
        updateMemory();
        int add= algorithm.fitProcess(p, currentlyUsedMemorySlots);
        if(add != -1){
            fit = true;
            p.getPCB().setState(ProcessState.READY,CPU.clock);
            p.setAddress(add);
            runningProcesses.add(p);
        }
        return fit;
    }
    private void updateMemory(){
        //accessing the array of processes that have been added in the cpu
        for (int i=0; i<runningProcesses.size(); i++){
            //checking each process' state

            if (runningProcesses.get(i).getPCB().getState()==ProcessState.TERMINATED){
                //if terminated or ready it must be removed from cpu

                //padress is the address given to the terminated process and also the start of the empty slot
                int pAddress = runningProcesses.get(i).getAddress();
                int pBlock=-1;
                //pblock is the block where the terminated process is located
                for (int k=0;k< currentlyUsedMemorySlots.size();k++)
                {
                   if (pAddress>=currentlyUsedMemorySlots.get(k).getBlockStart() && pAddress<=currentlyUsedMemorySlots.get(k).getBlockEnd())
                   {

                       pBlock=k;
                       break;
                   }
                }
                ArrayList<Process> processestobemoved = new ArrayList<>();

                //accessing the following cpu running processes contained in the same block as the terminated process
                for (int k=0;k<runningProcesses.size();k++)
                {
                    if (runningProcesses.get(k).getAddress()>pAddress && runningProcesses.get(k).getAddress()<currentlyUsedMemorySlots.get(pBlock).getBlockEnd())
                    {
                        processestobemoved.add(runningProcesses.get(k));
                    }
                }
                //relocating all following addresses higher on the cpu table, the first process gets the address of the terminated one
                if (processestobemoved.size()!=0)
                {
                    selectionSort(processestobemoved);
                    for (int k=0;k<processestobemoved.size();k++)
                    {
                        //the next free address that can accommodate a process
                        processestobemoved.get(k).setAddress(pAddress);
                        pAddress+=processestobemoved.get(k).getMemoryRequirements();

                    }
                }
                currentlyUsedMemorySlots.get(pBlock).setStart(pAddress);
                runningProcesses.remove(i);
                i--;
            }
        }
    }
}
