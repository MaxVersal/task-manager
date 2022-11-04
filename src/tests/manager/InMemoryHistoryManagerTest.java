package tests.manager;

import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Progress;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    protected HistoryManager historyManager;
    protected Task task;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
        task = new Task(
                "title",
                "descr",
                Progress.NEW,
                LocalDateTime.of(2021,2,21,10,30),
                Duration.ofMinutes(300)
        );
    }

    @Test
    void shouldAddTask() {
        historyManager.add(task);
        assertEquals(task, historyManager.getHistory().get(0));
    }

    @Test
    void getTasks() {
        List<Task> list = new ArrayList<>();
        list.add(task);
        historyManager.add(task);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void shouldRemoveTask() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertEquals(0,historyManager.getHistory().size());
    }
}