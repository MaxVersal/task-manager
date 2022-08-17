public class SimpleTask extends Task {

    public SimpleTask(String title, String description, String progress){
        super(title, description,  progress);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progress='" + progress + '\'' +
                '}';
    }
}
