package tasks;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Properties;


public class ToDoAppGUI {
    private TaskManager taskManager = new TaskManager();

    private JFrame frame = new JFrame("To-Do App");
    private JPanel inputPanel = new JPanel();
    private JTextField taskTitleField = new JTextField(20);
    private JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
    private JDatePickerImpl JDatePickerImpl1;
    private JButton addButton = new JButton("Add task");

    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = taskTitleField.getText();
                LocalDate date = LocalDate.parse(JDatePickerImpl1.getJFormattedTextField().getText());
                Priority priority = (Priority) priorityComboBox.getSelectedItem();
                taskManager.addTask(new Task(title, date, priority));

            }
        });
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(taskTitleField);
        inputPanel.add(new JLabel("Date:"));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl1 = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        inputPanel.add(JDatePickerImpl1);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);
        inputPanel.add(addButton);

        frame.add(inputPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskTitleField(String title) {
        taskTitleField.setText(title);
    }

    public void setDatePickerDate(LocalDate date) {
        JDatePickerImpl1.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        JDatePickerImpl1.getModel().setSelected(true);
    }

    public void setPriorityComboBox(Priority priority) {
        priorityComboBox.setSelectedItem(priority);
    }

    public void clickAddButton() {
        addButton.doClick();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoAppGUI app = new ToDoAppGUI();
            app.createAndShowGUI();
        });
    }
}

