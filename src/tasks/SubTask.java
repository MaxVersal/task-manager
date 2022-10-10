package tasks;

import java.util.Objects;

public class SubTask extends Task {
     protected int epicID;

     protected TypeOfTask type = TypeOfTask.SUBTASK;

    public TypeOfTask getType() {
        return type;
    }

    public SubTask(String title, String description, Progress progress, int epicID){
        super( title, description,  progress);
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
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress='" + progress + '\'' +
                '}';
    }
}
