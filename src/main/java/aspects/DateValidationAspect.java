package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import tasks.Task;

import java.time.LocalDate;

@Aspect
public class DateValidationAspect {

    @Pointcut(value = "execution(public * tasks.TaskManager.addTask(tasks.Task)) && args(task)", argNames = "task")
    public void addTaskMethod(Task task) {
    }

    @Pointcut(value = "execution(public * tasks.TaskManager.searchByDateInterval(java.time.LocalDate, java.time.LocalDate)) && args(startDate, endDate)", argNames = "startDate,endDate")
    public void searchByDateIntervalMethod(LocalDate startDate, LocalDate endDate) {
    }

    @Pointcut(value = "execution(public * tasks.TaskManager.deleteByDateInterval(java.time.LocalDate, java.time.LocalDate)) && args(startDate, endDate)", argNames = "startDate,endDate")
    public void deleteByDateIntervalMethod(LocalDate startDate, LocalDate endDate) {
    }

    @Around(value = "addTaskMethod(task)", argNames = "proceedingJoinPoint,task")
    public Object aroundAdvice1(ProceedingJoinPoint proceedingJoinPoint, Task task) throws Throwable {
        LocalDate today = LocalDate.now();
        LocalDate date = task.getDate();
        if (date.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }
        return proceedingJoinPoint.proceed();
    }

    @Around(value = "searchByDateIntervalMethod(startDate,endDate)", argNames = "proceedingJoinPoint,startDate,endDate")
    public Object aroundAdvice2(ProceedingJoinPoint proceedingJoinPoint, LocalDate startDate, LocalDate endDate) throws Throwable {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The start date of the interval must not be after the end date of the interval.");
        }
        return proceedingJoinPoint.proceed();
    }

    @Around(value = "deleteByDateIntervalMethod(startDate,endDate)", argNames = "proceedingJoinPoint,startDate,endDate")
    public Object aroundAdvice3(ProceedingJoinPoint proceedingJoinPoint, LocalDate startDate, LocalDate endDate) throws Throwable {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The start date of the interval must not be after the end date of the interval.");
        }
        return proceedingJoinPoint.proceed();
    }

}
