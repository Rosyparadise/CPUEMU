/**This class represents a Process Control Block*/
import java.util.ArrayList;

public class ProcessControlBlock {
    
    private final int pid;
    private ProcessState state;
    // the following two ArrayLists should record when the process starts/stops
    // for statistical purposes
    private ArrayList<Integer> startTimes; // when the process starts running
    private ArrayList<Integer> stopTimes;  // when the process stops running
    
    private static int pidTotal= 0;

    /**Class Constructor.*/
    public ProcessControlBlock() {
        this.state = ProcessState.NEW;
        this.startTimes = new ArrayList<Integer>();
        this.stopTimes = new ArrayList<Integer>();
        /* TODO: you need to add some code here
         * Hint: every process should get a unique PID */
        pidTotal++;
        this.pid = pidTotal; // change this line
        
    }

    /**This method is a getter for the process' state.
     * @return the process' state*/
    public ProcessState getState() {
        return this.state;
    }

    /**This method is a setter for the process' state.
     * @param state the process' new state
     * @param currentClockTime the time on the clock when the state changes*/
    public void setState(ProcessState state, int currentClockTime)
    {
        /* TODO: you need to add some code here
         * Hint: update this.state, but also include currentClockTime
         * in startTimes/stopTimes */

        //in case process RUNNING->READY add a stop time
        if (this.state==ProcessState.RUNNING && state==ProcessState.READY)
            stopTimes.add(currentClockTime);
        //in case READY->RUNNING add a start time
        else if (state==ProcessState.RUNNING)
            startTimes.add(currentClockTime);
        //in case RUNNING->TERMINATED add stop time
        else if (state==ProcessState.TERMINATED)
            stopTimes.add(currentClockTime);

        //finally change the state to the new one
        this.state=state;
    }

    /**This method is a getter for the process' id.
     * @return the process' id*/
    public int getPid() {
        return this.pid;
    }

    /**This method is a getter for the array list that contains processes' start times.
     * @return the array list that contains processes' start times*/
    public ArrayList<Integer> getStartTimes() {
        return startTimes;
    }

    /**This method is a getter for the array list that contains processes' stop times.
     * @return the array list that contains processes' stop times*/
    public ArrayList<Integer> getStopTimes() {
        return stopTimes;
    }
    
}
