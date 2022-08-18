package tasks;

import java.util.HashMap;

public class Manager {
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    protected HashMap<Integer, Task> tasks = new HashMap<>();

    int newId = 1;

    public void printInfoEpic(){
        System.out.println("Информация о составных задачах:");
        String epicTasksInfo = epicTasks.toString();
        System.out.println(epicTasksInfo);
    }

    public void printInfoSubs(){
        System.out.println("Информация о подзадачах: ");
        String subTaskInfo = subTasks.toString();
        System.out.println("subTaskInfo");
    }

    public void printInfoSimple(){
        System.out.println("Информация о обычных задачах: ");
        String simpleTasksInfo = tasks.toString();
        System.out.println(simpleTasksInfo);
    }

    public void removeEpic(int id){
        epicTasks.remove(id);
    }

    public void removeSub(int id){
        SubTask s = subTasks.get(id);
        EpicTask e = epicTasks.get(s.getEpicID());
        e.getSubs().remove(s);
        subTasks.remove(id);
        updateEpic(e);
    }

    public void removeSimple(int id){
        tasks.remove(id);
    }

    public EpicTask getEpicById(int id){
        return epicTasks.get(id); //если передан не существующий ID, то возвращаем NULL
    }

    public Task getSimpleById(int id){
        return tasks.get(id);
    }

    public SubTask getSubById(int id){
        return subTasks.get(id);
    }

    public void removeAllSubs(){
        subTasks.clear();
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
            updateEpic(value);
        }
    }

    public void removeAllEpics(){
        for (EpicTask value : epicTasks.values()) {
            value.getSubs().clear();
        }
        epicTasks.clear();
    }

    public void removeAllSimple(){
        tasks.clear();
    }

    public void addTask(Task simpleTask){
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
    }

    public void addEpic(EpicTask epicTask){
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpic(epicTask);
    }

    public void addSub(SubTask subTask){
        subTask.setId(newId++);
        subTasks.put(subTask.getId(), subTask);
        EpicTask e = epicTasks.get(subTask.getEpicID());
        e.getSubs().add(subTask);
        updateEpic(e);
    }

    public void updateEpic(EpicTask epicTask){
        String progress = "";
        int quantityDONE = 0;
        int quantityINPROGRESS = 0;
        int quantityNEW = 0;
        for (SubTask sub : epicTask.getSubs()) {
            if (sub.progress.equals("IN_PROGRESS")){
                quantityINPROGRESS += 1;
            } else if (sub.progress.equals("DONE")){
                quantityDONE += 1;
            } else if(sub.progress.equals("NEW")) {
                quantityNEW += 1;
            }
        }

        if (quantityINPROGRESS > 0){
            epicTask.progress = "IN_PROGRESS";
        } else if (quantityNEW == epicTask.getSubs().size() || epicTask.getSubs().isEmpty()){
            epicTask.progress = "NEW";
        } else if (quantityDONE == epicTask.getSubs().size()){
            epicTask.progress = "DONE";
        } else {
            epicTask.progress = "IN_PROGRESS"; // случай, когда у сабтасков статусы NEW И DONE
        }


    }

    public void updateSimple(Task simpleTask){
        tasks.put(simpleTask.getId(), simpleTask);
    }

    public void updateSub(SubTask subTask){
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epicTasks.get(subTask.epicID));
    }

    public String getSubsFromEpic(EpicTask epicTask){
        String info = "";
        for (SubTask sub : epicTask.getSubs()) {
             info += sub.toString();
        }
        return info;
    }

}
