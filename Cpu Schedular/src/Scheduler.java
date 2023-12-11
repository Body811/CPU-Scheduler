import java.util.Queue;
public class Scheduler {
    Queue<Process> processes;
    public Scheduler(Queue<Process> processes){
        this.processes=processes;
    }
}
