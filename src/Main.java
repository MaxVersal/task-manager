import manager.Manager;
import tasks.EpicTask;
import tasks.SubTask;


public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Manager manager = new Manager();

        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", "NEW");

        manager.addEpic(epicTask);

        SubTask subTask = new SubTask("Купить мандаринов", "2 кг импортные", "IN_PROGRESS", epicTask.getId());

        manager.addSub(subTask);

        SubTask subTask1 = new SubTask("Купить огурцов", "2кг импортные", "NEW", epicTask.getId());

        manager.addSub(subTask1);
        manager.updateEpic(epicTask);

        EpicTask epicTask1 = new EpicTask("Программирование", "Закрыть спринт", "IN_PROGRESS");

        manager.addEpic(epicTask1);

        SubTask subTask2 = new SubTask("Спринт 3", "Отправить проект на сдачу", "IN_PROGRESS", epicTask1.getId());

        manager.addSub(subTask2);

        manager.printInfoEpic();

        subTask2.setProgress("DONE");


        manager.printInfoEpic();

        manager.removeSub(subTask2.getId());

        manager.removeAllSubs();
        manager.printInfoEpic();
    }
}
