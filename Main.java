import java.util.Scanner;
import java.util.*;

import static java.lang.Math.ceil;

class Process {
    private int remainingTime;

    private String name;
    private String color;
    private int arrivalTime;
    private int completionTime;

    private int turnAround;

    private int burstTime;
    private int waitTime;
    private int QuantumTime;
    private int priority = 0;
    private int AGFactor = 0;
    public Process() {}

    public int getQuantumTime() {
        return QuantumTime;
    }

    public void setQuantumTime(int quantumTime) {
        QuantumTime = quantumTime;
    }

    public int getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(int turnAround) {
        this.turnAround = turnAround;
    }

    public Process(String name, String color, int arrivalTime, int burstTime, int priority, int Quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.QuantumTime = Quantum;
        this.remainingTime = burstTime;
    }

    public Process(String name, String color, int arrivalTime,int burstTime,int priority){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        color = color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAGFactor() {
        return AGFactor;
    }

    public void setAGFactor(int AGFactor) {
        this.AGFactor = AGFactor;
    }
}


class Scheduler {
    Queue<Process> processes;
    Queue<Process> OutProcesses = new LinkedList<>();
    Queue<Process> ReadyProcesses = new LinkedList<>();
    Process RunningProcess = new Process();
    int counter = 0 , executionTime=0 , counterOfProcesses = 0;
    Process oldProcess = new Process();
    public Scheduler(Queue<Process> processes){
        this.processes=processes;
    }

    public void SRTF(){
        int n = processes.size() , finished = 0, time = 0, agingThreshold = 7;
        double totalWait=0 , totalTurnaround=0;
        Queue<Process> ready = new LinkedList<>();
        Vector<String> names = new Vector<>();
        while (finished < n){
            for(Process pro : processes) {
                if (pro.getArrivalTime() <= time && pro.getRemainingTime() > 0) {
                    ready.add(pro);
                }
            }
            if(!ready.isEmpty()) {
                Process p = ready.element();
                int s = ready.size();
                for (int i = 1; i < s; i++) {
                    ready.poll();
                    if(p.getRemainingTime() > ready.element().getRemainingTime() && p.getPriority() <= ready.element().getPriority()){
                        p.setAGFactor(p.getAGFactor() + 1);
                        if (p.getAGFactor() >= agingThreshold) {
                            p.setPriority(p.getPriority() + 1);
                            p.setAGFactor(0);
                        }
                        p = ready.element();
                    }
                }
                for (Process process : ready) {
                    process.setAGFactor(process.getAGFactor() + 1);
                    if (process.getAGFactor() >= agingThreshold) {
                        process.setPriority(process.getPriority() + 1);
                        process.setAGFactor(0);
                    }
                    ready.poll();
                }
                if (names.isEmpty()) {
                    names.add(p.getName());
                }
                else if(!Objects.equals(p.getName(), names.get(names.size()-1))) {
                    names.add(p.getName());
                }
                p.setRemainingTime((p.getRemainingTime() - 1));
                if (p.getRemainingTime() == 0) {
                    p.setCompletionTime(time+1);
                    finished++;
                }
                time++;
            }else {
                time++;
            }
        }
        for (String name: names) {
            System.out.println("process "+ name +" is executing.");
        }
        for(Process pro : processes) {
            int turnaroundTime = pro.getCompletionTime() - pro.getArrivalTime();
            int waitTime = turnaroundTime - pro.getBurstTime();
            pro.setWaitTime(waitTime);
            System.out.println("for process "+ pro.getName() +" wait time = "+pro.getWaitTime()+" and turnaround time = "+turnaroundTime);
            totalWait += waitTime;
            totalTurnaround += turnaroundTime;
        }
        totalWait /=n;
        totalTurnaround /= n;
        System.out.println("Average waiting time = " + totalWait);
        System.out.println("Average turnaround time = " + totalTurnaround);
    }

    public void PriorityScheduling() {
        int agingThreshold = 5;
        int currentTime = 0;
        int numberOfProcesses = processes.size();
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        while (!processes.isEmpty()) {
            Process currentProcess = null;
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime) {
                    currentProcess = process;
                    break;
                }
            }
            if (currentProcess == null) {
                currentProcess = processes.poll();
            } else {
                for (Process process : processes) {
                    if (process.getArrivalTime() <= currentTime && process.getPriority() > currentProcess.getPriority()) {
                        currentProcess = process;
                    }
                }
                for (Process process : processes) {
                    if (process.getArrivalTime() <= currentTime) {
                        process.setAGFactor(process.getAGFactor() + 1);
                        if (process.getAGFactor() >= agingThreshold) {
                            process.setPriority(process.getPriority() + 1);
                            process.setAGFactor(0);
                        }
                    }
                }
                processes.remove(currentProcess);
            }
            System.out.println("Executing process: " + currentProcess.getName());
            currentProcess.setCompletionTime(currentTime + currentProcess.getBurstTime());
            currentProcess.setWaitTime(currentTime - currentProcess.getArrivalTime());
            currentProcess.setTurnAround(currentProcess.getWaitTime() + currentProcess.getBurstTime());
            System.out.println("Waiting Time for " + currentProcess.getName() + ": " + currentProcess.getWaitTime());
            System.out.println("Turnaround Time for " + currentProcess.getName() + ": " + currentProcess.getTurnAround());
            totalWaitingTime += currentProcess.getWaitTime();
            totalTurnaroundTime += currentProcess.getTurnAround();
            currentTime = currentProcess.getCompletionTime();
        }
        double averageWaitingTime = totalWaitingTime / numberOfProcesses;
        double averageTurnaroundTime = totalTurnaroundTime / numberOfProcesses;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }

    public void SJF(int contextSwitch) {
        ArrayList<Process> processList = new ArrayList<>(processes);
        int averageTurnaroundTime = 0;
        int averageWaitingTime = 0;
        processList.sort(Comparator.comparingInt(Process::getArrivalTime).thenComparing(Process::getBurstTime));
        int currentTime = contextSwitch;

        for (Process process : processList) {

            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
            //execute the process
            process.setWaitTime(currentTime - process.getArrivalTime());
            process.setCompletionTime(currentTime + process.getBurstTime());
            System.out.println(process.getName()+" is executing");
            System.out.println("Wait time = "+process.getWaitTime());
            System.out.println("Turnaround time = "+(process.getCompletionTime() - process.getArrivalTime()));
            System.out.println("------------------------------------");

            //calculating average
            averageTurnaroundTime += (process.getCompletionTime() - process.getArrivalTime());
            averageWaitingTime += process.getWaitTime();
            //updating the current time
            currentTime += process.getBurstTime() + contextSwitch;
        }
        averageTurnaroundTime /= processList.size();
        averageWaitingTime /= processList.size();
        System.out.println("Average waiting time = "+ averageWaitingTime);
        System.out.println("Average turnaround time = "+ averageTurnaroundTime);
    }
    public void AGScheduler(){
        int sum = 0 , numOfProcess=0;
        RunningProcess.setAGFactor(1000000000);
        oldProcess.setBurstTime(1000000000);
        ReadyProcesses.add(processes.element());
        while (!processes.isEmpty() || !ReadyProcesses.isEmpty()){
            while (processes.size()!= 0 && processes.element().getArrivalTime() <= counter){
                sum += processes.element().getQuantumTime();
                numOfProcess++;

                if (!ReadyProcesses.isEmpty() && ReadyProcesses.element().getAGFactor()<processes.element().getAGFactor()  ){
                    ReadyProcesses.add(processes.element());
                }else if(!ReadyProcesses.isEmpty() && ReadyProcesses.element().getAGFactor()>processes.element().getAGFactor()){
                    ReadyProcesses.add(ReadyProcesses.element());
                    ReadyProcesses.remove();
                    ReadyProcesses.add(processes.element());
                    for (int i = 0; i < ReadyProcesses.size()-1; i++) {
                        ReadyProcesses.add(ReadyProcesses.element());
                        ReadyProcesses.remove();
                    }
                }
                RunningProcess = ReadyProcesses.element();
                processes.remove();
            }
            if(executionTime != 0 && ReadyProcesses.element() == oldProcess){
                Process p;
                for (int i = 0; i < ReadyProcesses.size(); i++) {
                    if(ReadyProcesses.element().getAGFactor() < RunningProcess.getAGFactor()){
                        RunningProcess = ReadyProcesses.element();
                    }
                    ReadyProcesses.add(ReadyProcesses.element());
                    ReadyProcesses.remove();
                }
                if(RunningProcess != ReadyProcesses.element()){
                    p = ReadyProcesses.element();
                    ReadyProcesses.remove(ReadyProcesses.element());
                    ReadyProcesses.remove(RunningProcess);
                    ReadyProcesses.add(RunningProcess);
                    for (int i = 0; i < ReadyProcesses.size()-1; i++) {
                        ReadyProcesses.add(ReadyProcesses.element());
                        ReadyProcesses.remove();
                    }
                    ReadyProcesses.add(p);
                }

            }
            if(!ReadyProcesses.isEmpty() && ReadyProcesses.element() != oldProcess) {
                RunningProcess = ReadyProcesses.element();
                sum-=oldProcess.getQuantumTime();
                oldProcess.setQuantumTime((oldProcess.getQuantumTime() + executionTime));
                sum+=oldProcess.getQuantumTime();
                executionTime = RunningProcess.getQuantumTime();
            }
            System.out.println("The Running Process Is: "+  RunningProcess.getName() + " At Time: " + counter);
            System.out.println("The Quantum Time for process " + RunningProcess.getName() + " is: " + RunningProcess.getQuantumTime() +"\n__________________________________");

            double roundedNum =(double) RunningProcess.getQuantumTime()/2;
            if(RunningProcess.getBurstTime()<= ceil(roundedNum)&& executionTime == RunningProcess.getQuantumTime()){
                counter+=RunningProcess.getBurstTime();
                oldProcess = RunningProcess;
                RunningProcess.setQuantumTime(0);
                RunningProcess.setBurstTime(0);
                RunningProcess.setCompletionTime(counter);
                oldProcess.setTurnAround(RunningProcess.getCompletionTime() - RunningProcess.getArrivalTime() );
                oldProcess.setWaitTime(oldProcess.getTurnAround() - oldProcess.getWaitTime());
                executionTime = 0;
            }else if(RunningProcess.getBurstTime()>= ceil(roundedNum) && executionTime==RunningProcess.getQuantumTime()) {
                counter += ceil(roundedNum);
                RunningProcess.setBurstTime(RunningProcess.getBurstTime()-(int)ceil(roundedNum));
                oldProcess = RunningProcess;
                executionTime -= (int)ceil(roundedNum);
            }else {
                counter +=1;
                RunningProcess.setBurstTime(RunningProcess.getBurstTime()-1);
                oldProcess = RunningProcess;
                executionTime -= 1;
            }
            if(executionTime == 0 && oldProcess.getBurstTime() != 0){
                if(numOfProcess != 0 ){
                    int mean= (int) ceil(0.1*((double) sum/numOfProcess));
                    oldProcess.setQuantumTime(oldProcess.getQuantumTime()+mean);
                    if(!ReadyProcesses.isEmpty()){
                        ReadyProcesses.remove();
                        ReadyProcesses.add(oldProcess);
                    }
                }
            }else if(executionTime == 0 && oldProcess.getBurstTime() == 0){
                sum -= oldProcess.getQuantumTime();
                ReadyProcesses.remove();
                OutProcesses.add(oldProcess);
                executionTime = 4;
                numOfProcess --;

            }

        }
        System.out.println("______________________________\nProcesses execution order: ");
        int n = OutProcesses.size();
        int avgTurn = 0;
        int avgWait = 0;
        for (int i = 0; i < n; i++) {
            avgTurn += OutProcesses.element().getTurnAround();
            avgWait += OutProcesses.element().getWaitTime();
            System.out.println(OutProcesses.element().getName());
            System.out.println("Waiting Time of "+OutProcesses.element().getName()+":"+OutProcesses.element().getWaitTime());
            System.out.println("Turn Around Time of "+OutProcesses.element().getName()+":"+OutProcesses.element().getTurnAround());
            OutProcesses.remove();
            System.out.println("__________________________________________");
        }
        System.out.println("The Average Turn Around Time of processes:"+avgTurn/n);
        System.out.println("The Average Waiting Time of processes:"+avgWait/n);

    }
}



public class Main {
    public static void main(String[] args) {


        Scanner obj = new Scanner(System.in);
        Random random = new Random();
        Queue<Process> processes;
        Scheduler scheduler;


        System.out.println("Choose one of the following algorithms");
        System.out.println("1.SJF");
        System.out.println("2.SRTF");
        System.out.println("3.Priority Scheduling");
        System.out.println("4.AG");
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
                Queue<Process> enterProcesses = new LinkedList<>();
                int numOfProcess , RRTime , contextSwitching , randomVal;
                System.out.println("Enter the number of processes: ");
                numOfProcess = obj.nextInt();
                System.out.println("Enter the Round Robin Time Quantum: ");
                RRTime = obj.nextInt();
                System.out.println("Enter the context switching: ");
                contextSwitching = obj.nextInt();
                for (int i = 0; i < numOfProcess; i++) {
                    String name, color;
                    int arrTime, burstTime, priorityNum;
                    System.out.println("Enter the Name Of The Process "+(i+1)+": ");
                    name = obj.next();
                    System.out.println("Enter the Color Of The Process "+(i+1)+": ");
                    color = obj.next();
                    System.out.println("Enter Arrival Time Of The Process "+(i+1)+": ");
                    arrTime = obj.nextInt();
                    System.out.println("Enter the Burst Time Of The Process "+(i+1)+": ");
                    burstTime = obj.nextInt();
                    System.out.println("Enter the Priority Of The Process "+(i+1)+": ");
                    priorityNum = obj.nextInt();
                    Process p = new Process(name , color , arrTime , burstTime, priorityNum , RRTime);
                    p.setWaitTime(burstTime);
                    randomVal = random.nextInt(20);
                    if (randomVal<10){
                        p.setAGFactor(randomVal + p.getArrivalTime() + p.getBurstTime());
                    }else if(randomVal == 10){
                        p.setAGFactor(priorityNum + p.getArrivalTime() + p.getBurstTime());
                    }else{
                        p.setAGFactor(10 + p.getArrivalTime() + p.getBurstTime());
                    }

                    enterProcesses.add(p);
                }
                scheduler = new Scheduler(enterProcesses);
                scheduler.AGScheduler();
            }
            default:
                System.out.println("See You Next time");
        }
    }




//        Process p = new Process("p1" , "red" , 0 , 17, 4 , 4 , 20);
//        Process p2 = new Process("p2" , "blue" , 3 , 6, 9 , 4 , 17);
//        Process p3 = new Process("p3" , "green" , 4 , 10, 2 , 4 , 16);
//        Process p4 = new Process("p4" , "black" , 29 , 4, 8 , 4 , 43);
//
//
//        enterProcesses.add(p);
//        enterProcesses.add(p2);
//        enterProcesses.add(p3);
//        enterProcesses.add(p4);

}
