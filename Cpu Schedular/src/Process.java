public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int completionTime;
    private int burstTime;
    private int waitTime;
    private int turnaroundTime;
    private int priority = 0;
    private int AGFactor = 0;
    private int remainingTime;


    public Process(String name, String color, int arrivalTime, int completionTime, int burstTime, int waitTime, int priority, int AGFactor) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.completionTime = completionTime;
        this.burstTime = burstTime;
        this.waitTime = waitTime;
        this.priority = priority;
        this.AGFactor = AGFactor;
        this.remainingTime=burstTime;
    }
    public Process(){}
    public Process(String name, String color, int arrivalTime,int burstTime,int priority){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime=burstTime;
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
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
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

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

}
