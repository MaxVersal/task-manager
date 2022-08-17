import java.util.HashMap;

public class Manager {
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();

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
        String simpleTasksInfo = simpleTasks.toString();
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
        simpleTasks.remove(id);
    }

    public EpicTask getEpicById(int id){
        for (Integer epicId : epicTasks.keySet()) {
            if (epicId == id){
                return epicTasks.get(epicId);
            }
        }
        return null; //если передан не существующий ID, то возвращаем NULL
    }

    public SimpleTask getSimpleById(int id){
        for (Integer simpleId : simpleTasks.keySet()) {
            if (simpleId == id){
                return simpleTasks.get(simpleId);
            }
        }
        return null;
    }

    public SubTask getSubById(int id){
        for (Integer subId : subTasks.keySet()) {
            if (subId == id){
                return subTasks.get(subId);
            }
        }
        return null;
    }

    public void removeAllSubs(){
        subTasks.clear();
    }

    public void removeAllEpics(){
        epicTasks.clear();
    }

    public void removeAllSimple(){
        simpleTasks.clear();
    }

    public void add(SimpleTask simpleTask){
        simpleTask.setId(newId++);
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void add(EpicTask epicTask){
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpic(epicTask);
    }

    public void add(SubTask subTask){
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
        }

    }

    public void updateSimple(SimpleTask simpleTask){
        simpleTasks.put(simpleTask.getId(), simpleTask);
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
