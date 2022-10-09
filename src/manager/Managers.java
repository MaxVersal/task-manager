package manager;

import java.nio.file.Path;

public class Managers {

    public static TaskManager getDefault(){

        return  new InMemoryTaskManager();
    }

    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }



}
