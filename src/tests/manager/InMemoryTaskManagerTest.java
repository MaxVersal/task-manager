package manager;

class InMemoryTaskManagerTest extends TaskManagerTest<TaskManager>{
    private final static TaskManager manager = new InMemoryTaskManager();

    public InMemoryTaskManagerTest(){
        super(manager);
    }
}