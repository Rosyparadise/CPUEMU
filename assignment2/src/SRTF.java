/**This class implements the Shortest Remaining Time First scheduling algorithm */
public class SRTF extends Scheduler {
    private Process lastprocess;

    /**Class Constructor. */
    public SRTF() {
        /* TODO: you _may_ need to add some code here */
        lastprocess= null;
    }

    /**This method adds a new process to available processes and also changes its state to READY.
     * @param p  a process*/
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        //Adds process to available processes. Also changes state to READY.
        p.getPCB().setState(ProcessState.READY,CPU.clock);
        processes.add(p);
    }

    /**This method finds the next process that will be executed based on the Shortest Remaining Time First algorithm.
     * @return the process the will be executed next*/
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */

            if (processes.size()!=0)
            {
                //find available process with the least burst time.
                int min=processes.get(0).getBurstTime();
                int pos=0;
                for (int i=0;i<processes.size();i++)
                {
                    if (processes.get(i).getBurstTime()<min)
                    {
                        min = processes.get(i).getBurstTime();
                        pos = i;
                    }
                }
                //set state of current process to READY
                if ( lastprocess!=null && lastprocess.getPCB().getState()!=ProcessState.TERMINATED)
                    lastprocess.waitInBackground();
                lastprocess=processes.get(pos);

                //swap state to the one with the least burst time and switch its state to RUNNING
                lastprocess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
            }

            //in case the running process got terminated and there are no other available processes
            else if (lastprocess!=null && lastprocess.getPCB().getState()==ProcessState.TERMINATED)
                lastprocess=null;


                
        return lastprocess;
    }
}
