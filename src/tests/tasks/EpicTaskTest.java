package tasks;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    private final static EpicTask epic = new EpicTask("Title", "description", Progress.NEW);
    TaskManager manager = new InMemoryTaskManager();

    private static SubTask sub;

    private static SubTask sub1;

    @BeforeEach
    void init(){
        manager.addEpicTask(epic);
        sub = new SubTask(
                "SubTask",
                "description",
                Progress.NEW,
                LocalDateTime.of(2022, 1, 1, 20, 30),
                Duration.ofMinutes(300),
                epic.getId());
        sub1 = new SubTask(
                "SubTask1",
                "description1",
                Progress.NEW,
                LocalDateTime.of(2023, 1, 1, 20, 30),
                Duration.ofMinutes(300),
                epic.getId()
        );
    }

    @Test
    void shouldReturnEmptySubs(){
        Assertions.assertTrue(epic.getSubs().isEmpty());
    }

    @Test
    void shouldReturnProgressNew(){
        manager.addSubTask(sub);
        assertEquals(Progress.NEW, epic.getProgress());
    }

    @Test
    void shouldReturnDone(){
        sub.setProgress(Progress.DONE);
        manager.addSubTask(sub);
        assertEquals(Progress.DONE, epic.getProgress());
    }

    @Test
    void shouldReturnInProgress(){
        sub.setProgress(Progress.DONE);
        manager.addSubTask(sub);
        manager.addSubTask(sub1);
        assertEquals(Progress.IN_PROGRESS, epic.getProgress());
    }

    @Test
    void shouldReturnInProgressWithSubsInProgress(){
        sub.setProgress(Progress.IN_PROGRESS);
        manager.addSubTask(sub);
        assertEquals(Progress.IN_PROGRESS, epic.getProgress());
    }

}