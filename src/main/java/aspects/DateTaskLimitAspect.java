package aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import tasks.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class DateTaskLimitAspect {

    private int maxTasksPerDate = 15;
    private Map<LocalDate, Integer> taskCountByDate = new HashMap<>();

    @Pointcut("execution(public * tasks.TaskManager.addTask(tasks.Task)) && args(task)")
    public void addTaskMethod(Task task) {}

    @Around(value = "addTaskMethod(task)", argNames = "proceedingJoinPoint,task")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Task task) throws Throwable {
        LocalDate date = task.getDate();
        int tasksAddedForDate = taskCountByDate.getOrDefault(date, 0);
        if (tasksAddedForDate >= maxTasksPerDate) {
            throw new IllegalStateException("Maximum task limit reached for " + date);
        }
        return proceedingJoinPoint.proceed();
    }

    @After("addTaskMethod(task)")
    public void afterAddTask(Task task) {
        LocalDate date = task.getDate();
        int tasksAddedForDate = taskCountByDate.getOrDefault(date, 0);
        taskCountByDate.put(date, tasksAddedForDate + 1);
    }
}
