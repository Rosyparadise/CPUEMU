/**This class implements the Round Robin scheduling algorithm */
public class RoundRobin extends Scheduler {
    
    private int quantum;
    private Process lastProcess;


    /**Class Constructor with quantum set automatically.*/
    public RoundRobin() {
        this.quantum = 3; // default quantum
        /* TODO: you _may_ need to add some code here */
        lastProcess= null;
    }

    /**Class Constructor.
     * @param quantum the quantum used in the algorithm*/
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    /**This method adds a new process to available processes and also changes its state to READY.
     * @param p  a process*/
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        p.getPCB().setState(ProcessState.READY,CPU.clock);
        processes.add(p);
    }

    /**This method finds the next process that will be executed based on the Round Robin algorithm.
     * @return the process the will be executed next*/
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

    /**This method is a getter for the quantum.
     * @return the quantum*/
    public int getQuantum(){return quantum;}
}

