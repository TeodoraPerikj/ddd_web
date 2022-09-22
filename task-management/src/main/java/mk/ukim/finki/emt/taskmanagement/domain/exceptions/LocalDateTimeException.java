package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class LocalDateTimeException extends RuntimeException {
    public LocalDateTimeException(){
        super("Start date can not be after due date!");
    }
}
