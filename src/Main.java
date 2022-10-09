import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Progress;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");

        TaskManager manager = Managers.getDefault();

        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", Progress.NEW);

        manager.addEpicTask(epicTask);

        SubTask subTask = new SubTask("Купить мандаринов", "2 кг импортные", Progress.IN_PROGRESS, epicTask.getId());

        SubTask subTask1 = new SubTask("Купить огурцов", "2кг импортные", Progress.IN_PROGRESS, epicTask.getId());

        SubTask subTask2 = new SubTask("Купить помидоров", "2 кг импортные", Progress.IN_PROGRESS, epicTask.getId());

        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);

        EpicTask epicTask1 = new EpicTask("Программирование", "Закрыть спринт", Progress.IN_PROGRESS);
        manager.addEpicTask(epicTask1);

        manager.getEpicTaskById(1);
        manager.getEpicTaskById(1);
        manager.getEpicTaskById(1);
        manager.getEpicTaskById(1);

        manager.getSubTaskById(2);
        manager.getSubTaskById(2);
        manager.getSubTaskById(2);

        manager.getEpicTaskById(1);

        Path path = Paths.get("C:\\Users\\User\\IdeaProjects\\java-kanban\\src\\history.csv");
        FileBackedTasksManager fbtm = new FileBackedTasksManager(path);

        fbtm.addEpicTask(epicTask);
        fbtm.addEpicTask(epicTask1);
        fbtm.addSubTask(subTask);

        fbtm.getEpicTaskById(1);
        fbtm.getEpicTaskById(1);

        fbtm.getSubTaskById(3);
        fbtm.getEpicTaskById(2);

        System.out.println(fbtm.getHistory());

        Path path1 = Paths.get("C:\\Users\\User\\IdeaProjects\\java-kanban\\src\\history2.csv");
        FileBackedTasksManager fbtm1 = FileBackedTasksManager.load(path1);

        System.out.println(fbtm1.getHistory());

    }
}
