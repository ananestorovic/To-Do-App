package tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;


public class ToDoAppGUI {
    private TaskManager taskManager = new TaskManager();
    private JTextField taskTitleField = new JTextField(20);
    private JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
    private JDatePickerImpl JDatePickerImpl1;
    private JButton addButton = new JButton("Add task");

    private JDatePickerImpl JDatePickerImpl2;

    private JTextArea resultTextArea = new JTextArea(10, 30);

    private JButton searchDateButton = new JButton("Search");

    public void createAndShowGUI() {
        JFrame frame = new JFrame("To-Do App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(taskTitleField);

        inputPanel.add(new JLabel("Date:"));
        UtilDateModel model1 = new UtilDateModel();
        Properties p1 = new Properties();
        p1.put("text.today", "Today");
        p1.put("text.month", "Month");
        p1.put("text.year", "Year");
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
        JDatePickerImpl1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        inputPanel.add(JDatePickerImpl1);

        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = taskTitleField.getText();
                LocalDate date = LocalDate.parse(JDatePickerImpl1.getJFormattedTextField().getText());
                Priority priority = (Priority) priorityComboBox.getSelectedItem();
                taskManager.addTask(new Task(title, date, priority));
            }
        });

        frame.add(inputPanel);
        frame.pack();
        frame.setVisible(true);


        JPanel searchPanel = new JPanel();
        searchDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calendar searchCalendar = (Calendar) JDatePickerImpl2.getJFormattedTextField().getValue();
                LocalDate searchDate = searchCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                List<Task> searchResults = taskManager.searchByDate(searchDate);
                displaySearchResults(searchResults);
            }
        });

        searchPanel.add(new JLabel("Search by date:"));
        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p1.put("text.today", "Today");
        p1.put("text.month", "Month");
        p1.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        JDatePickerImpl2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        searchPanel.add(JDatePickerImpl2);
        searchPanel.add(searchDateButton);

        JPanel resultsPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultsPanel.add(scrollPane);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(searchPanel, BorderLayout.CENTER);
        frame.add(resultsPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void displaySearchResults(List<Task> searchResults) {
        resultTextArea.setText("");
        for (Task task : searchResults) {
            resultTextArea.append(task.getTitle() + " - " + task.getDate() + " - " + task.getPriority() + "\n");
        }
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskTitleField(String title) {
        taskTitleField.setText(title);
    }

    public void setDatePickerDate1(LocalDate date) {
        JDatePickerImpl1.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        JDatePickerImpl1.getModel().setSelected(true);
    }

    public void setDatePickerDate2(LocalDate date) {
        JDatePickerImpl2.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        JDatePickerImpl2.getModel().setSelected(true);
    }

    public void setPriorityComboBox(Priority priority) {
        priorityComboBox.setSelectedItem(priority);
    }

    public void clickAddButton() {
        addButton.doClick();
    }

    public void clickSearchDateButton() {
        searchDateButton.doClick();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoAppGUI app = new ToDoAppGUI();
            app.createAndShowGUI();
        });
    }
}



