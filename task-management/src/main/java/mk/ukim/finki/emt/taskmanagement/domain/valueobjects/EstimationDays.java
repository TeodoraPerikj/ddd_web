package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class EstimationDays implements ValueObject {

    private final int estimationDays;

    protected EstimationDays(){
        this.estimationDays = 0;
    }

    public EstimationDays(@NonNull int estimationDays){
        this.estimationDays = estimationDays;
    }

    public static EstimationDays valueOf(int estimationDays){
        return new EstimationDays(estimationDays);
    }

    public static Long parseToLong(EstimationDays estimationDays){
        return Long.parseLong(String.valueOf(estimationDays));
    }
}
