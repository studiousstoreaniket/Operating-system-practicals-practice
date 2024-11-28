import java.util.*;

class Process {
    int id, burstTime, priority, waitingTime, turnaroundTime;

    public Process(int id, int burstTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityScheduling {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Get number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        
        List<Process> processes = new ArrayList<>();
        
        // Get process details from user
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Process ID: ");
            int id = sc.nextInt();
            System.out.print("Enter Burst Time: ");
            int burstTime = sc.nextInt();
            System.out.print("Enter Priority: ");
            int priority = sc.nextInt();
            processes.add(new Process(id, burstTime, priority));
        }

        // Sort processes by priority (lower number = higher priority)
        processes.sort(Comparator.comparingInt(p -> p.priority));
        
        // Calculate waiting time and turnaround time
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        int completionTime = 0;

        for (Process p : processes) {
            p.waitingTime = completionTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
            completionTime += p.burstTime;
        }

        // Print results
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("Process ID | Burst Time | Priority | Waiting Time | Turnaround Time");
        System.out.println("-------------------------------------------------------------");
        for (Process p : processes) {
            System.out.printf("    %d     |     %d     |    %d     |     %d      |      %d\n", 
                p.id, p.burstTime, p.priority, p.waitingTime, p.turnaroundTime);
        }
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / n);
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTurnaroundTime / n);
        System.out.println("-------------------------------------------------------------");
        
        sc.close();
    }
}

