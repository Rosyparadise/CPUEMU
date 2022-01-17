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
            currentlyUsedMemorySlots.add(new MemorySlot(previousEnd + 1, previousEnd + 1 + availableBlockSizes[i], previousEnd, previousEnd + 1 + availableBlockSizes[i])) ;
            previousEnd += availableBlockSizes[i];

            i++;
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
            p.setAddress(add);
            runningProcesses.add(p);
        }
        return fit;
    }
    //!!!!!!!!!!!!!!!!!!!!!!!CHANGE STATE TO READY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void updateMemory(){
        //accessing the array of processes that have been added in the cpu
        for (int i=0; i<runningProcesses.size(); i++){
            //checking each process' state
            
            //uyuyggyvyvgykvguyvyvbguybvkjkuyghkbjjvkugybhkvjkkgyhjkbkugyhjkbjvkgjbykhjvjkgybjkhkgybhkjkgykbhjkgybhkjkjyb
            if (runningProcesses.get(i).getPCB().getState().equals("TERMINATED") || runningProcesses.get(i).getPCB().getState().equals("READY")){
                //if terminated or ready it must be removed from cpu
                
                //padress is the address given to the terminated process and also the start of the empty slot
                int pAddress = runningProcesses.get(i).getAddress();
                //pblock is the block where the terminated process is located
                int pBlock = currentlyUsedMemorySlots.indexOf(pAddress);

                //gjrneigjrtnjgrnjk we cant know for certain if there's an extra element (j=i+1)!!!!!!!!!!!!!!!!!!!!!!!!!
                //accessing the following cpu running processes contained in the same block as the terminated process
                for(int j=i+1; runningProcesses.get(j).getAddress()<currentlyUsedMemorySlots.get(pBlock).getBlockEnd() ; j++){
                    //relocating all following addresses higher on the cpu table, the first process gets the address of the terminated one
                    runningProcesses.get(j).setAddress(pAddress);
                    //the next free address that can accommodate a process
                    pAddress+=runningProcesses.get(j).getMemoryRequirements();
                }
                
                //changing the start of the empty slot
                currentlyUsedMemorySlots.get(pBlock).setStart(pAddress);
                //removing process from array list
                //gfdfsgsgdf
                runningProcesses.remove(runningProcesses.get(i));
                //setting up the process' address to "null"
                runningProcesses.get(i).setAddress(-1);
            }
        }
    }
    
    public ArrayList<Process> getRunningProcesses(){return runningProcesses;}
}
