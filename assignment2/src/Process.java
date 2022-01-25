/**This class represents a process.*/
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    //saves the address the process is saved at
    private int address;
    //clock cycle it first got into a READY state
    private int RAMarrivalClock;

    /**Class constructor.
     *  @param arrivalTime the process' arrival time
     *  @param burstTime the process' burst time
     *  @param memoryRequirements the process' memory requirements in kB
     *  */
    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
        this.address= -1;
        RAMarrivalClock=-1;
    }

    /**This method is a getter for the process' PCB.
     * @return the process' PCB */
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    /**This method decreases the process' burst time whenever it runs.*/
    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */

        burstTime--;
        
    }

    /**This method changes the process' state to READY whenever it stops running.*/
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        pcb.setState(ProcessState.READY,CPU.clock);
        
    }

    /**This method calculates the process' waiting time.
     * @return This returns the waiting time of the process. */
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

    /**This method is a getter for the process' Response Time.
     * @return the process' response time*/
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        //time between first READY and first RUNNING state
        return pcb.getStartTimes().get(0)-RAMarrivalClock;
    }

    /**This method is a getter for the process' Turn Around Time.
     * @return the process' Turn Around Time*/
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        //time between first READY state and TERMINATED state
        return pcb.getStopTimes().get(pcb.getStopTimes().size()-1)-RAMarrivalClock;
    }

    /**This method is a getter for the process' Memory Requirements.
     * @return the process' Memory Requirements */
    public int getMemoryRequirements(){
        return memoryRequirements;
    }

    /**This method is a getter for the process' Address.
     * @return the process' Address */
    public int getAddress(){
        return address;
    }

    /**This method is a setter for the process' Address.
     * @param address the process' Address */
    public void setAddress(int address){
        this.address=address;
    }

    /**This method is a getter for the process' Burst Time.
     * @return the process' Burst Time */
    public int getBurstTime(){return burstTime;}

    /**This method is a setter for the process' Burst Time.
     * @param burstTime the process' Burst Time */
    public void setBurstTime(int burstTime){this.burstTime=burstTime;}

    /**This method is a getter for the process' Arrival Time.
     * @return the process' Arrival Time */
    public int getArrivalTime(){return arrivalTime;}

    /**This method is a setter for the process' Arrival Time on RAM.
     * @param RAMarrivalClock  the process' Arrival Time into RAM*/
    public void setRAMarrivalClock(int RAMarrivalClock){this.RAMarrivalClock=RAMarrivalClock;}
}