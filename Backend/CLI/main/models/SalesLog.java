package main.models;

public class SalesLog {
    private int id;
    private String timeAndDate;
    private String log;


    public SalesLog(int id, String timeAndDate, String log) {
        this.id = id;
        this.timeAndDate = timeAndDate;
        this.log = log;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public String getLog() {
        return log;
    }

}
