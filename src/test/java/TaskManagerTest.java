import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Priority;
import tasks.Task;
import tasks.TaskManager;

import java.time.LocalDate;
import java.util.List;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        populateTaskManager();
    }

    private void populateTaskManager() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2020, 9, 16);
        Task newTask1 = new Task("Purchase some milk", taskDate1, Priority.LOW);
        Task newTask2 = new Task("Schedule a dentist appointment", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Pay the phone bill", taskDate3, Priority.MEDIUM);

        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);
    }

    @Test
    void addTaskToTaskManager() { //Does it make sense to check that now?
        LocalDate taskDate = LocalDate.of(2023, 7, 16);
        Task newTask = new Task("Take the dog to the veterinarian", taskDate, Priority.HIGH);

        taskManager.addTask(newTask);

        List<Task> tasks = taskManager.getTasks();
        boolean found = tasks.stream().anyMatch(task -> task.equals(newTask));
        Assertions.assertTrue(found);
    }

    @Test
    void testSearchByDate() {
        LocalDate targetDate = LocalDate.of(2023, 8, 5);
        List<Task> tasksOnDate = taskManager.searchByDate(targetDate);

        Assertions.assertFalse(tasksOnDate.isEmpty());
        Assertions.assertEquals(taskManager.getTasks().get(1), tasksOnDate.get(0));
    }

    @Test
    void testSearchByDateInterval() {
        LocalDate startDate = LocalDate.of(2019, 12, 1);
        LocalDate endDate = LocalDate.of(2020, 12, 1);
        List<Task> tasksOnDateInterval = taskManager.searchByDateInterval(startDate, endDate);

        Assertions.assertEquals(1, tasksOnDateInterval.size());
        Assertions.assertEquals(taskManager.getTasks().get(2), tasksOnDateInterval.get(0));
    }

    @Test
    void testSearchByTitle() {
        List<Task> foundTasks = taskManager.searchByTitle("Pay the phone bill");

        Assertions.assertEquals(1, foundTasks.size());
        Assertions.assertEquals(taskManager.getTasks().get(2), foundTasks.get(0));
    }

    @Test
    void testDeleteByDate() {
        LocalDate targetDate = LocalDate.of(2023, 8, 5);
        taskManager.deleteByDate(targetDate);

        List<Task> tasksOnDate = taskManager.searchByDate(targetDate);
        Assertions.assertTrue(tasksOnDate.isEmpty());
    }

    @Test
    void testDeleteByDateInterval() {
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        taskManager.deleteByDateInterval(startDate, endDate);

        Assertions.assertEquals(1, taskManager.getTasks().size());
        //Do I really need all these checks?
        Assertions.assertEquals("Pay the phone bill", taskManager.getTasks().get(0).getTitle());
        Assertions.assertEquals(LocalDate.of(2020, 9, 16), taskManager.getTasks().get(0).getDate());
        Assertions.assertEquals(Priority.MEDIUM, taskManager.getTasks().get(0).getPriority());
    }

    @Test
    void testSearchByPriority() {
        List<Task> foundTasks = taskManager.searchByPriority(Priority.LOW);

        Assertions.assertEquals(1, foundTasks.size());
        Assertions.assertEquals(taskManager.getTasks().get(0), foundTasks.get(0));
    }

    @Test
    void testSortByPriority() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 1);
        List<Task> foundTasks = taskManager.searchByDateInterval(startDate, endDate);

        Assertions.assertEquals(3, foundTasks.size());
        Assertions.assertEquals(taskManager.getTasks().get(1), foundTasks.get(0));
        Assertions.assertEquals(taskManager.getTasks().get(2), foundTasks.get(1));
        Assertions.assertEquals(taskManager.getTasks().get(0), foundTasks.get(2));
    }
}
