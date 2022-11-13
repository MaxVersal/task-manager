package manager;

import java.nio.file.Path;

public class Managers {

    public static manager.TaskManager getDefault(){

        return  new manager.HTTPTaskManager();
    }

    public static manager.InMemoryHistoryManager getDefaultHistory(){
        return new manager.InMemoryHistoryManager();
    }



}
