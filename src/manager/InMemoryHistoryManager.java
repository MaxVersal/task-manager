package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager{

    protected static HashMap<Integer, Task> tasks = new HashMap<>();

    protected static ArrayList <Task> history = new ArrayList<>();

    private int newId;

    @Override
    public void add(Task simpleTask){
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
        history.add(simpleTask);
    }

    public ArrayList<Task>  getHistory(){
        return history;
    }
}
