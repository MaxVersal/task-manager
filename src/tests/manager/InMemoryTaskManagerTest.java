package tests.manager;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp(){
        manager = new InMemoryTaskManager();
        init();
    }

    @Test
    void shouldReturnSortedSet() {
        manager.addEpicTask(epic);
        sub.setEpicID(epic.getId());
        sub1.setEpicID(epic.getId());
        manager.addSubTask(sub);
        manager.addSubTask(sub1);
        System.out.println(manager.getPrioritizedTasks());
        assertAll(
                () -> assertNotNull(manager.getPrioritizedTasks()),
                () -> assertTrue(manager.getPrioritizedTasks().contains(sub))
        );

    }

}