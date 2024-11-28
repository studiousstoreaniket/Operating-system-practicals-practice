import java.util.*;

class MemoryAllocation {

    // First Fit Memory Allocation
    public static void firstFit(int[] memory, int[] processes) {
        System.out.println("\n--- First Fit Allocation ---");
        for (int p : processes) {
            boolean allocated = false;
            for (int i = 0; i < memory.length; i++) {
                if (memory[i] >= p) {
                    memory[i] -= p;  // Allocate the memory block
                    System.out.println("Process of size " + p + " allocated to block of size " + memory[i]);
                    allocated = true;
                    break;
                }
            }
            if (!allocated) System.out.println("Process of size " + p + " could not be allocated.");
        }
    }

    // Best Fit Memory Allocation
    public static void bestFit(int[] memory, int[] processes) {
        System.out.println("\n--- Best Fit Allocation ---");
        for (int p : processes) {
            int bestIdx = -1, minWaste = Integer.MAX_VALUE;
            for (int i = 0; i < memory.length; i++) {
                if (memory[i] >= p) {
                    int waste = memory[i] - p;
                    if (waste < minWaste) {
                        minWaste = waste;
                        bestIdx = i;
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

        // Run First Fit
        firstFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Best Fit
        // Run Best Fit
        bestFit(Arrays.copyOf(memory, memory.length), processes);  // Make a copy to reset for Best Fit

        sc.close();
    }
}

