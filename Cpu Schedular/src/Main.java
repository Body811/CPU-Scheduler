import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Queue;
public class Main {
    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        int numOfProcess , RRTime , contextSwitching;
        System.out.print("Enter the number of processes: ");
        numOfProcess = obj.nextInt();
        System.out.print("Enter the Round Robin Time Quantum: ");
        RRTime = obj.nextInt();
        System.out.print("Enter the context switching: ");
        contextSwitching = obj.nextInt();
        Queue<Process> processes = new LinkedList<Process>();
        for (int i = 0; i < numOfProcess; i++) {
            String name, color;
            int arrTime, burstTime, priorityNum;
            System.out.print("Enter the process name: ");
            name = obj.next();
            System.out.print("Enter the process color: ");
            color = obj.next();
            System.out.print("Enter the arrival time: ");
            arrTime = obj.nextInt();
            System.out.print("Enter the burst time: ");
            burstTime = obj.nextInt();
            System.out.print("Enter the priority num: ");
            priorityNum = obj.nextInt();
            Process p = new Process(name,color,arrTime,burstTime,priorityNum);
            processes.add(p);
        }
        Scheduler scheduler = new Scheduler(processes);
        scheduler.SRTF();
        scheduler.PriorityScheduling();
    }
}