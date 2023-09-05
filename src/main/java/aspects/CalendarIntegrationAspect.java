package aspects;

import aspects.utils.CalendarAuth;
import com.google.api.client.util.DateTime;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.time.LocalDate;

@Aspect
public class CalendarIntegrationAspect {

    private final Calendar calendar;

    public CalendarIntegrationAspect() throws Exception {
        this.calendar = CalendarAuth.getCalendarService();
    }

    @Before("execution(public * tasks.TaskManager.addTask(tasks.Task)) && args(task)")
    public void addTaskToGoogleCalendar(tasks.Task task) {
        try {
            Event event = createEventFromTask(task);
            Event createdEvent = calendar.events().insert("primary", event).execute();
            System.out.println("Event created: " + createdEvent.getHtmlLink());
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

