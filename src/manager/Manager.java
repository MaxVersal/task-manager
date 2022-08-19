package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;

public class Manager {
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    protected HashMap<Integer, Task> tasks = new HashMap<>();

    private int newId = 1;

    public void printInfoEpicTasks(){
        System.out.println("Информация о составных задачах:");
        String epicTasksInfo = epicTasks.toString();
        System.out.println(epicTasksInfo);
    }

    public void printInfoSubTasks(){
        System.out.println("Информация о подзадачах: ");
        String subTaskInfo = subTasks.toString();
        System.out.println("subTaskInfo");
    }

    public void printInfoTask(){
        System.out.println("Информация о обычных задачах: ");
        String simpleTasksInfo = tasks.toString();
        System.out.println(simpleTasksInfo);
    }

    public void removeEpicTask(int id){
        epicTasks.remove(id);
    }

    public void removeSubTask(int id){
        SubTask s = subTasks.get(id);
        EpicTask e = epicTasks.get(s.getEpicID());
        e.getSubs().remove(s);
        subTasks.remove(id);
        updateEpicTask(e);
    }

    public void removeTask(int id){
        tasks.remove(id);
    }

    public EpicTask getEpicTaskById(int id){
        return epicTasks.get(id); //если передан не существующий ID, то возвращаем NULL
    }

    public Task getTaskById(int id){
        return tasks.get(id);
    }

    public SubTask getSubTaskById(int id){
        return subTasks.get(id);
    }

    public void removeAllSubTasks(){
        subTasks.clear();
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
            updateEpicTask(value);
        }
    }

    public void removeAllEpicTasks(){
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
        }
        epicTasks.clear();
    }

    public void removeAllTasks(){
        tasks.clear();
    }

    public void addTask(Task simpleTask){
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
    }

    public void addEpicTask(EpicTask epicTask){
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpicTask(epicTask);
    }

    public void addSubTask(SubTask subTask){
        subTask.setId(newId++);
        subTasks.put(subTask.getId(), subTask);
        EpicTask e = epicTasks.get(subTask.getEpicID());
        e.getSubs().add(subTask);
        updateEpicTask(e);
    }

    public void updateEpicTask(EpicTask epicTask){
        String progress = "";
        int quantityDONE = 0;
        int quantityINPROGRESS = 0;
        int quantityNEW = 0;
        for (SubTask sub : epicTask.getSubs()) {
            if (sub.getProgress().equals("IN_PROGRESS")){
                quantityINPROGRESS += 1;
            } else if (sub.getProgress().equals("DONE")){
                quantityDONE += 1;
            } else if(sub.getProgress().equals("NEW")) {
                quantityNEW += 1;
            }
        }

        if (quantityINPROGRESS > 0){
            epicTask.setProgress("IN_PROGRESS");
        } else if (quantityNEW == epicTask.getSubs().size() || epicTask.getSubs().isEmpty()){
            epicTask.setProgress("NEW");
        } else if (quantityDONE == epicTask.getSubs().size()){
            epicTask.setProgress("DONE");
        } else {
            epicTask.setProgress("IN_PROGRESS"); // случай, когда у сабтасков статусы NEW И DONE
        }


    }

    public void updateTask(Task simpleTask){
        tasks.put(simpleTask.getId(), simpleTask);
    }

    public void updateSubTask(SubTask subTask){
        subTasks.put(subTask.getId(), subTask);
        updateEpicTask(epicTasks.get(subTask.getEpicID()));
    }

    public String getSubTasksFromEpic(EpicTask epicTask){
        String info = "";
        for (SubTask sub : epicTask.getSubs()) {
             info += sub.toString();
        }
        return info;
    }

}
