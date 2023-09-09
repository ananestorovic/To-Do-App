package aspects;

import aspects.utils.CalendarAuth;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Events;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Aspect
public class CalendarIntegrationAspect {

    private final Calendar calendar;

    public CalendarIntegrationAspect() throws Exception {
        this.calendar = CalendarAuth.getCalendarService();
    }

    @Before("execution(private * tasks.TaskManager.addTaskToTasks(tasks.Task)) && args(task)")
    public void addTaskToGoogleCalendar(tasks.Task task) {
        try {
            Event event = createEventFromTask(task);
            Event createdEvent = calendar.events().insert("primary", event).execute();
            System.out.println("INFO: Event created: " + createdEvent.getHtmlLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After("execution(public * tasks.TaskManager.deleteByDate(LocalDate)) && args(targetDate)")
    public void deleteTaskFromGoogleCalendar(LocalDate targetDate) {
        try {
            String calendarId = "primary";
            Events events = calendar.events().list(calendarId)
                    .setTimeMin(new DateTime(targetDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toString()))
                    .setTimeMax(new DateTime(targetDate.atTime(LocalTime.MAX).atZone(ZoneId.of("UTC")).toInstant().toString()))
                    .execute();

            for (Event event : events.getItems()) {
                calendar.events().delete(calendarId, event.getId()).execute();
            }

            System.out.println("INFO: Tasks for " + targetDate + " have been deleted from Google Calendar.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After("execution(public * tasks.TaskManager.deleteByDateInterval(LocalDate, LocalDate)) && args(startDate, endDate)")
    public void deleteTasksFromGoogleCalendar(LocalDate startDate, LocalDate endDate) {
        try {
            String calendarId = "primary";
            Events events = calendar.events().list(calendarId)
                    .setTimeMin(new DateTime(startDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toString()))
                    .setTimeMax(new DateTime(endDate.atTime(LocalTime.MAX).atZone(ZoneId.of("UTC")).toInstant().toString()))
                    .execute();

            for (Event event : events.getItems()) {
                calendar.events().delete(calendarId, event.getId()).execute();
            }

            System.out.println("INFO: Tasks from " + startDate + " to " + endDate + " have been deleted from Google Calendar.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Event createEventFromTask(tasks.Task task) {
        Event event = new Event()
                .setSummary(task.getTitle())
                .setDescription("Priority: " + task.getPriority());

        LocalDate date = task.getDate();
        DateTime startDateTime = new DateTime(date.toString() + "T00:00:00Z");
        DateTime endDateTime = new DateTime(date.toString() + "T23:59:59Z");

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("UTC");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("UTC");
        event.setEnd(end);

        return event;
    }
}

