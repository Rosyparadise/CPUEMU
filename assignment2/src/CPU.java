import java.util.ArrayList;

/**This class represents a Central Processing Unit. */
public class CPU {
    
    public static int clock = 0; // this should be incremented on every CPU cycle

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    //processes array is copied here. Easier to work with.
    private ArrayList<Process> list_processes;
    //USED FOR STATS
    private ArrayList<Process> list_processes_STATS;
    private int currentProcess;
    //saves the currect object running on the cpu
    private Process currentObjproc;
    //flag that is used to decide when the program is terminated
    private boolean enoughIsEnough;
    //used to check if the most recently available process has a smaller burst time than the one currently running. (In case SRTF is used)
    private boolean largerThan;
    // used to represent if the quantum has reached its end. (In case RoundRobin is used)
    private boolean RRchange;
    //true in case a process has been terminated in the current cycle. (Is used to correctly identify an end state of the program)
    private boolean removed;
    // starts from 0 and adds up every cpu cycle until it reaches the quantum of RR
    private int tempQuantum;

    /**Class constructor.
     * @param scheduler the base class for the scheduler algorithms
     * @param mmu the memory management unit
     * @param processes an array list containing the processes that should be completed.
     */
    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
        list_processes = new ArrayList<>();
        list_processes_STATS = new ArrayList<>();
        currentProcess=-1;
        currentObjproc=null;
        enoughIsEnough=false;
        largerThan=false;
        RRchange=false;
        tempQuantum=0;


        for (int i=0;i<processes.length;i++)
        {
            if (processes[i].getBurstTime()>0) {
                list_processes.add(processes[i]);
                list_processes_STATS.add(processes[i]);
            }
        }
        //all processes sorted in increasing order based on their arrival time
        for (int i = 0; i < list_processes.size(); i++)
        {
            int min = list_processes.get(i).getArrivalTime();
            int minId = i;
            for (int j = i+1; j < list_processes.size(); j++) {
                if (list_processes.get(j).getArrivalTime() < min) {
                    min = list_processes.get(j).getArrivalTime();
                    minId = j;
                }
            }
            Process temp = list_processes.get(i);
            list_processes.set(i,list_processes.get(minId));
            list_processes.set(minId,temp);
        }
    }

    /**This method loads process into RAM when a process is terminated or an interruption has occurred.
     * @return true if the process was able to fit into the RAM, false if not.
     * */
    private boolean nextProcess()
    {
        for (int i=0;i<list_processes.size();i++)
        {
            if (list_processes.get(i).getArrivalTime()<=clock)
            {
                if (currentProcess!=-1&&list_processes.get(i).getBurstTime()<currentObjproc.getBurstTime())
                    largerThan=true;
                if (largerThan ||currentProcess==-1)
                {
                    if(mmu.loadProcessIntoRAM(list_processes.get(i)))
                    {
                        System.out.println(clock+"  NEW->READY ");
                        clock++;
                        scheduler.addProcess(list_processes.get(i));
                        list_processes.remove(list_processes.get(i));
                        return true;
                    }
                    else
                        largerThan=false;
                }
            }
        }
        return false;
    }

    /**This method runs each clock cycle until enoughIsEnough is true.
     * */
    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */
        while (!enoughIsEnough)
            tick();

        for (int i=0;i<list_processes.size();i++)
        {
            list_processes_STATS.remove(list_processes.get(i));
        }


        //Stat testing can be done here using list_processes_STATS
    }

    /**This method represents a clock cycle. It gets a process, schedules it and checks if the program needs to
     * be terminated.
     * */
    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        removed=false;
        //gets next process if the process got terminated or quantum ended or new process has smaller bursttime.
        if (currentProcess==-1 || (scheduler instanceof SRTF && !largerThan))
        {
            if (nextProcess()) {
                //clock++;
                return;
            }
        }

        //scheduling algorithm is called when its time for a new process to start
        if (currentProcess==-1 || largerThan || RRchange)
        {
            Process temp = scheduler.getNextProcess();
            if (temp!=null)
            {
                currentObjproc = temp;
                currentProcess=currentObjproc.getAddress();
                System.out.println(clock+" READY->RUNNING");
                System.out.println(clock+1+" READY->RUNNING");
                clock+=2;
                largerThan=false;
                RRchange=false;
                return;
            }
            System.out.println(clock +" NO OPERATION");
        }

        //process is in CPU
        if (currentProcess!=-1)
        {
            //burst time is reduced by 1
            currentObjproc.run();
            System.out.print(clock+" BURST TIME REDUCED TO "+ currentObjproc.getBurstTime()+" ADDRESS IS "+ currentObjproc.getAddress());
            //when the process is finished it gets terminated and its space freed up.
            if (currentObjproc.getBurstTime() == 0) {
                System.out.print(" RUNNING -> TERMINATED");
                currentObjproc.getPCB().setState(ProcessState.TERMINATED, clock);
                mmu.loadProcessIntoRAM(null);
                scheduler.removeProcess(currentObjproc);
                currentObjproc = null;
                currentProcess = -1;
                removed=true;
            }
            System.out.println();
        }
        //in case RR is used count how much of the quantum is left.
        if (scheduler instanceof RoundRobin)
        {
            tempQuantum++;
            if (tempQuantum== ((RoundRobin) scheduler).getQuantum() || removed)
            {
                RRchange=true;
                tempQuantum=0;
                currentObjproc = null;
                currentProcess = -1;
            }
        }
        /*program is terminated when there are no processes waiting to be brought to RAM, no processes in ram and no process in CPU.
        * its also terminated when there is a process waiting to be situated in a memory slot but there can never be enough room for it.*/
        if ( list_processes.size()==0 && currentProcess==-1 && scheduler.processes.size()==0 ||(currentProcess==-1 && scheduler.processes.size()==0 && clock+1>list_processes.get(list_processes.size()-1).getArrivalTime() && !removed))
        {
            System.out.println("PROCESSES LEFT IN NEW STATE: "+list_processes.size());
            enoughIsEnough = true;
        }
        clock++;
    }

}
