
public class RoundRobin extends Scheduler {
    
    private int quantum;
    private Process lastProcess;
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
        lastProcess= null;
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }
    
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        p.getPCB().setState(ProcessState.READY,CPU.clock);
        processes.add(p);
    }
    
    //assume if there exists a process with the same clock time as the swap, that it's already in the memory slot
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (processes.size()!=0)
        {
            if (lastProcess==null)
            {
                lastProcess=processes.get(0);
                lastProcess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
            }
            else
            {

                //pos=processes.indexOf(lastProcess);
                if (lastProcess.getPCB().getState()!=ProcessState.TERMINATED)
                {
                    processes.remove(lastProcess);
                    processes.add(lastProcess);
                    lastProcess.getPCB().setState(ProcessState.READY,CPU.clock);
                }

                lastProcess=processes.get(0);
                lastProcess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
            }
        }
        else if (lastProcess!=null && lastProcess.getPCB().getState()==ProcessState.TERMINATED)//simplified to else?
            lastProcess=null;
        return lastProcess;
    }

    public int getQuantum(){return quantum;}
}

