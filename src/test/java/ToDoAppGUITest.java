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
        String title1 = "Test Task1";
        String title2 = "Test Task2";
        LocalDate date1 = LocalDate.of(2023,8,10);
        LocalDate date2 = LocalDate.of(2023,8,12);
        Priority priority1 = Priority.HIGH;
        Priority priority2 = Priority.LOW;

        Task testTask1 = new Task(title1, date1, priority1);
        Task testTask2 = new Task(title2, date2, priority2);
        todoAppGUI.getTaskManager().addTask(testTask1);
        todoAppGUI.getTaskManager().addTask(testTask2);

        todoAppGUI.setDatePickerDate2(date1);
        todoAppGUI.clickSearchDateButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title1, foundTask.getTitle());
        assertEquals(date1, foundTask.getDate());
        assertEquals(priority1, foundTask.getPriority());
    }

    @Test
    void testSearchByDateInterval() {
        String title1 = "Test Task1";
        String title2 = "Test Task2";
        LocalDate date1 = LocalDate.of(2023,8,10);
        LocalDate date2 = LocalDate.of(2023,8,12);
        Priority priority1 = Priority.HIGH;
        Priority priority2 = Priority.LOW;

        Task testTask1 = new Task(title1, date1, priority1);
        Task testTask2 = new Task(title2, date2, priority2);
        todoAppGUI.getTaskManager().addTask(testTask1);
        todoAppGUI.getTaskManager().addTask(testTask2);

        todoAppGUI.setDatePickerDate3(LocalDate.of(2023,8,9));
        todoAppGUI.setDatePickerDate4(LocalDate.of(2023,8,11));
        todoAppGUI.clickSearchDateIntervalButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title1, foundTask.getTitle());
        assertEquals(date1, foundTask.getDate());
        assertEquals(priority1, foundTask.getPriority());
    }

    @Test
    void testSearchByTitle() {
        String title1 = "Test Task1";
        String title2 = "Test Task2";
        LocalDate date1 = LocalDate.of(2023,8,10);
        LocalDate date2 = LocalDate.of(2023,8,12);
        Priority priority1 = Priority.HIGH;
        Priority priority2 = Priority.LOW;

        Task testTask1 = new Task(title1, date1, priority1);
        Task testTask2 = new Task(title2, date2, priority2);
        todoAppGUI.getTaskManager().addTask(testTask1);
        todoAppGUI.getTaskManager().addTask(testTask2);

        todoAppGUI.setSearchTitleField(title1);
        todoAppGUI.clickSearchTitleButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title1, foundTask.getTitle());
        assertEquals(date1, foundTask.getDate());
        assertEquals(priority1, foundTask.getPriority());
    }
    @Test
    void testSearchByPriority() {
        String title1 = "Test Task1";
        String title2 = "Test Task2";
        LocalDate date1 = LocalDate.of(2023,8,10);
        LocalDate date2 = LocalDate.of(2023,8,12);
        Priority priority1 = Priority.HIGH;
        Priority priority2 = Priority.LOW;

        Task testTask1 = new Task(title1, date1, priority1);
        Task testTask2 = new Task(title2, date2, priority2);
        todoAppGUI.getTaskManager().addTask(testTask1);
        todoAppGUI.getTaskManager().addTask(testTask2);

        todoAppGUI.setSearchPriorityComboBox(priority1);
        todoAppGUI.clickSearchPriorityButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title1, foundTask.getTitle());
        assertEquals(date1, foundTask.getDate());
        assertEquals(priority1, foundTask.getPriority());
    }
}
