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

    public List<Task> searchByDateInterval(LocalDate startDate, LocalDate endDate) {
        return tasks.stream()
                .filter(task -> task.getDate().isAfter(startDate) && task.getDate().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<Task> searchByTitle(String targetTitle) {
        return tasks.stream()
                .filter(task -> task.getTitle().equals(targetTitle))
                .collect(Collectors.toList());
    }

    public void deleteByDate(LocalDate targetDate) {
        tasks.removeIf(task -> task.getDate().equals(targetDate));
    }

    public void deleteByDateInterval(LocalDate startDate, LocalDate endDate) {
        tasks.removeIf(task -> task.getDate().isAfter(startDate) && task.getDate().isBefore(endDate));
    }

    public List<Task> searchByPriority(Priority targetPriority) {
        return tasks.stream()
                .filter(task -> task.getPriority().equals(targetPriority))
                .collect(Collectors.toList());
    }
}
