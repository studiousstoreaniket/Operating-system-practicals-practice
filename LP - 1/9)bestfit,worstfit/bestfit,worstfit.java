import java.util.*;

class MemoryAllocation {

    // Best Fit Memory Allocation
    public static void bestFit(int[] memory, int[] processes) {
        System.out.println("\n--- Best Fit Allocation ---");
        for (int p : processes) {
            int bestIdx = -1, minWaste = Integer.MAX_VALUE;
            for (int i = 0; i < memory.length; i++) {
                if (memory[i] >= p) {
                    int waste = memory[i] - p;  // Remaining space in the block
                    if (waste < minWaste) {
                        minWaste = waste;
                        bestIdx = i;  // Track the best block with minimum waste
                    }
                }
            }
            if (bestIdx != -1) {
                memory[bestIdx] -= p;  // Allocate the memory block
                System.out.println("Process of size " + p + " allocated to block of size " + memory[bestIdx]);
            } else {
                System.out.println("Process of size " + p + " could not be allocated.");
            }
        }
    }

    // Worst Fit Memory Allocation
    public static void worstFit(int[] memory, int[] processes) {
        System.out.println("\n--- Worst Fit Allocation ---");
        for (int p : processes) {
            int worstIdx = -1, maxSpace = Integer.MIN_VALUE;
            for (int i = 0; i < memory.length; i++) {
                if (memory[i] >= p) {
                    int waste = memory[i] - p;  // Remaining space in the block
                    if (waste > maxSpace) {
                        maxSpace = waste;
                        worstIdx = i;  // Track the worst block with maximum waste
                    }
                }
            }
            if (worstIdx != -1) {
                memory[worstIdx] -= p;  // Allocate the memory block
                System.out.println("Process of size " + p + " allocated to block of size " + memory[worstIdx]);
            } else {
                System.out.println("Process of size " + p + " could not be allocated.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input for memory blocks
        System.out.print("Enter number of memory blocks: ");
        int m = sc.nextInt();
        int[] memory = new int[m];
        System.out.println("Enter sizes of memory blocks:");
        for (int i = 0; i < m; i++) {
            memory[i] = sc.nextInt();
        }

        // Input for processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] processes = new int[n];
        System.out.println("Enter sizes of processes:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        // Run Best Fit
        bestFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Worst Fit
        // Run Worst Fit
        worstFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Worst Fit

        sc.close();
    }
}

