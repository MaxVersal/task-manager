package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;
import tasks.Progress;

import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int newId = 1;

    @Override
    public void printInfoEpicTasks() {
        System.out.println("Информация о составных задачах:");
        String epicTasksInfo = epicTasks.toString();
        System.out.println(epicTasksInfo);
    }

    @Override
    public void printInfoSubTasks() {
        System.out.println("Информация о подзадачах: ");
        String subTaskInfo = subTasks.toString();
        System.out.println("subTaskInfo");
    }

    @Override
    public void printInfoTask() {
        System.out.println("Информация о обычных задачах: ");
        String simpleTasksInfo = tasks.toString();
        System.out.println(simpleTasksInfo);
    }

    @Override
    public void removeEpicTask(int id) {

        for (SubTask sub : epicTasks.get(id).getSubs()){
            historyManager.remove(sub.getId()); // удаляем сабы из истории
        }
        epicTasks.remove(id);
        historyManager.remove(id);

    }

    @Override
    public void removeSubTask(int id) {
        SubTask s = subTasks.get(id);
        EpicTask e = epicTasks.get(s.getEpicID());
        e.getSubs().remove(s);
        subTasks.remove(id);
        updateEpicTask(e);
        historyManager.remove(id);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        historyManager.add(epicTasks.get(id));
        return epicTasks.get(id);
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);

    }

    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.clear();
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
            updateEpicTask(value);
        }
    }

    @Override
    public void removeAllEpicTasks() {
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
        }
        epicTasks.clear();
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void addTask(Task simpleTask) {
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
    }

    @Override
    public void addEpicTask(EpicTask epicTask) {
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpicTask(epicTask);

    }

    @Override
    public  void addSubTask(SubTask subTask) {
        subTask.setId(newId++);
        subTasks.put(subTask.getId(), subTask);
        EpicTask e = epicTasks.get(subTask.getEpicID());
        e.getSubs().add(subTask);
        updateEpicTask(e);
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        String progress = "";
        int quantityDONE = 0;
        int quantityINPROGRESS = 0;
        int quantityNEW = 0;
        for (SubTask sub : epicTask.getSubs()) {
            if (sub.getProgress().equals(Progress.IN_PROGRESS)) {
                quantityINPROGRESS += 1;
            } else if (sub.getProgress().equals(Progress.DONE)) {
                quantityDONE += 1;
            } else if (sub.getProgress().equals(Progress.NEW)) {
                quantityNEW += 1;
            }
        }

        if (quantityINPROGRESS > 0) {
            epicTask.setProgress(Progress.IN_PROGRESS);
        } else if (quantityNEW == epicTask.getSubs().size() || epicTask.getSubs().isEmpty()) {
            epicTask.setProgress(Progress.NEW);
        } else if (quantityDONE == epicTask.getSubs().size()) {
            epicTask.setProgress(Progress.DONE);
        } else {
            epicTask.setProgress(Progress.IN_PROGRESS); // случай, когда у сабтасков статусы NEW И DONE
        }


    }

    @Override
    public void updateTask(Task simpleTask) {
        tasks.put(simpleTask.getId(), simpleTask);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpicTask(epicTasks.get(subTask.getEpicID()));
    }

    @Override
    public String getSubTasksFromEpic(EpicTask epicTask) {
        String info = "";
        for (SubTask sub : epicTask.getSubs()) {
            info += sub.toString();
        }
        return info;
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }

    @Override
    public int getNewId() {
        return newId;
    }
    @Override
    public void setNewId(int newId) {
        this.newId = newId;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public HashMap<Integer, EpicTask> getEpicTasks() {
        return epicTasks;
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Task findTask(int id){
        Task task = null;
        for (Integer idTask : getEpicTasks().keySet()){
            if (idTask == id){
                task = getEpicTaskById(idTask);
                return task;
            }
        }
        if (task == null){
            for (Integer taskId : getSubTasks().keySet()){
                if (taskId == id){
                    task = getSubTaskById(taskId);
                    return task;
                }
            }
        } else {
            return task;
        }
        if (task == null){
            for (Integer taskId : getTasks().keySet()){
                if (taskId == id){
                    task = getTaskById(taskId);
                    return task;
                }
            }
        }
        return task;
    }
}
