package aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import tasks.ToDoAppGUI;

import java.util.logging.Logger;

@Aspect
public class ExceptionAspect {
    private Throwable lastThrowable = null;

    private static final Logger logger = Logger.getLogger(ToDoAppGUI.class.getName());

    @AfterThrowing(pointcut = "execution(* tasks..*(..))", throwing = "exception")
    public void handleException(Exception exception) {
        if (lastThrowable != exception) {
            lastThrowable = exception;
            String errorMessage = "Exception occurred: " + exception.getMessage();
            StackTraceElement[] stackTrace = exception.getStackTrace();
            String callingMethod = (stackTrace != null && stackTrace.length > 0) ? stackTrace[0].toString() : "Unknown method";
            String logMessage = "Error in method " + callingMethod + ": " + errorMessage;
            logger.severe(logMessage);
        }
    }
}
