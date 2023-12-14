import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Queue;
public class Main {
    public static void main(String[] args) {
        Scheduler scheduler;
        Scanner obj = new Scanner(System.in);
        Queue<Process> processes;

        System.out.println("Choose one of the following algorithms");
        System.out.println("1.SJF");
        System.out.println("2.SRTF");
        System.out.println("3.Priority Scheduling");
        System.out.println("4.RR");
        System.out.print(">>");
        int choice = Integer.parseInt(obj.nextLine());
        switch (choice){
            case 1:{
                System.out.print("Enter the context switching: ");
                int contextSwitching = obj.nextInt();
                System.out.print("Enter the number of processes: ");
                int numOfProcess = obj.nextInt();
                processes = new LinkedList<Process>();
                for (int i = 0; i < numOfProcess; i++) {

                    System.out.print("Enter the process name: ");
                    String name = obj.next();
                    System.out.print("Enter the process color: ");
                    String color = obj.next();
                    System.out.print("Enter the arrival time: ");
                    int arrTime = obj.nextInt();
                    System.out.print("Enter the burst time: ");
                    int burstTime = obj.nextInt();
                    Process p = new Process(name,color,arrTime,burstTime,0);
                    processes.add(p);
                }
                scheduler = new Scheduler(processes);
                scheduler.SJF(contextSwitching);
                break;
            }
            case 2: {
                System.out.print("Enter the number of processes: ");
                int numOfProcess = obj.nextInt();
                processes = new LinkedList<Process>();
                for (int i = 0; i < numOfProcess; i++) {

                    System.out.print("Enter the process name: ");
                    String name = obj.next();
                    System.out.print("Enter the process color: ");
                    String color = obj.next();
                    System.out.print("Enter the arrival time: ");
                    int arrTime = obj.nextInt();
                    System.out.print("Enter the burst time: ");
                    int burstTime = obj.nextInt();
                    Process p = new Process(name, color, arrTime, burstTime, 0);
                    processes.add(p);
                }
                scheduler = new Scheduler(processes);
                scheduler.SRTF();
                break;
            }
            case 3: {
                System.out.print("Enter the number of processes: ");
                int numOfProcess = obj.nextInt();
                processes = new LinkedList<Process>();
                for (int i = 0; i < numOfProcess; i++) {

                    System.out.print("Enter the process name: ");
                    String name = obj.next();
                    System.out.print("Enter the process color: ");
                    String color = obj.next();
                    System.out.print("Enter the arrival time: ");
                    int arrTime = obj.nextInt();
                    System.out.print("Enter the burst time: ");
                    int burstTime = obj.nextInt();
                    System.out.print("Enter the priority num: ");
                    int priorityNum = obj.nextInt();
                    Process p = new Process(name, color, arrTime, burstTime, priorityNum);
                    processes.add(p);
                }
                scheduler = new Scheduler(processes);
                scheduler.PriorityScheduling();
                break;
            }
            case 4:{
                System.out.print("Enter the Round Robin Time Quantum: ");
                int RRTime = obj.nextInt();
                System.out.print("Enter the number of processes: ");
                int numOfProcess = obj.nextInt();
                processes = new LinkedList<Process>();
                for (int i = 0; i < numOfProcess; i++) {

                    System.out.print("Enter the process name: ");
                    String name = obj.next();
                    System.out.print("Enter the process color: ");
                    String color = obj.next();
                    System.out.print("Enter the arrival time: ");
                    int arrTime = obj.nextInt();
                    System.out.print("Enter the burst time: ");
                    int burstTime = obj.nextInt();
                    Process p = new Process(name, color, arrTime, burstTime, 0);
                    processes.add(p);
                }
                scheduler = new Scheduler(processes);
//                scheduler.RR(RRtime);
                break;
            }
            default:
                System.out.println("Invalid Choice");
        }
    }
}