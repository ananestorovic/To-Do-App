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

    @Test
    void testSearchByDate() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1);
        Task newTask2 = new Task("Pay the phone bill", taskDate2);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);

        LocalDate targetDate = LocalDate.of(2023, 8, 5);
        List<Task> tasksOnDate = taskManager.searchByDate(targetDate);

        Assertions.assertFalse(tasksOnDate.isEmpty());
        Assertions.assertEquals(newTask2, tasksOnDate.get(0));

    }

}
