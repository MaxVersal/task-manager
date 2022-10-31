package manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.Progress;
import tasks.SubTask;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    static Path path = Path.of(".\\history.csv");

    public FileBackedTasksManagerTest(){
        super(new FileBackedTasksManager(path));
    }

    /*@BeforeAll
    static void init(){
        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", Progress.NEW);


        SubTask subTask = new SubTask("Sub",
                "description",
                Progress.IN_PROGRESS,
                LocalDateTime.of(2022,1,1, 10, 30),
                Duration.ofMinutes(20), 1);

        manager.addEpicTask(epicTask);
        manager.addSubTask(subTask);

        manager.getEpicTaskById(epicTask.getId());
        manager.getEpicTaskById(epicTask.getId());

        manager.getSubTaskById(subTask.getId());
    }

    @Test
    void loadCorrectly() throws IOException {
        FileBackedTasksManager fbtm1 = FileBackedTasksManager.load(path);
        assertAll(
                () -> assertEquals(manager.getHistory(), fbtm1.getHistory()),
                () -> assertEquals(manager.getEpicTasks(), fbtm1.getEpicTasks())
        );
    }*/

}