package tasks;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.time.LocalDate;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import config.ConfigurationManager;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;


public class ToDoAppGUI {
    private final TaskManager taskManager = new TaskManager();
    private final JTextField taskTitleField = new JTextField(20);
    private final JTextField searchTitleField = new JTextField(20);
    private final JTextArea resultTextArea = new JTextArea(10, 30);

    private final JButton addButton = new JButton("Add task");
    private final JButton searchDateButton = new JButton("Search");
    private final JButton searchDateIntervalButton = new JButton("Search");
    private final JButton searchTitleButton = new JButton("Search");
    private final JButton searchPriorityButton = new JButton("Search");

    private final JButton deleteByDateButton = new JButton("Delete");

    private final JButton deleteByDateIntervalButton = new JButton("Delete");

    private final JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
    private final JComboBox<Priority> searchPriorityComboBox = new JComboBox<>(Priority.values());

    private JDatePickerImpl datePicker1;
    private JDatePickerImpl datePicker2;
    private JDatePickerImpl datePicker3;
    private JDatePickerImpl datePicker4;
    private JDatePickerImpl datePicker5;
    private JDatePickerImpl datePicker6;
    private JDatePickerImpl datePicker7;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("To-Do App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));


        frame.add(createInputPanel());
        frame.add(createSearchDatePanel());
        frame.add(createSearchDateIntervalPanel());
        frame.add(createSearchTitlePanel());
        frame.add(createSearchPriorityPanel());
        frame.add(createDeleteByDatePanel());
        frame.add(createDeleteByDateIntervalPanel());
        frame.add(createResultsPanel());

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();

        inputPanel.add(new JLabel("Title:"));
        setDarkTextComponentUI(taskTitleField);
        inputPanel.add(taskTitleField);

        inputPanel.add(new JLabel("Date:"));
        datePicker1 = initializeDatePicker();
        inputPanel.add(datePicker1);

        inputPanel.add(new JLabel("Priority:"));
        setDarkComboBoxUI(priorityComboBox);
        inputPanel.add(priorityComboBox);
        setDarkButtonUI(addButton);
        inputPanel.add(addButton);
        addButton.addActionListener(e -> addTask());

        return inputPanel;
    }

    private JPanel createSearchDatePanel() {
        JPanel searchDatePanel = new JPanel();


        searchDatePanel.add(new JLabel("Search by date:"));
        datePicker2 = initializeDatePicker();
        searchDatePanel.add(datePicker2);
        setDarkButtonUI(searchDateButton);
        searchDatePanel.add(searchDateButton);
        searchDateButton.addActionListener(e -> searchByDate());

        return searchDatePanel;
    }

    private JPanel createSearchDateIntervalPanel() {
        JPanel searchDateIntervalPanel = new JPanel();

        searchDateIntervalPanel.add(new JLabel("Search by date interval:"));
        datePicker3 = initializeDatePicker();
        datePicker4 = initializeDatePicker();

        searchDateIntervalPanel.add(datePicker3);
        searchDateIntervalPanel.add(datePicker4);
        setDarkButtonUI(searchDateIntervalButton);
        searchDateIntervalPanel.add(searchDateIntervalButton);
        searchDateIntervalButton.addActionListener(e -> searchByDateInterval());

        return searchDateIntervalPanel;
    }

    private JPanel createSearchTitlePanel() {
        JPanel searchTitlePanel = new JPanel();

        searchTitlePanel.add(new JLabel("Search by title:"));
        setDarkTextComponentUI(searchTitleField);
        searchTitlePanel.add(searchTitleField);
        setDarkButtonUI(searchTitleButton);
        searchTitlePanel.add(searchTitleButton);
        searchTitleButton.addActionListener(e -> searchByTitle());

        return searchTitlePanel;
    }

    private JPanel createSearchPriorityPanel() {
        JPanel searchPriorityPanel = new JPanel();

        searchPriorityPanel.add(new JLabel("Search by priority:"));
        setDarkComboBoxUI(searchPriorityComboBox);
        searchPriorityPanel.add(searchPriorityComboBox);
        setDarkButtonUI(searchPriorityButton);
        searchPriorityPanel.add(searchPriorityButton);
        searchPriorityButton.addActionListener(e -> searchByPriority());

        return searchPriorityPanel;
    }

    private JPanel createDeleteByDatePanel() {
        JPanel deleteByDatePanel = new JPanel();

        deleteByDatePanel.add(new JLabel("Delete tasks for the selected date:"));
        datePicker5 = initializeDatePicker();
        deleteByDatePanel.add(datePicker5);
        setDarkButtonUI(deleteByDateButton);
        deleteByDatePanel.add(deleteByDateButton);
        deleteByDateButton.addActionListener(e -> deleteByDate());

        return deleteByDatePanel;
    }

    private JPanel createDeleteByDateIntervalPanel() {
        JPanel deleteByDateIntervalPanel = new JPanel();

        deleteByDateIntervalPanel.add(new JLabel("Delete tasks for the selected date interval:"));
        datePicker6 = initializeDatePicker();
        datePicker7 = initializeDatePicker();
        deleteByDateIntervalPanel.add(datePicker6);
        deleteByDateIntervalPanel.add(datePicker7);
        setDarkButtonUI(deleteByDateIntervalButton);
        deleteByDateIntervalPanel.add(deleteByDateIntervalButton);
        deleteByDateIntervalButton.addActionListener(e -> deleteByDateInterval());

        return deleteByDateIntervalPanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.add(new JLabel("Last search:"));
        setDarkTextComponentUI(resultTextArea);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultsPanel.add(scrollPane);
        return resultsPanel;
    }

    private void addTask() {
        String title = taskTitleField.getText();
        LocalDate date = getDateFromDatePicker(datePicker1);
        Priority priority = (Priority) priorityComboBox.getSelectedItem();
        taskManager.addTask(new Task(title, date, priority));
    }

    private void searchByDate() {
        LocalDate searchDate = getDateFromDatePicker(datePicker2);
        List<Task> searchResults = taskManager.searchByDate(searchDate);
        displaySearchResults(searchResults);
    }

    private void searchByDateInterval() {
        LocalDate startDate = getDateFromDatePicker(datePicker3);
        LocalDate endDate = getDateFromDatePicker(datePicker4);
        List<Task> searchResults = taskManager.searchByDateInterval(startDate, endDate);
        displaySearchResults(searchResults);
    }

    private void searchByTitle() {
        String title = searchTitleField.getText();
        List<Task> searchResults = taskManager.searchByTitle(title);
        displaySearchResults(searchResults);
    }

    private void searchByPriority() {
        Priority priority = (Priority) searchPriorityComboBox.getSelectedItem();
        List<Task> searchResults = taskManager.searchByPriority(priority);
        displaySearchResults(searchResults);
    }

    private LocalDate getDateFromDatePicker(JDatePickerImpl datePicker) {
        Calendar searchCalendar = (Calendar) datePicker.getJFormattedTextField().getValue();
        return searchCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void displaySearchResults(List<Task> searchResults) {
        resultTextArea.setText("");
        for (Task task : searchResults) {
            resultTextArea.append(task.getTitle() + " - " + task.getDate() + " - " + task.getPriority() + "\n");
        }
    }

    private void deleteByDate() {
        LocalDate targetDate = getDateFromDatePicker(datePicker5);
        taskManager.deleteByDate(targetDate);
    }

    private void deleteByDateInterval() {
        LocalDate startDate = getDateFromDatePicker(datePicker6);
        LocalDate endDate = getDateFromDatePicker(datePicker7);
        taskManager.deleteByDateInterval(startDate, endDate);
    }

    private JDatePickerImpl initializeDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        //default value
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getModel().setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
        datePicker.getModel().setSelected(true);

        return datePicker;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskTitleField(String title) {
        taskTitleField.setText(title);
    }

    public void setDatePickerDate1(LocalDate date) {
        datePicker1.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePicker1.getModel().setSelected(true);
    }

    public void setDatePickerDate2(LocalDate date) {
        datePicker2.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePicker2.getModel().setSelected(true);
    }

    public void setDatePickerDate3(LocalDate date) {
        datePicker3.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePicker3.getModel().setSelected(true);
    }

    public void setSearchTitleField(String title){
        searchTitleField.setText(title);
    }

    public void setDatePickerDate4(LocalDate date) {
        datePicker4.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePicker4.getModel().setSelected(true);
    }

    public void setDatePickerDate5(LocalDate date) {
        datePicker5.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePicker5.getModel().setSelected(true);
    }

    public void setDatePickerDate6(LocalDate startDate) {
        datePicker6.getModel().setDate(startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
        datePicker6.getModel().setSelected(true);
    }

    public void setDatePickerDate7(LocalDate endDate) {
        datePicker7.getModel().setDate(endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());
        datePicker7.getModel().setSelected(true);
    }

    public void setPriorityComboBox(Priority priority) {
        priorityComboBox.setSelectedItem(priority);
    }

    public void setSearchPriorityComboBox(Priority priority) {
        searchPriorityComboBox.setSelectedItem(priority);
    }

    public void clickSearchPriorityButton() {
        searchPriorityButton.doClick();
    }

    public void clickAddButton() {
        addButton.doClick();
    }

    public void clickSearchDateButton() {
        searchDateButton.doClick();
    }

    public void clickSearchDateIntervalButton() {
        searchDateIntervalButton.doClick();
    }

    public void clickSearchTitleButton() {
        searchTitleButton.doClick();
    }

    public void clickDeleteByDateButton() {
        deleteByDateButton.doClick();
    }

    public void clickDeleteByDateIntervalButton() {
        deleteByDateIntervalButton.doClick();
    }

    public List<Task> getSearchResults() {
        List<Task> searchResults = new ArrayList<>();
        String[] lines = resultTextArea.getText().split("\n");
        for (String line : lines) {
            String[] parts = line.split(" - ");
            if (parts.length == 3) {
                String title = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                Priority priority = Priority.valueOf(parts[2]);
                searchResults.add(new Task(title, date, priority));
            }
        }
        return searchResults;
    }

    private void setDarkTextComponentUI(JTextComponent textComponent) {
        textComponent.setBackground(new Color(60,63,65));
        textComponent.setForeground(Color.WHITE);
        textComponent.setCaretColor(Color.WHITE);
        textComponent.setSelectedTextColor(new Color(70,109,148));
        textComponent.setBorder(new LineBorder(Color.GRAY));
    }

    private void setDarkButtonUI(JButton button) {
        button.setBackground(new Color(60, 63, 65));
        button.setForeground(Color.WHITE);
    }

    private void setDarkComboBoxUI(JComboBox<?> comboBox) {
        comboBox.setBackground(new Color(60, 63, 65));
        comboBox.setForeground(Color.WHITE);
        comboBox.setSelectedItem(new Color(70,109,148));
        comboBox.setBorder(new LineBorder(Color.GRAY));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LafManager.install(new DarculaTheme());
            ConfigurationManager.init("/config.properties");
            ToDoAppGUI app = new ToDoAppGUI();
            app.createAndShowGUI();
        });
    }
}