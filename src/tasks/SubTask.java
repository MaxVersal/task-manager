package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
     protected int epicID;

    public TypeOfTask getType() {
        return type;
    }

    public SubTask(String title, String description, Progress progress, LocalDateTime dateTime, Duration duration, int epicID){
        super( title, description,  progress, dateTime, duration);
        this.epicID = epicID;
        super.type = TypeOfTask.SUBTASK;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "epicID=" + epicID +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress=" + progress +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
