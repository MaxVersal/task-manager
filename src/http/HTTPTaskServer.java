package http;

import kv.KVServer;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import tasks.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HTTPTaskServer {
    static TaskManager manager = Managers.getDefault();
    private final static int PORT = 8081;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static Gson gson = new Gson();
    private static HttpServer server;

    private KVServer kvServer;

    public HTTPTaskServer() throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        System.out.println("Сервер запущен на хосту 8081");
    }

    public void start() throws IOException {
        server.start();
        kvServer.start();
        System.out.println("Запустили ТаскСервер на порту " + PORT);
        System.out.println("Адрес: http://localhost:" +  PORT);
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили TaskServer");
    }
    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String rawQuery = httpExchange.getRequestURI().getRawQuery();
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String[] array = path.split("/");

            switch (method){
                case "GET":
                    if (array[2].equals("task")) {
                        if (rawQuery != null) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            response = gson.toJson(manager.getTaskById(id));
                        } else {
                            response = gson.toJson(manager.getTasks());
                        }
                    } else if (array[2].equals("subtask")) {
                        if (rawQuery != null && !array[3].equals("epic")) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            response = gson.toJson(manager.getTaskById(id));
                        } else if (array[3].equals("epic")) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));

                            response = gson.toJson(manager.getSubTasksFromEpic(manager.getEpicTaskById(id)));
                        }else {
                            response = gson.toJson(manager.getTasks());
                        }
                    } else if (array[2].equals("epic")) {
                        if (rawQuery != null) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            response = gson.toJson(manager.getTaskById(id));
                        } else {
                            response = gson.toJson(manager.getTasks());
                        }
                    } else if (array[2].equals("history")) {
                        response = gson.toJson(manager.getHistory());
                    }
                    break;
                case "DELETE":
                    if (array[2].equals("task")) {
                        if (rawQuery != null) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            manager.removeTask(id);
                        } else {
                            manager.removeAllTasks();
                        }
                    } else if (array[2].equals("subtask")) {
                        if (rawQuery != null) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            manager.removeSubTask(id);
                        } else {
                            manager.removeAllSubTasks();
                        }
                    } else if (array[2].equals("epic")) {
                        if (rawQuery != null) {
                            int id = Integer.parseInt(rawQuery.substring(rawQuery.length() - 1));
                            manager.removeEpicTask(id);
                        } else {
                            manager.removeAllEpicTasks();
                        }
                    }
                    break;
                case "POST":
                    if (array[2].equals("task")) {
                        System.out.println("кто-то постит таск");
                        String body = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                        Task task1 = gson.fromJson(body, Task.class);
                        System.out.println(task1);
                        manager.addTask(task1);
                    } else if (array[2].equals("subtask")){
                        String body = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                        System.out.println(body);
                        SubTask task1 = gson.fromJson(body, SubTask.class);
                        System.out.println(task1);
                        manager.addTask(task1);
                    } else if (array[2].equals("epic")) {
                        String body = new String(httpExchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                        EpicTask epicTask = gson.fromJson(body, EpicTask.class);
                        System.out.println(epicTask);
                        manager.addEpicTask(epicTask);
                    }
                break;
            }
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
