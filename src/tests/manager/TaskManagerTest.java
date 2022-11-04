package tests.manager;

import Exceptions.TaskTimeException;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
abstract class TaskManagerTest <T extends TaskManager>{
    protected  T manager;
    protected  EpicTask epic;
    protected  EpicTask epic1;
    protected  SubTask sub;
    protected  SubTask sub1;
    protected  SubTask sub2;
    protected  Task task;


    protected void init(){
        epic = new EpicTask(
                "Title",
                "Decription",
                Progress.NEW
        );
        sub = new SubTask(
                "SubTask",
                "description",
                Progress.NEW,
                LocalDateTime.of(2022, 1, 1, 20, 30),
                Duration.ofMinutes(300),
                epic.getId()
        );
        sub1 = new SubTask(
                "SubTask1",
                "description1",
                Progress.NEW,
                LocalDateTime.of(2023, 1, 1, 20, 30),
                Duration.ofMinutes(300),
                epic.getId()
        );
        epic1 = new EpicTask(
                "Title",
                "Description",
                Progress.NEW,
                LocalDateTime.of(2023,2,2,10,30),
                Duration.ofMinutes(400)
        );
        sub2 = new SubTask(
                "Subtask2",
                "decription2",
                Progress.NEW,
                LocalDateTime.of(2024,1,1,20,30),
                Duration.ofMinutes(150),
                epic1.getId()
        );
        task = new Task(
                "Title",
                "Decription",
                Progress.NEW,
                LocalDateTime.of(2025,1,1,20,30),
                Duration.ofMinutes(60)
        );
    }

    @AfterEach
    void clear(){
        manager.removeAllEpicTasks();
        manager.removeAllSubTasks();
        manager.removeAllTasks();
    }

    @Test
    void shouldRemoveEpic(){
        manager.addEpicTask(epic);
        manager.removeEpicTask(epic.getId());
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getEpicTaskById(epic.getId())
        );
        assertEquals("Данного id не существует!", ex.getMessage());
        //вернет ошибку, если попробуем найти удаленный эпик
    }

    @Test
    void shoudldRemoveSub(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.removeSubTask(sub.getId());
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getSubTaskById(sub.getId())
        );
        assertEquals("Данного id не существует!", ex.getMessage());
    }

    @Test
    void shouldRemoveTask(){
        manager.addTask(task);
        manager.removeTask(task.getId());
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getTaskById(sub.getId())
        );
        assertEquals("Данного id не существует!", ex.getMessage());
    }

    @Test
    void shouldReturnEpicById(){
        manager.addEpicTask(epic);
        assertEquals(epic, manager.getEpicTaskById(epic.getId()));
    }

    @Test
    void shouldReturnExceptionIfEpicDoesntExist(){
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getEpicTaskById(50)
        );
        assertEquals("Данного id не существует!", ex.getMessage());
    }

    @Test
    void shouldReturnSubById(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        assertEquals(sub, manager.getSubTaskById(sub.getId()));
    }

    @Test
    void shouldReturnExceptionIfSubDoesntExist(){
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getSubTaskById(56)
        );
        assertEquals("Данного id не существует!", ex.getMessage());
    }

    @Test
    void shouldReturnTaskById(){
        manager.addTask(task);
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void shouldReturnExceptionIfTaskDoesntExist(){
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> manager.getTaskById(180)
        );
        assertEquals("Данного id не существует!", ex.getMessage());
    }

    @Test
    void shouldRemoveAllSubtasks(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        sub1.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.addSubTask(sub1);
        manager.removeAllSubTasks();
        assertTrue(manager.getSubTasks().isEmpty());
    }

    @Test
    void epicsSubsShouldBeEmptyAfterClearing(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        sub1.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.addSubTask(sub1);
        manager.removeAllSubTasks();
        assertTrue(epic.getSubs().isEmpty());
    }

    @Test
    void shouldRemoveAllEpics(){
        manager.addEpicTask(epic);
        manager.addEpicTask(epic1);
        manager.removeAllEpicTasks();
        assertTrue(manager.getEpicTasks().isEmpty());
    }

    @Test
    void subsShouldBeEmptyAfterEpicsRemoving() {
        manager.addEpicTask(epic);
        sub1.setEpicID(epic.getId());
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub1);
        manager.addSubTask(sub);
        manager.addEpicTask(epic1);
        sub2.setEpicID(epic1.getId());
        manager.addSubTask(sub2);
        manager.removeAllEpicTasks();
        assertAll(
                () -> assertTrue(manager.getSubTasks().isEmpty()),
                () -> assertTrue(epic.getSubs().isEmpty()),
                () -> assertTrue(epic1.getSubs().isEmpty())
        );
    }

    @Test
    void shouldReturnTrueAfterClearingTasks(){
        manager.addTask(task);
        manager.removeAllTasks();
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void shouldAddTask(){
        manager.addTask(task);
        assertTrue(manager.getTasks().containsValue(task));
    }

    @Test
    void shouldNotAddTaskWithExistingStartTime(){
        manager.addTask(task);
        Task task1 = new Task("Title", "Descr", Progress.NEW, task.getStartTime(), task.getDuration());
        manager.addTask(task1);
        assertFalse(manager.getTasks().containsValue(task1));
    }

    @Test
    void shouldAddEpicTask(){
        EpicTask epic4 = new EpicTask("title",
                "descr",
                Progress.NEW,
                LocalDateTime.of(2020, 4, 6, 5, 30),
                Duration.ofMinutes(15)
        );
        manager.addEpicTask(epic4);
        assertTrue(manager.getEpicTasks().containsValue(epic4));
    }

    @Test
    void shouldNotAddEpicWithExistingTime(){
        manager.addEpicTask(epic);
        EpicTask epicTest = new EpicTask("title",
                "descr",
                Progress.NEW,
                epic.getStartTime(),
                epic.getDuration()
        );
        manager.addEpicTask(epicTest);
        assertFalse(manager.getEpicTasks().containsValue(epicTest));
    }

    @Test
    void shouldAddSub(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        sub.setEpicID(epic.getId());
        manager.updateSubTask(sub);
        manager.addSubTask(sub);
        assertTrue(manager.getSubTasks().containsValue(sub));
    }

    @Test
    void shouldNotAddSubWithSameStartTime(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        SubTask sub3 = new SubTask(
                "Title",
                "Des",
                Progress.NEW,
                sub.getStartTime(),
                sub.getDuration(),
                epic.getId()
                );
        assertFalse(manager.getSubTasks().containsValue(sub3));
    }

    @Test
    void shouldUpdateEpicProgress(){
        manager.addEpicTask(epic);
        sub.setProgress(Progress.DONE);
        sub1.setProgress(Progress.DONE);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        assertEquals(Progress.DONE,epic.getProgress());
        sub1.setEpicID(epic.getId());
        sub1.setProgress(Progress.IN_PROGRESS);
        manager.addSubTask(sub1);
        assertEquals(Progress.IN_PROGRESS, epic.getProgress());
    }

    @Test
    void shouldUpdateSubTask(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        sub.setProgress(Progress.DONE);
        sub.setDuration(Duration.ofMinutes(15));
        manager.updateSubTask(sub);
        assertEquals(sub, manager.getSubTaskById(sub.getId()));
    }

    @Test
    void shouldUpdateTask(){
        manager.addTask(task);
        task.setDuration(Duration.ofMinutes(450));
        task.setDescription("ssss");
        manager.updateTask(task);
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void shouldReturnSubsFromEpic(){
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        sub1.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.addSubTask(sub1);
        String info = "";
        info += sub.toString();
        info += sub1.toString();
        assertEquals(info, manager.getSubTasksFromEpic(epic));
    }

    @Test
    void shouldReturnFindedTaskById(){
        manager.addEpicTask(epic);
        assertEquals(epic, manager.findTask(epic.getId()));
    }

    @Test
    void shouldReturnNullIfIdNotExist(){
        assertNull(manager.findTask(56));
    }

    @Test
    void shouldThrowTaskTimeException(){
        manager.addEpicTask(epic);
        EpicTask epicTask = new EpicTask(
                "d",
                ",",
                Progress.NEW,
                epic.getStartTime(),
                epic.getDuration()
        );
        TaskTimeException ex = assertThrows(
                TaskTimeException.class,
                () -> manager.checkCrossings(epicTask)
        );
        assertEquals("Задания не должны пересекаться по времени", ex.getMessage());
    }

}
