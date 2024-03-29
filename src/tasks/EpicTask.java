package tasks;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task{

    protected ArrayList<SubTask> subs;

    public TypeOfTask getType() {
        return type;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(subs, epicTask.subs) && type == epicTask.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subs, type);
    }

    public EpicTask(String title, String description, Progress progress){
        super(title, description, progress, LocalDateTime.now(),Duration.ZERO);
        subs = new ArrayList<>();
        super.type = TypeOfTask.EPIC;
    }

    public EpicTask(String title, String description, Progress progress, LocalDateTime dateTime, Duration duration){
        super(title, description, progress, dateTime, duration);
        subs = new ArrayList<>();
        super.type = TypeOfTask.EPIC;
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
    public LocalDateTime getEndTime(){
        Duration durationSubs = Duration.ZERO;
        for (SubTask sub : subs){
            durationSubs = durationSubs.plusMinutes(sub.getDuration().toMinutes());
        }
        return this.startTime.plusMinutes(durationSubs.toMinutes());
    }

    public LocalDateTime calculateStartTime() {
        LocalDateTime minStartTime = LocalDateTime.MAX;
        if (this.subs.isEmpty()) {
            return LocalDateTime.now(); //если подзадач нет, то ставится текущее время
        } else {
            for (SubTask sub : subs){
                if (sub.getStartTime().isBefore(minStartTime)){
                    minStartTime = sub.getStartTime();
                }
            }
        }
        return minStartTime;
    }

    public Duration calculateSummaryDuration(){
        Duration duration = Duration.ZERO;
        for (SubTask sub : subs){
            duration = duration.plusMinutes(sub.getDuration().toMinutes());
        }
        return duration;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "subs=" + subs +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress=" + progress +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public void setType(TypeOfTask type) {
        this.type = type;
    }
}
