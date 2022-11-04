package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
     protected int epicID;

     protected TypeOfTask type = TypeOfTask.SUBTASK;

    public TypeOfTask getType() {
        return type;
    }

    public SubTask(String title, String description, Progress progress, LocalDateTime dateTime, Duration duration, int epicID){
        super( title, description,  progress, dateTime, duration);
        this.epicID = epicID;
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
