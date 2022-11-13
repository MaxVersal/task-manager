package tests.manager;

import KV.KVServer;
import KV.KVTaskClient;
import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.*;
import tasks.EpicTask;
import tasks.Progress;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;

class HTTPTaskManagerTest {

    public static KVServer server;

    private static TaskManager manager;

    private static Task task;

    private static EpicTask epic;

    private static Gson gson;


    @BeforeEach
    public void shouldStartServer() throws IOException {
        server = new KVServer();
        server.start();
        manager = Managers.getDefault();
        gson = new Gson();
    }

    @AfterEach
    public void shouldStopKV() {
       server.stop();
    }

    @BeforeAll
    public static void init() {
        task = new Task("title",
                "decription",
                Progress.NEW,
                LocalDateTime.of(2021, 1, 10,23,30),
                Duration.ofMinutes(300));
        epic = new EpicTask("Продукты", "Сходить в магазин", Progress.NEW);
    }

    @Test
    @DisplayName("загрузка")
    public void shouldLoadCorrectly() {
        TaskManager manager1 = Managers.getDefault();

        manager.addTask(task);
        manager.addEpicTask(epic);

        manager1.load();

        Assertions.assertAll(
                () -> Assertions.assertEquals(manager1.getTasks(), manager.getTasks()),
                () -> Assertions.assertEquals(manager1.getEpicTasks(),manager.getEpicTasks())
        );
    }
}