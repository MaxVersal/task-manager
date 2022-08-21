package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;


public interface TaskManager {
    public void printInfoEpicTasks();

    public void printInfoSubTasks();

    public void printInfoTask();

    public void removeEpicTask(int id);

    public void removeSubTask(int id);

    public void removeTask(int id);

    public EpicTask getEpicTaskById(int id);

    public Task getTaskById(int id);

    public SubTask getSubTaskById(int id);

    public void removeAllSubTasks();

    public void removeAllEpicTasks();

    public void removeAllTasks();

    public void addTask(Task simpleTask);

    public void addEpicTask(EpicTask epicTask);

    public void addSubTask(SubTask subTask);

    public void updateEpicTask(EpicTask epicTask);

    public void updateTask(Task simpleTask);

    public void updateSubTask(SubTask subTask);

    public String getSubTasksFromEpic(EpicTask epicTask);

    public ArrayList<Task> getHistory();

    public void updateHistory();


}
