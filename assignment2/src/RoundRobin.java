
public class RoundRobin extends Scheduler {
    
    private int quantum;
    private Process lastProcess;
    public RoundRobin() {
        this.quantum = 3; // default quantum
        /* TODO: you _may_ need to add some code here */
        lastProcess= null;
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
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

        if (processes.size()!=0)
        {
            //if there is no running process get the first one available and change state to RUNNING
            if (lastProcess==null)
            {
                lastProcess=processes.get(0);
                lastProcess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
            }
            else
            {

                //if there is, add it to the end of the available processes and change state to READY
                if (lastProcess.getPCB().getState()!=ProcessState.TERMINATED)
                {
                    processes.remove(lastProcess);
                    processes.add(lastProcess);
                    lastProcess.waitInBackground();
                }

                lastProcess=processes.get(0);
                lastProcess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
            }
        }
        //in case the running process got terminated and there are no other available processes
        else if (lastProcess!=null && lastProcess.getPCB().getState()==ProcessState.TERMINATED)
            lastProcess=null;
        return lastProcess;
    }

    public int getQuantum(){return quantum;}
}

