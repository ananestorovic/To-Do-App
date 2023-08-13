import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskManager;

import java.time.LocalDate;
import java.util.List;

class TaskManagerTest {
    @Test
    void addTaskToTaskManager() {
        LocalDate taskDate = LocalDate.of(2023, 7, 15);
        Task newTask = new Task("Schedule a dentist appointment", taskDate);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask);

        List<Task> tasks = taskManager.getTasks();
        boolean found = tasks.stream().anyMatch(task -> task.equals(newTask));
        Assertions.assertTrue(found);
    }

}
