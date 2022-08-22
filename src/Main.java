import manager.InMemoryHistoryManager;
import manager.Managers;
import manager.TaskManager;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Progress;



public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = Managers.getDefault();

        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", Progress.NEW);

        manager.addEpicTask(epicTask);

        SubTask subTask = new SubTask("Купить мандаринов", "2 кг импортные", Progress.IN_PROGRESS, epicTask.getId());

        SubTask subTask1 = new SubTask("Купить огурцов", "2кг импортные", Progress.IN_PROGRESS, epicTask.getId());

        manager.addSubTask(subTask);
        manager.addSubTask(subTask1);

        EpicTask epicTask1 = new EpicTask("Программирование", "Закрыть спринт", Progress.IN_PROGRESS);
        manager.addEpicTask(epicTask1);

        SubTask subTask2 = new SubTask("Спринт 3", "Отправить проект на сдачу", Progress.IN_PROGRESS, epicTask1.getId());

        manager.addSubTask(subTask2);

        manager.printInfoEpicTasks();

        subTask2.setProgress(Progress.NEW);
        manager.updateEpicTask(epicTask1);

        manager.printInfoEpicTasks();

        manager.getEpicTaskById(1);
        manager.getEpicTaskById(1);
        manager.getEpicTaskById(1);

        manager.getSubTaskById(3);
        manager.getSubTaskById(3);
        manager.getSubTaskById(3);

        System.out.println(manager.getHistory());

        InMemoryHistoryManager manager1 = Managers.getDefaultHistory();

        manager1.add(epicTask);



    }
}
