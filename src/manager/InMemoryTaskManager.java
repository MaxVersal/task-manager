package manager;

import Exceptions.TaskTimeException;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;
import tasks.Progress;

import java.util.*;

public class InMemoryTaskManager implements manager.TaskManager {
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final manager.HistoryManager historyManager = manager.Managers.getDefaultHistory();
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
        System.out.println(subTaskInfo);
    }

    @Override
    public void printInfoTask() {
        System.out.println("Информация о обычных задачах: ");
        String simpleTasksInfo = tasks.toString();
        System.out.println(simpleTasksInfo);
    }

    @Override
    public void removeEpicTask(int id) throws NullPointerException {
        try {
            if (!epicTasks.get(id).getSubs().isEmpty()){
                for (SubTask sub : epicTasks.get(id).getSubs()){
                    historyManager.remove(sub.getId()); // удаляем сабы из истории
                }
            }
            epicTasks.remove(id);
            historyManager.remove(id);
        } catch (NullPointerException ex){
            throw new NullPointerException("Данного id не суещствует!");
        }
    }

    @Override
    public void removeSubTask(int id) throws NullPointerException {
        try {
            SubTask s = subTasks.get(id);
            EpicTask e = epicTasks.get(s.getEpicID());
            e.getSubs().remove(s);
            subTasks.remove(id);
            updateEpicTask(e);
            historyManager.remove(id);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Данного id не суещствует!");
        }

    }

    @Override
    public void removeTask(int id) throws NullPointerException {
        try {
            tasks.remove(id);
            historyManager.remove(id);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Данного id не существует!");
        }

    }

    @Override
    public EpicTask getEpicTaskById(int id) throws NullPointerException {
        try{
            historyManager.add(epicTasks.get(id));
            return epicTasks.get(id);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Данного id не существует!");
        }

    }

    @Override
    public Task getTaskById(int id) throws NullPointerException {
        try{
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Данного id не существует!");
        }
    }

    @Override
    public SubTask getSubTaskById(int id) throws NullPointerException {
        try{
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Данного id не существует!");
        }
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
        subTasks.clear();
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void addTask(Task simpleTask) {
        try {
            checkCrossings(simpleTask); // проверка на пересечения при добавлении
        }catch (TaskTimeException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        simpleTask.setId(newId++);
        tasks.put(simpleTask.getId(), simpleTask);
    }

    @Override
    public void addEpicTask(EpicTask epicTask) {
        try {
            checkCrossings(epicTask); // проверка на пересечения при добавлении
        }catch (TaskTimeException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        epicTask.setId(newId++);
        epicTasks.put(epicTask.getId(), epicTask);
        updateEpicTask(epicTask);

    }

    @Override
    public  void addSubTask(SubTask subTask) {
        try {
            checkCrossings(subTask); // проверка на пересечения при добавлении
        }catch (TaskTimeException ex) {
            System.out.println(ex.getMessage());
            return;
        }
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

        //также с добавлением саба меняем время начала и продолжительность у эпика
        epicTask.setStartTime(epicTask.calculateStartTime());
        epicTask.setDuration(epicTask.calculateSummaryDuration());
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

    public void checkCrossings(Task task) throws TaskTimeException{
        for (EpicTask epic : epicTasks.values()){
            if (task.getStartTime().isBefore(epic.getEndTime()) && task.getStartTime().isAfter(epic.getStartTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getEndTime().isAfter(epic.getStartTime()) && task.getEndTime().isBefore(epic.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(epic.getStartTime()) && task.getEndTime().isEqual(epic.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isBefore(epic.getStartTime()) && task.getEndTime().isAfter(epic.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(epic.getStartTime())) {
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(epic.getEndTime())) {
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            }
        }

        for (SubTask subTask : subTasks.values()){
            if (task.getStartTime().isBefore(subTask.getEndTime()) && task.getStartTime().isAfter(subTask.getStartTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getEndTime().isAfter(subTask.getStartTime()) && task.getEndTime().isBefore(subTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(subTask.getStartTime()) || task.getEndTime().isEqual(subTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isBefore(subTask.getStartTime()) && task.getEndTime().isAfter(subTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            }
        }

        for (Task simpleTask : tasks.values()){
            if (task.getStartTime().isBefore(simpleTask.getEndTime()) && task.getStartTime().isAfter(simpleTask.getStartTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getEndTime().isAfter(simpleTask.getStartTime()) && task.getEndTime().isBefore(simpleTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(simpleTask.getStartTime()) || task.getEndTime().isEqual(simpleTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isBefore(simpleTask.getStartTime()) && task.getEndTime().isAfter(simpleTask.getEndTime())){
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            } else if (task.getStartTime().isEqual(simpleTask.getEndTime()) || task.getEndTime().isEqual(simpleTask.getStartTime())) {
                throw new TaskTimeException("Задания не должны пересекаться по времени");
            }
        }
    }

    public Set<Task> getPrioritizedTasks() {
        Set<Task> allTasks = new TreeSet<>();
        for (SubTask sub : subTasks.values()) {
            allTasks.add(sub);
        }
        for (EpicTask epic : epicTasks.values()) {
            allTasks.add(epic);
        }
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }
}
