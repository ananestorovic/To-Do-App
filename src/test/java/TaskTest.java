import org.junit.jupiter.api.Test;
import tasks.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    @Test
    void testTaskCreation() {
        LocalDate taskDate = LocalDate.of(2023, 7, 15);
        Task task = new Task("Schedule a dentist appointment", taskDate);

        assertEquals("Schedule a dentist appointment", task.getTitle());
        assertEquals(taskDate, task.getDate());
    }
}
