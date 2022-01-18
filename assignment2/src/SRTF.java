public class SRTF extends Scheduler {
    private Process lastprocess;
    public SRTF() {
        /* TODO: you _may_ need to add some code here */
        lastprocess= null;
    }
    
    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        p.getPCB().setState(ProcessState.READY,CPU.clock);
        processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */

            if (processes.size()!=0)
            {

                int min=processes.get(0).getBurstTime();
                int pos=0;
                for (int i=0;i<processes.size();i++)
                {
                    //must pop out terminated process before doing this
                    if (processes.get(i).getBurstTime()<min)
                        min=processes.get(i).getBurstTime();
                    pos=i;
                }
                //!!!!!!!!!!!!MADE CHANGES THINK ABOUT IT. REMOVED IF LASTPROC==NULL
                if ( lastprocess!=null && lastprocess.getPCB().getState()!=ProcessState.TERMINATED)
                    lastprocess.getPCB().setState(ProcessState.READY,CPU.clock);
                lastprocess=processes.get(pos);

                lastprocess.getPCB().setState(ProcessState.RUNNING,CPU.clock);
                //have to decrease bursttime with each tick in CPU
                //if bursttime==0 change lastprocess to null
            }
            else if (lastprocess!=null && lastprocess.getPCB().getState()==ProcessState.TERMINATED)//simplified to else?
                lastprocess=null;


                
        return lastprocess;
    }
}
