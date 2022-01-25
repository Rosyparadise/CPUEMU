import java.util.ArrayList;

/**This class represents a Scheduler. */
public abstract class Scheduler {
    
    protected ArrayList<Process> processes; // the list of processes to be executed

    /**Class Constructor. */
    public Scheduler() {
        this.processes = new ArrayList<Process>();
    }
    
    /** The addProcess() method should add a new process to the list of
     * processes that are candidates for execution. This will probably
     * differ for different schedulers
     * @param p  a process*/
    public abstract void addProcess(Process p);
    
    /** The removeProcess() method should remove a process from the list
     * of processes that are candidates for execution. Common for all
     * schedulers.
     * @param p a process*/
    public void removeProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.remove(p);
        
    }
    
    /** the getNextProcess() method should return the process that should
     * be executed next by the CPU
     * @return the process the will be executed next**/
    public abstract Process getNextProcess();
}
