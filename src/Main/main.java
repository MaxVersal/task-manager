package Main;

import KV.KVServer;
import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import tasks.EpicTask;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class main {

    public static void main(String[] args) throws IOException {
        KVServer server = new KVServer();
        server.start();
        TaskManager manager = Managers.getDefault();
        Task task = new Task("title",
                "decription",
                Progress.NEW,
                LocalDateTime.of(2021, 1, 10,23,30),
                Duration.ofMinutes(300));
        EpicTask epicTask = new EpicTask("Продукты", "Сходить в магазин", Progress.NEW);
        manager.addEpicTask(epicTask);
        manager.addTask(task);
        SubTask subTask = new SubTask("Sub",
                "description",
                Progress.IN_PROGRESS,
                LocalDateTime.of(2022,1,1, 10, 30),
                Duration.ofMinutes(20), epicTask.getId());
        manager.addSubTask(subTask);

        server.stop();
    }
}
