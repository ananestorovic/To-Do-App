package tasks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public List<Task> searchByDate(LocalDate targetDate) {
        return tasks.stream()
                .filter(task -> task.getDate().equals(targetDate))
                .collect(Collectors.toList());
    }
}
