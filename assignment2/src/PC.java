
public class PC {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */
        
        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 5),
                new Process(1,4,5),
                new Process(2,5,15),
                new Process(15,1,40),
                new Process(4, 5, 5),
                new Process(6,4,5),
                new Process(8,5,15),
                new Process(17,15,40),
                new Process(100,3,40),
                new Process(106,1,500)
                //new Process(8,3,1)
        };
        final int[] availableBlockSizes = {20, 5,40}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new BestFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new SRTF();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }
    
}
