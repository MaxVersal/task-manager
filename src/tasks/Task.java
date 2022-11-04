package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
    protected String title;
    protected String description;
    protected int id;

    protected Progress progress;

    protected TypeOfTask type = TypeOfTask.TASK;

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    protected Duration duration;

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    protected LocalDateTime startTime;

    public TypeOfTask getType() {
        return type;
    }

    public Task(String title, String description, Progress progress, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, progress, type);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public LocalDateTime getEndTime(){
        return startTime.plusMinutes(duration.toMinutes());
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress=" + progress +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && progress == task.progress && type == task.type && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int compareTo(Task o1){
        if (this.getStartTime().isBefore(o1.getStartTime())) return -1;
        else if (this.getStartTime().isAfter(o1.getStartTime())) return 1;
        else return 0;
    }
}
