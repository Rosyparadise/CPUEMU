
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    private int address;
    
    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
        this.address= -1;
    }
    
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }
    
    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        if (pcb.getState()!=ProcessState.RUNNING)
            pcb.setState(ProcessState.RUNNING,CPU.clock);
        burstTime--;
        
    }
    
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        pcb.setState(ProcessState.READY,CPU.clock);
        
    }
    
    public double getWaitingTime()
    {
        /* TODO: you need to add some code here
         * and change the return value */
        int waitingTime=0;
        if (pcb.getStartTimes().size()==0)
            waitingTime=CPU.clock-arrivalTime;
        else
        {
            waitingTime=CPU.clock-arrivalTime;
            for (int i=0;i<pcb.getStartTimes().size();i++)
            {
                waitingTime+=pcb.getStopTimes().get(i)-pcb.getStartTimes().get(i);
            }
        }
        return waitingTime;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        int responseTime=0;
        if (pcb.getStartTimes().size()==0)
            responseTime=CPU.clock-arrivalTime;
        else if (pcb.getStartTimes().size()>0)
            responseTime=pcb.getStartTimes().get(0)-arrivalTime;
        
        return responseTime;
    }
    
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        double turnAroundTime=0;
        turnAroundTime=burstTime+getWaitingTime();
        return turnAroundTime;
    }
    
    public int getMemoryRequirements(){
        return memoryRequirements;
    }

    public int getAddress(){
        return address;
    }

    public void setAddress(int address){
        this.address=address;
    }
}
