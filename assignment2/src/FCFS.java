public class FCFS extends Scheduler {
    
    public FCFS() {
        /* TODO: you _may_ need to add some code here */
    }
    
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        //Adds process to available processes. Also changes state to READY.
        p.getPCB().setState(ProcessState.READY,CPU.clock);
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */

        //run only if there are available processes in RAM
        if (processes.size()!=0)
        {

            //find the first unterminated process, set state to RUNNING and return it to the CPU
            for (int i=0;i<processes.size();i++)
            {
                if (processes.get(i).getPCB().getState()!=ProcessState.TERMINATED)
                {
                    processes.get(i).getPCB().setState(ProcessState.RUNNING,CPU.clock);
                    return processes.get(i);
                }
            }

        }
        
        return null;
    }
}
