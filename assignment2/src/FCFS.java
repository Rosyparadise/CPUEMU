public class FCFS extends Scheduler {
    
    public FCFS() {
        /* TODO: you _may_ need to add some code here */
        /* BLANK FOR NOW */
    }
    
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        //UPDATE AVAILABLE PROCESS EACH TICK (I THINK, IM VERY SLEEPY)
        //will be called again when its ready for another process >:)
        if (processes.size()!=0)
        {

            //prepei na to kanoume kai gia ta memoryslots!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            processes.get(0).getPCB().setState(ProcessState.RUNNING,CPU.clock);
            return processes.get(0);
            //in cpu if bursttime==0 set process as TERMINATED and remove it from memory slot
            //will be saved in tick() as a temp process variable and from there currentprocess will get its value.
            //statement to check if process is done in cpu before running getnextprocess
        }
        
        return null;
    }
}
