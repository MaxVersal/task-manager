package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;
import tasks.Progress;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    protected static HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected static HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    protected static HashMap<Integer, Task> tasks = new HashMap<>();

    protected static ArrayList <Task> history = new ArrayList<>();

    private int newId = 1;

    @Override
    public void printInfoEpicTasks(){
        System.out.println("Информация о составных задачах:");
        String epicTasksInfo = epicTasks.toString();
        System.out.println(epicTasksInfo);
    }
    @Override
    public void printInfoSubTasks(){
        System.out.println("Информация о подзадачах: ");
        String subTaskInfo = subTasks.toString();
        System.out.println("subTaskInfo");
    }
    @Override
    public void printInfoTask(){
        System.out.println("Информация о обычных задачах: ");
        String simpleTasksInfo = tasks.toString();
        System.out.println(simpleTasksInfo);
    }
    @Override
    public void removeEpicTask(int id){
        epicTasks.remove(id);
    }

    @Override
    public void removeSubTask(int id){
        SubTask s = subTasks.get(id);
        EpicTask e = epicTasks.get(s.getEpicID());
        e.getSubs().remove(s);
        subTasks.remove(id);
        updateEpicTask(e);
    }
    @Override
    public void removeTask(int id){
        tasks.remove(id);
    }
    @Override
    public EpicTask getEpicTaskById(int id){
        history.add(epicTasks.get(id));
        updateHistory();
        return epicTasks.get(id);
    }
    @Override
    public Task getTaskById(int id){
        history.add(tasks.get(id));
        updateHistory();
        return tasks.get(id);

    }
    @Override
    public SubTask getSubTaskById(int id){
        history.add(subTasks.get(id));
        updateHistory();
        return subTasks.get(id);
    }
    @Override
    public void removeAllSubTasks(){
        subTasks.clear();
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
            updateEpicTask(value);
        }
    }
    @Override
    public void removeAllEpicTasks(){
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
        }
        epicTasks.clear();
    }
    @Override
    public void removeAllTasks(){
        tasks.clear();
    }
    @Override
    public void addTask(Task simpleTask){
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
    }
    @Override
    public void addEpicTask(EpicTask epicTask){
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpicTask(epicTask);
    }
    @Override
    public void addSubTask(SubTask subTask){
        subTask.setId(newId++);
        subTasks.put(subTask.getId(), subTask);
        EpicTask e = epicTasks.get(subTask.getEpicID());
        e.getSubs().add(subTask);
        updateEpicTask(e);
    }
    @Override
    public void updateEpicTask(EpicTask epicTask){
        String progress = "";
        int quantityDONE = 0;
        int quantityINPROGRESS = 0;
        int quantityNEW = 0;
        for (SubTask sub : epicTask.getSubs()) {
            if (sub.getProgress().equals(Progress.IN_PROGRESS)){
                quantityINPROGRESS += 1;
            } else if (sub.getProgress().equals(Progress.DONE)){
                quantityDONE += 1;
            } else if(sub.getProgress().equals(Progress.NEW)) {
                quantityNEW += 1;
            }
        }

        if (quantityINPROGRESS > 0){
            epicTask.setProgress(Progress.IN_PROGRESS);
        } else if (quantityNEW == epicTask.getSubs().size() || epicTask.getSubs().isEmpty()){
            epicTask.setProgress(Progress.NEW);
        } else if (quantityDONE == epicTask.getSubs().size()){
            epicTask.setProgress(Progress.DONE);
        } else {
            epicTask.setProgress(Progress.IN_PROGRESS); // случай, когда у сабтасков статусы NEW И DONE
        }


    }
    @Override
    public void updateTask(Task simpleTask){
        tasks.put(simpleTask.getId(), simpleTask);
    }
    @Override
    public void updateSubTask(SubTask subTask){
        subTasks.put(subTask.getId(), subTask);
        updateEpicTask(epicTasks.get(subTask.getEpicID()));
    }
    @Override
    public String getSubTasksFromEpic(EpicTask epicTask){
        String info = "";
        for (SubTask sub : epicTask.getSubs()) {
             info += sub.toString();
        }
        return info;
    }

    public void updateHistory(){
        if (history.size() > 10) {
            history.remove(0);
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
