package tests.manager;

import manager.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    Path path = Path.of("././history.csv");

    @BeforeEach
    public void init() {
        manager = new FileBackedTasksManager(path);
        super.init();
    }


    @Test
    void shouldLoadCorrectly() throws IOException {
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
        manager.getEpicTaskById(epic.getId());
        FileBackedTasksManager fbtm = FileBackedTasksManager.load(path);
        assertEquals(manager.getEpicTasks(), fbtm.getEpicTasks());
    }

    @Test
    void loadWithoutTasks(){
        assertEquals(0,manager.getHistory().size());
    }
}