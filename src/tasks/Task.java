package tasks;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;

    protected Progress progress;

    protected TypeOfTask type = TypeOfTask.TASK;

    public TypeOfTask getType() {
        return type;
    }

    public Task(String title, String description, Progress progress) {
        this.title = title;
        this.description = description;
        this.progress = progress;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && progress == task.progress && type == task.type;
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

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress='" + progress + '\'' +
                '}';
    }
}
