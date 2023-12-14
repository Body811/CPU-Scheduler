import java.util.*;

public class Scheduler {
    Queue<Process> processes;
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
            currentProcess.setTurnaroundTime(currentProcess.getWaitTime() + currentProcess.getBurstTime());
            System.out.println("Waiting Time for " + currentProcess.getName() + ": " + currentProcess.getWaitTime());
            System.out.println("Turnaround Time for " + currentProcess.getName() + ": " + currentProcess.getTurnaroundTime());
            totalWaitingTime += currentProcess.getWaitTime();
            totalTurnaroundTime += currentProcess.getTurnaroundTime();
            currentTime = currentProcess.getCompletionTime();
        }
        double averageWaitingTime = totalWaitingTime / numberOfProcesses;
        double averageTurnaroundTime = totalTurnaroundTime / numberOfProcesses;
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
