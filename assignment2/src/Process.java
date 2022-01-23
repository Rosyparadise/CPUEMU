public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    //saves the address the process is saved at
    private int address;
    //clock cycle it first got into a READY state
    private int RAMarrivalClock;
    
    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
        this.address= -1;
        RAMarrivalClock=-1;
    }
    
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }
    
    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */

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

        //the amount of time the process is in READY state

        int waitingTime=0;
        //in case the process has never been in a RUNNING state, return current cpu clock minus RAM Arrival cycle
        //otherwise subtract each time the process has been in a running state
        waitingTime=CPU.clock-RAMarrivalClock;
        for (int i=0;i<pcb.getStartTimes().size();i++)
        {
            waitingTime=waitingTime-(pcb.getStopTimes().get(i)-pcb.getStartTimes().get(i));
        }

        return waitingTime;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        //time between first READY and first RUNNING state
        return pcb.getStartTimes().get(0)-RAMarrivalClock;
    }
    
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        //time between first READY state and TERMINATED state
        return pcb.getStopTimes().get(pcb.getStopTimes().size()-1)-RAMarrivalClock;
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
    
    public int getBurstTime(){return burstTime;}
    public void setBurstTime(int burstTime){this.burstTime=burstTime;}

    public int getArrivalTime(){return arrivalTime;}
    public void setRAMarrivalClock(int RAMarrivalClock){this.RAMarrivalClock=RAMarrivalClock;}
}