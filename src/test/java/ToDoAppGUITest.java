import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import tasks.Priority;
import tasks.Task;
import tasks.TaskManager;
import tasks.ToDoAppGUI;

class ToDoAppGUITest {

    private ToDoAppGUI todoAppGUI;

    @BeforeEach
    void setUp() {
        todoAppGUI = new ToDoAppGUI();
        todoAppGUI.createAndShowGUI();
    }

    @Test
    void testAddTaskThroughGUI() {
        String title = "Test Task";
        LocalDate date = LocalDate.now();
        Priority priority = Priority.HIGH;

        todoAppGUI.setTaskTitleField(title);
        todoAppGUI.setDatePickerDate1(date);
        todoAppGUI.setPriorityComboBox(priority);

        todoAppGUI.clickAddButton();

        TaskManager taskManager = todoAppGUI.getTaskManager();
        Task addedTask = taskManager.getTasks().get(0);
        assertEquals(title, addedTask.getTitle());
        assertEquals(date, addedTask.getDate());
        assertEquals(priority, addedTask.getPriority());
    }

    @Test
    void testSearchByDate() {
        String title = "Test Task";
        LocalDate date = LocalDate.now();
        Priority priority = Priority.HIGH;

        Task testTask = new Task(title, date, priority);
        todoAppGUI.getTaskManager().addTask(testTask);

        todoAppGUI.setDatePickerDate2(date);
        todoAppGUI.clickSearchDateButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title, foundTask.getTitle());
        assertEquals(date, foundTask.getDate());
        assertEquals(priority, foundTask.getPriority());
    }
}
