package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    void printInfoEpicTasks();

    void printInfoSubTasks();

    void printInfoTask();

    void removeEpicTask(int id);

    void removeSubTask(int id);

    void removeTask(int id);

    EpicTask getEpicTaskById(int id);

    Task getTaskById(int id);

    SubTask getSubTaskById(int id);

    void removeAllSubTasks();

    void removeAllEpicTasks();

    void removeAllTasks();

    void addTask(Task simpleTask);

    void addEpicTask(EpicTask epicTask);

    void addSubTask(SubTask subTask);

    void updateEpicTask(EpicTask epicTask);

    void updateTask(Task simpleTask);

    void updateSubTask(SubTask subTask);

    String getSubTasksFromEpic(EpicTask epicTask);

    List<Task> getHistory();

    int getNewId();

    void setNewId(int id);

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, EpicTask> getEpicTasks();

    HashMap<Integer, SubTask> getSubTasks();

    Task findTask(int id);
}
