import java.util.*;

class MemoryAllocation {

    // Next Fit Memory Allocation
    public static void nextFit(int[] memory, int[] processes) {
        System.out.println("\n--- Next Fit Allocation ---");
        int lastIdx = -1; // Keeps track of the last allocated block
        for (int p : processes) {
            boolean allocated = false;
            for (int i = lastIdx + 1; i < memory.length; i++) {  // Start from last allocated block
                if (memory[i] >= p) {
                    memory[i] -= p;  // Allocate memory
                    lastIdx = i;  // Update the last allocated block index
                    System.out.println("Process of size " + p + " allocated to block of size " + memory[i]);
                    allocated = true;
                    break;
                }
            }
            if (!allocated) System.out.println("Process of size " + p + " could not be allocated.");
        }
    }

    // Worst Fit Memory Allocation
    public static void worstFit(int[] memory, int[] processes) {
        System.out.println("\n--- Worst Fit Allocation ---");
        for (int p : processes) {
            int worstIdx = -1, maxSpace = Integer.MIN_VALUE;
            for (int i = 0; i < memory.length; i++) {
                if (memory[i] >= p && memory[i] - p > maxSpace) {
                    maxSpace = memory[i] - p;  // Track the largest block
                    worstIdx = i;
                }
            }
            if (worstIdx != -1) {
                memory[worstIdx] -= p;  // Allocate memory
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

        // Run Next Fit
        nextFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Worst Fit
        // Run Worst Fit
        worstFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Worst Fit

        sc.close();
    }
}

