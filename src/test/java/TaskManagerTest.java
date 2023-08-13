import org.junit.jupiter.api.Test;
import tasks.Task;

import java.time.LocalDate;

public class TaskManagerTest {
    @Test
    public void addTaskToTaskManager() {
        LocalDate taskDate = LocalDate.of(2023, 7, 15);
        Task task = new Task("Schedule a dentist appointment", taskDate);

        TaskManager taskManager = new TaskManager();

    }

}
