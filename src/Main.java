import manager.Manager;
import tasks.EpicTask;
import tasks.SubTask;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Manager manager = new Manager();

        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", "NEW");

        manager.addEpicTask(epicTask);

        SubTask subTask = new SubTask("Купить мандаринов", "2 кг импортные", "IN_PROGRESS", epicTask.getId());

        manager.addSubTask(subTask);

        SubTask subTask1 = new SubTask("Купить огурцов", "2кг импортные", "NEW", epicTask.getId());

        manager.addSubTask(subTask1);
        manager.updateEpicTask(epicTask);

        EpicTask epicTask1 = new EpicTask("Программирование", "Закрыть спринт", "IN_PROGRESS");

        manager.addEpicTask(epicTask1);

        SubTask subTask2 = new SubTask("Спринт 3", "Отправить проект на сдачу", "IN_PROGRESS", epicTask1.getId());

        manager.addSubTask(subTask2);

        manager.printInfoEpicTasks();

        subTask2.setProgress("DONE");


        manager.printInfoEpicTasks();

        manager.removeSubTask(subTask2.getId());

        manager.printInfoEpicTasks();
        manager.printInfoEpicTasks();
    }
}
