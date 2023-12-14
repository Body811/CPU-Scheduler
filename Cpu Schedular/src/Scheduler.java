import java.util.*;

public class Scheduler {
//    Queue<Process> processes;
    Process[] processes;
    public Scheduler(Process[] processes){
        this.processes=processes;
    }
    public void SRTF(){
        int n = processes.length , finished = 0, time = 0, totalWait=0 , totalTurnaround=0;
        Vector<Process> ready = new Vector<>();
        Vector<String> names = new Vector<>();
        while (finished < n){
            for(Process pro : processes) {
                if (pro.getArrivalTime() <= time && pro.getRemainingTime() > 0) {
                    ready.add(pro);
                }
            }
            if(!ready.isEmpty()) {
                Process p = ready.get(0);
                for (int i = 1; i < ready.size(); i++) {
                    if(p.getRemainingTime() > ready.get(i).getRemainingTime() && p.getPriority() < ready.get(i).getPriority()){
                        p = ready.get(i);
                    }
                }
                ready.remove(p);
                for (Process waitingPro: ready) {
                    waitingPro.setPriority(waitingPro.getPriority()+1);
                }
                ready.clear();
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
}
