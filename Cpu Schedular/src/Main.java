import java.util.Scanner;
import java.util.Queue;
public class Main {
    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        int numOfProcess , RRTime , contextSwitching;
        System.out.println("Enter the number of processes: ");
        numOfProcess = obj.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        RRTime = obj.nextInt();
        System.out.println("Enter the context switching: ");
        contextSwitching = obj.nextInt();
        Queue<Process> processes = null;
        for (int i = 0; i < numOfProcess; i++) {
            String name, color;
            int arrTime, burstTime, priorityNum;
            System.out.println("Enter the context switching: ");
            name = obj.next();
            System.out.println("Enter the context switching: ");
            color = obj.next();
            System.out.println("Enter the context switching: ");
            arrTime = obj.nextInt();
            System.out.println("Enter the context switching: ");
            burstTime = obj.nextInt();
            System.out.println("Enter the context switching: ");
            priorityNum = obj.nextInt();
            Process p = new Process(name,color,arrTime,burstTime,priorityNum);
            processes.add(p);
        }
        Scheduler scheduler = new Scheduler(processes);
    }
}