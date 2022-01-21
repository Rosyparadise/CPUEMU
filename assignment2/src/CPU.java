import java.util.ArrayList;

public class CPU {
    
    public static int clock = 0; // this should be incremented on every CPU cycle
    
    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private ArrayList<Process> list_processes;
    private int currentProcess;
    private Process currentObjproc;
    private boolean enoughIsEnough;
    private boolean largerThan;
    private boolean RRchange;
    private int tempQuantum;

    
    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
        list_processes = new ArrayList<>();
        currentProcess=-1;
        currentObjproc=null;
        enoughIsEnough=false;
        largerThan=false;
        if (scheduler instanceof RoundRobin)
        {
            tempQuantum=0;
            RRchange=false;
        }



        for (int i=0;i<processes.length;i++)
        {
            list_processes.add(processes[i]);
        }
    }
    //FOR SRTF= IF ITS NULL INFINITELY MANY PROCESSES MAY BE ADDED, IF ITS NOT ONLY ONE AT A TIME.
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
                        System.out.println(clock+"  new process ");
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
    
    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */
        while (!enoughIsEnough)
            tick();
        
    }
    
    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        //if it fits add proccess to Scheduler.proccesses
        if (currentProcess==-1 || (scheduler instanceof SRTF && !largerThan))
        {
            if (nextProcess()) {

                //clock++;
                return;
            }
        }


        if (scheduler instanceof FCFS && currentProcess==-1)
        {
            System.out.println(clock+" IN FCFS");

            Process temp = scheduler.getNextProcess();
            if (temp!=null)
            {
                currentObjproc = temp;
                currentProcess=currentObjproc.getAddress();
                System.out.println(clock+1+" IN FCFS");
                clock+=2;
                return;
            }

        }

        else if (scheduler instanceof SRTF && (largerThan || currentProcess==-1))
        {
            System.out.println(clock+" IN SRTF");

            Process temp = scheduler.getNextProcess();
            if (temp!=null)
            {
                currentObjproc = temp;
                currentProcess=currentObjproc.getAddress();
                System.out.println(clock+1+" IN SRTF");
                clock+=2;
                largerThan=false;
                return;
            }


        }

        else if (scheduler instanceof RoundRobin && (currentProcess==-1 || RRchange))
        {
            Process temp = scheduler.getNextProcess();
            if (temp!=null)
            {
                currentObjproc = temp;
                currentProcess=currentObjproc.getAddress();
                clock+=2;
                return;
            }
            RRchange=false;
        }

        //CAN MERGE ALL 3 ALGORITHMS INTO ONE IF.


        if (currentProcess!=-1)
        {
            currentObjproc.setBurstTime(currentObjproc.getBurstTime()-1);
            System.out.println(clock+" burst time reduced to "+ currentObjproc.getBurstTime()+" ADDRESS IS "+ currentObjproc.getAddress());
            if (scheduler instanceof RoundRobin) {
                tempQuantum++;
                if (tempQuantum== ((RoundRobin) scheduler).getQuantum())
                {
                    RRchange=true;
                    tempQuantum=0;
                }

            }
            if (currentObjproc.getBurstTime() == 0) {
                currentObjproc.getPCB().setState(ProcessState.TERMINATED, clock);
                mmu.loadProcessIntoRAM(null);
                scheduler.processes.remove(currentObjproc);
                currentObjproc = null;
                currentProcess = -1;
            }
        }
        if ( list_processes.size()==0 && currentProcess==-1 && scheduler.processes.size()==0) {
            System.out.println(clock+" NO OPERATION");
            enoughIsEnough = true;
        }
        clock++;
    }

}
