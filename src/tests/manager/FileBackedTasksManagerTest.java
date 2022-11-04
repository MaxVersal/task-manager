package tests.manager;

import manager.FileBackedTasksManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.Progress;
import tasks.SubTask;
import tests.manager.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    Path path = Path.of("././history.csv");

    @BeforeEach
    public void init() {
        manager = new FileBackedTasksManager(path);
        super.init();
    }


    @Test
    void shouldLoadCorrectly() throws IOException {
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.getSubTaskById(sub.getId());
        manager.getEpicTaskById(epic.getId());
        manager.addTask(task);
        FileBackedTasksManager fbtm = FileBackedTasksManager.load(path);
        assertAll(
                () -> assertEquals(manager.getSubTasks(), fbtm.getSubTasks()),
                () -> assertEquals(manager.getEpicTasks(), fbtm.getEpicTasks())
        );
    }

    @AfterEach
    void clearAll() {
        manager.removeAllTasks();
    }

    @Test
    void loadWithoutSubs() throws IOException {
        manager.addEpicTask(epic);
        manager.getEpicTaskById(epic.getId());
        FileBackedTasksManager fbtm = FileBackedTasksManager.load(path);
        assertEquals(manager.getEpicTasks(), fbtm.getEpicTasks());
    }

    @Test
    void loadWithoutTasks() throws IOException {
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        manager.addSubTask(sub);
        assertEquals(0,manager.getHistory().size());
    }
}