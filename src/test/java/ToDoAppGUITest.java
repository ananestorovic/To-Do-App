import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import tasks.Priority;
import tasks.Task;
import tasks.TaskManager;
import tasks.ToDoAppGUI;

class ToDoAppGUITest {

    private ToDoAppGUI todoAppGUI;
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        todoAppGUI = new ToDoAppGUI();
        todoAppGUI.createAndShowGUI();
        taskManager = todoAppGUI.getTaskManager();
    }

    private void addTestTasks() {
        taskManager.addTask(new Task("Test Task1", LocalDate.of(2023, 8, 10), Priority.HIGH));
        taskManager.addTask(new Task("Test Task2", LocalDate.of(2023, 8, 12), Priority.LOW));
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

        Task addedTask = taskManager.getTasks().get(0);
        assertEquals(title, addedTask.getTitle());
        assertEquals(date, addedTask.getDate());
        assertEquals(priority, addedTask.getPriority());
    }

    @Test
    void testSearchByDate() {
        addTestTasks();

        LocalDate date = LocalDate.of(2023, 8, 10);
        todoAppGUI.setDatePickerDate2(date);
        todoAppGUI.clickSearchDateButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals("Test Task1", foundTask.getTitle());
        assertEquals(date, foundTask.getDate());
        assertEquals(Priority.HIGH, foundTask.getPriority());
    }

    @Test
    void testSearchByDateInterval() {
        addTestTasks();

        LocalDate startDate = LocalDate.of(2023, 8, 9);
        LocalDate endDate = LocalDate.of(2023, 8, 11);
        todoAppGUI.setDatePickerDate3(startDate);
        todoAppGUI.setDatePickerDate4(endDate);
        todoAppGUI.clickSearchDateIntervalButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals("Test Task1", foundTask.getTitle());
        assertEquals(LocalDate.of(2023, 8, 10), foundTask.getDate());
        assertEquals(Priority.HIGH, foundTask.getPriority());
    }

    @Test
    void testSearchByTitle() {
        addTestTasks();

        String title = "Test Task1";
        todoAppGUI.setSearchTitleField(title);
        todoAppGUI.clickSearchTitleButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals(title, foundTask.getTitle());
        assertEquals(LocalDate.of(2023, 8, 10), foundTask.getDate());
        assertEquals(Priority.HIGH, foundTask.getPriority());
    }

    @Test
    void testSearchByPriority() {
        addTestTasks();

        Priority priority = Priority.HIGH;
        todoAppGUI.setSearchPriorityComboBox(priority);
        todoAppGUI.clickSearchPriorityButton();

        List<Task> searchResults = todoAppGUI.getSearchResults();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals("Test Task1", foundTask.getTitle());
        assertEquals(LocalDate.of(2023, 8, 10), foundTask.getDate());
        assertEquals(priority, foundTask.getPriority());
    }

    @Test
    void testDeleteByDate(){
        addTestTasks();

        LocalDate targetDate = LocalDate.of(2023, 8, 10);
        todoAppGUI.setDatePickerDate5(targetDate);
        todoAppGUI.clickDeleteByDateButton();

        List<Task> searchResults = todoAppGUI.getTaskManager().getTasks();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertFalse(targetDate.equals(foundTask.getDate()));
    }

    @Test
    void testDeleteByDateInterval(){
        addTestTasks();

        LocalDate startDate = LocalDate.of(2023, 8, 9);
        LocalDate endDate = LocalDate.of(2023, 8, 11);
        todoAppGUI.setDatePickerDate6(startDate);
        todoAppGUI.setDatePickerDate7(endDate);
        todoAppGUI.clickDeleteByDateIntervalButton();

        List<Task> searchResults = todoAppGUI.getTaskManager().getTasks();

        assertEquals(1, searchResults.size());
        Task foundTask = searchResults.get(0);
        assertEquals("Test Task2", foundTask.getTitle());
        assertEquals(LocalDate.of(2023, 8, 12), foundTask.getDate());
        assertEquals(Priority.LOW, foundTask.getPriority());
    }
}
