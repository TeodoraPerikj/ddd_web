package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class EstimationDaysGreaterThanDaysToWorkOnTaskException extends RuntimeException {

    public EstimationDaysGreaterThanDaysToWorkOnTaskException(Integer estimationDays, long daysToWorkOnTask){
        super(String.format("Estimation days %d can not be greater " +
                "than days given to work on task: %d", estimationDays, daysToWorkOnTask));
    }

}
