package aspects;

import aspects.utils.EmailConfigKey;
import aspects.utils.EmailSender;
import config.ConfigurationManager;
import org.aspectj.lang.annotation.*;
import tasks.Task;


@Aspect
public class TaskEmailNotificationAspect {

    @Pointcut("execution(private * tasks.TaskManager.addTaskToTasks(tasks.Task)) && args(task)")
    public void addTaskMethod(Task task) {}


    @After("addTaskMethod(task)")
    public void afterAddTask(Task task) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String emailReceiverEmail = configurationManager.getProperty(EmailConfigKey.EMAIL_RECEIVER_EMAIL);

        String subject = "Notification: New Task Added";
        String message = "Dear user,<br><br>" +
                "We would like to inform you that a new task has been added to your To-Do list:<br><br>" +
                "<strong>Task Details:</strong><br>" +
                "Title: " + task.getTitle() + "<br>" +
                "Date: " + task.getDate() + "<br>" +
                "Priority: " + task.getPriority() + "<br><br>" +
                "Thank you for using our To-Do app!<br><br>" +
                "Best regards,<br>" +
                "Your Reminder Team";

        EmailSender emailSender = createEmailSender();
        emailSender.sendEmail(emailReceiverEmail, subject, message);
    }

    private EmailSender createEmailSender() {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String host = configurationManager.getProperty(EmailConfigKey.HOST);
        String emailSenderEmail = configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_EMAIL);
        String emailSenderPassword = configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_PASSWORD);
        int hostPort = getHostPort(configurationManager);
        return new EmailSender(host, hostPort, emailSenderEmail, emailSenderPassword);
    }

    private static int getHostPort(ConfigurationManager configurationManager) {
        try {
            return Integer.parseInt(configurationManager.getProperty(EmailConfigKey.PORT));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
