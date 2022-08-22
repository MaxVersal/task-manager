package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final List<Task> history = new ArrayList<>();

    private int newId;

    @Override
    public void add(Task simpleTask){
        history.add(simpleTask);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    public List<Task> getHistory(){
        return history;
    }
}
