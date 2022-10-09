package manager;

import Exceptions.ManagerSaveException;
import Formatter.CSVFormatter;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager{

    private final Path file;

    public FileBackedTasksManager(Path file){
        this.file = file;
    }

    public static FileBackedTasksManager load(Path file) throws IOException {
        FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);
        //заполняем
        String csv = Files.readString(Path.of(file.toUri()));
        //Разделим его на строки, превратим в массив строк String[]
        String[] array = csv.split("\n");
        List<Integer> history = Collections.emptyList();
        //считывали таски
        int generatorID = 0;
        Task task;
        for (int i = 1; i < array.length; i++) {
            String line = array[i];
            if (line.isEmpty()){
                //дошли до строки с историей
                history = CSVFormatter.createHistoryFromString(array[i + 1]);
            } else if (i == array.length - 1){
                break;
            } else {
                task = CSVFormatter.fromString(line);
                int id = task.getId();
                if (id > generatorID){
                    generatorID = id;
                }
                tasksManager.addNewTask(task);
            }
        }
        tasksManager.setNewId(generatorID);
        //восстанавливаем id сабтасков у эпиков
        //проходим все элементы мапы с сабтасками,
        for (Map.Entry<Integer, SubTask> entry : tasksManager.getSubTasks().entrySet()){
            SubTask subTask = entry.getValue();
            EpicTask epicTask = tasksManager.getEpicTasks().get(subTask.getEpicID());
            epicTask.add(subTask);
        }
        //восстанавливаем историю
        for (Integer taskId : history) {
            tasksManager.getHistory().add(tasksManager.findTask(taskId));
            //нужно пробежаться по всем мапам, чтобы найти таск с нужным айди: *если не нулл - возвращаем*
        }
        return tasksManager;
    }

    public void save() throws ManagerSaveException{
        //запишем файл в строку через bufferedwriter(new filewriter(file)
        try(BufferedWriter bf = new BufferedWriter(new FileWriter(file.toFile()))){
            //записать в файл заголовок
            // проходим мапы с тасками, для каждой формируем строку и записываем в файл
            bf.write(CSVFormatter.getHeader());
            for (EpicTask epictask : getEpicTasks().values()){
                bf.write(CSVFormatter.toString(epictask));
                bf.write("\n");
            }
            for (SubTask subTask : getSubTasks().values()){
                bf.write(CSVFormatter.toString(subTask));
                bf.write("\n");
            }
            for (Task task : getTasks().values()){
                bf.write(CSVFormatter.toString(task));
                bf.write("\n");
            }
            bf.write("\n");
            bf.write(CSVFormatter.historyToString(super.getHistory()));
            //Записываем пустую строку
            //В виде строки записываем History Manager
        } catch (IOException exception){
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    @Override
    public void addTask(Task task){
        super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addEpicTask(EpicTask epicTask){
        super.addEpicTask(epicTask);
        //save();
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addSubTask(SubTask subTask){
        super.addSubTask(subTask);
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void addNewTask(Task task){
        switch (task.getType()){
            case EPIC: //в соответствии с классом вызываем метод добавления
                super.addEpicTask((EpicTask) task);
                break;
            case TASK:
                super.addTask(task);
            case SUBTASK:
                super.addSubTask((SubTask) task);
                break;
        }
    }

    @Override
    public EpicTask getEpicTaskById(int id){
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
        return super.getEpicTaskById(id);
    }

    @Override
    public SubTask getSubTaskById(int id){
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
        return super.getSubTaskById(id);
    }

    @Override
    public Task getTaskById(int id){
        try {
            save();
        } catch (ManagerSaveException ex){
            System.out.println(ex.getMessage());
        }
        return super.getTaskById(id);
    }
}
