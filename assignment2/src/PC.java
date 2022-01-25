/**This class represents the PC where our program runs. */
public class PC {

    /**This is the main method.
     * @param args is main's parameter. */
    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */

        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 5),
                new Process(1,4,40),
                new Process(2,5,15),
                new Process(2,15,30),
                new Process(38, 5, 5),
                new Process(6,4,45),
                new Process(8,5,15),
                new Process(7,15,40),
                new Process(100,3,40),
                new Process(94,4,5)
        };
        final int[] availableBlockSizes = {40, 40,40}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new BestFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new RoundRobin();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }

}