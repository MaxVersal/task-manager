package manager;

import kv.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {

    private final Gson gson;

    private final KVTaskClient client;

    public HTTPTaskManager(String url) {
        super(null);
        gson = new Gson();
        client = new KVTaskClient(URI.create(url));
    }

    public HTTPTaskManager() {
        this("http://localhost:8078");
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpicTask(EpicTask epicTask) {
        super.addEpicTask(epicTask);
        save();
    }

    @Override
    public void addSubTask(SubTask sub) {
        super.addSubTask(sub);
        save();
    }

    @Override
    protected void save(){
        String jsonTasks = gson.toJson(getTasks());
        System.out.println("сейвим таски");
        client.put("tasks", jsonTasks);

        String jsonEpics = gson.toJson(getEpicTasks());
        System.out.println("сейвим эпики");
        client.put("epics", jsonEpics);

        String jsonSubs = gson.toJson(getSubTasks(), new TypeToken<HashMap<Integer, SubTask>>(){}.getType());
        client.put("subs", jsonSubs);

        List<Task> history = getHistory();
        String jsonHistory = gson.toJson(history);
        client.put("history", jsonHistory);
    }

    @Override
    public void load() {
        String jsonTasks = client.load("tasks");
        String jsonSubs = client.load("subs");
        String jsonEpics = client.load("epics");
        String jsonHistory = client.load("history");

        this.setTasks(gson.fromJson(jsonTasks, new TypeToken<HashMap<Integer, Task>>(){}.getType()));
        this.setEpicTasks(gson.fromJson(jsonEpics, new TypeToken<HashMap<Integer, EpicTask>>(){}.getType()));
        this.setSubTasks(gson.fromJson(jsonSubs, new  TypeToken<HashMap<Integer, SubTask>>(){}.getType()));
        try {
            List<Task> history = gson.fromJson(jsonHistory, new TypeToken<List<Task>>(){}.getType());
            this.getHistory().addAll(history);
        } catch (NullPointerException ex) {
            System.out.println("Истории нет");
        }


    }
}
