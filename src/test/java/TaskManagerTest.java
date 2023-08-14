import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Priority;
import tasks.Task;
import tasks.TaskManager;

import java.time.LocalDate;
import java.util.List;

class TaskManagerTest {
    @Test
    void addTaskToTaskManager() {
        LocalDate taskDate = LocalDate.of(2023, 7, 15);
        Task newTask = new Task("Schedule a dentist appointment", taskDate, Priority.HIGH);

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
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);

        LocalDate targetDate = LocalDate.of(2023, 8, 5);
        List<Task> tasksOnDate = taskManager.searchByDate(targetDate);

        Assertions.assertFalse(tasksOnDate.isEmpty());
        Assertions.assertEquals(newTask2, tasksOnDate.get(0));
    }

    @Test
    void testSearchByDateInterval() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2023, 9, 16);
        LocalDate taskDate4 = LocalDate.of(2023, 9, 27);
        LocalDate taskDate5 = LocalDate.of(2020, 1, 12);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Purchase some milk", taskDate3, Priority.LOW);
        Task newTask4= new Task("Take the dog for a walk", taskDate4, Priority.HIGH);
        Task newTask5 = new Task("Buy medicine for a cold", taskDate5, Priority.MEDIUM);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);
        taskManager.addTask(newTask4);
        taskManager.addTask(newTask5);

        LocalDate startDate = LocalDate.of(2019, 12, 1);
        LocalDate endDate = LocalDate.of(2020, 12, 1);
        List<Task> tasksOnDateInterval = taskManager.searchByDateInterval(startDate, endDate);

        Assertions.assertEquals(1, tasksOnDateInterval.size());
        Assertions.assertEquals(newTask5, tasksOnDateInterval.get(0));
    }

    @Test
    void testSearchByTitle() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2023, 9, 16);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Purchase some milk", taskDate3, Priority.LOW);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);

        List<Task> foundTasks = taskManager.searchByTitle("Pay the phone bill");

        Assertions.assertEquals(1, foundTasks.size());
        Assertions.assertEquals(newTask2, foundTasks.get(0));
    }

    @Test
    void testDeleteByDate() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);

        LocalDate targetDate = LocalDate.of(2023, 8, 5);
        taskManager.deleteByDate(targetDate);

        List<Task> tasksOnDate = taskManager.searchByDate(targetDate);
        Assertions.assertTrue(tasksOnDate.isEmpty());
    }

    @Test
    void testDeleteByDateInterval() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2023, 9, 16);
        LocalDate taskDate4 = LocalDate.of(2023, 9, 27);
        LocalDate taskDate5 = LocalDate.of(2020, 1, 12);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Purchase some milk", taskDate3, Priority.LOW);
        Task newTask4= new Task("Take the dog for a walk", taskDate4, Priority.HIGH);
        Task newTask5 = new Task("Buy medicine for a cold", taskDate5, Priority.MEDIUM);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);
        taskManager.addTask(newTask4);
        taskManager.addTask(newTask5);

        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 1);
        taskManager.deleteByDateInterval(startDate, endDate);

        Assertions.assertEquals(1, taskManager.getTasks().size());
        Assertions.assertEquals(newTask5, taskManager.getTasks().get(0));
    }

    @Test
    void testSearchByPriority() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2023, 9, 16);
        Task newTask1 = new Task("Schedule a dentist appointment", taskDate1, Priority.HIGH);
        Task newTask2 = new Task("Pay the phone bill", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Purchase some milk", taskDate3, Priority.LOW);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);

        List<Task> foundTasks = taskManager.searchByPriority(Priority.LOW);

        Assertions.assertEquals(1, foundTasks.size());
        Assertions.assertEquals(newTask3, foundTasks.get(0));
    }

    @Test
    void testSortByPriority() {
        LocalDate taskDate1 = LocalDate.of(2023, 7, 15);
        LocalDate taskDate2 = LocalDate.of(2023, 8, 5);
        LocalDate taskDate3 = LocalDate.of(2023, 9, 16);
        Task newTask1 = new Task("Purchase some milk", taskDate1, Priority.LOW);
        Task newTask2 = new Task("Schedule a dentist appointment", taskDate2, Priority.HIGH);
        Task newTask3 = new Task("Pay the phone bill", taskDate3, Priority.MEDIUM);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(newTask1);
        taskManager.addTask(newTask2);
        taskManager.addTask(newTask3);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 1);
        List<Task> foundTasks = taskManager.searchByDateInterval(startDate, endDate);

        Assertions.assertEquals(3, foundTasks.size());
        Assertions.assertEquals(newTask2, foundTasks.get(0));
        Assertions.assertEquals(newTask3, foundTasks.get(1));
        Assertions.assertEquals(newTask1, foundTasks.get(2));
    }
}
