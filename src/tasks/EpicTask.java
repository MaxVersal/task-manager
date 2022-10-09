package tasks;
import java.util.ArrayList;

public class EpicTask extends Task{

    protected ArrayList<SubTask> subs;

    public TypeOfTask getType() {
        return type;
    }

    protected TypeOfTask type = TypeOfTask.EPIC;

    public EpicTask(String title, String description, Progress progress){
        super(title, description, progress);
        subs = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubs() {
        return subs;
    }

    public void setSubs(ArrayList<SubTask> subs) {
        this.subs = subs;
    }

    public void add(SubTask subTask){
        subs.add(subTask);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "subs=" + subs +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress='" + progress + '\'' +
                '}';
    }
}
