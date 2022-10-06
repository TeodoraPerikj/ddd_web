package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.usermanagement.domain.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserPerformanceDto implements ValueObject {

    @JsonProperty("user")
    private final User user;

    @JsonProperty("numberOfTasks")
    private final List<Integer> numberOfTasks;

    @JsonProperty("calculatedPerformanceAndRating")
    private final List<String> calculatedPerformanceAndRating;

    @JsonProperty("calculatedPerformance")
    private final Double calculatedPerformance;

    @JsonProperty("type")
    private final String type;

    private UserPerformanceDto(){
        this.user = new User();
        this.numberOfTasks = new ArrayList<>();
        this.calculatedPerformanceAndRating = new ArrayList<>();
        this.calculatedPerformance = 0.0;
        this.type = "";
    }

    @JsonCreator
    public UserPerformanceDto(@JsonProperty("user") User user,
                              @JsonProperty("numberOfTasks") List<Integer> numberOfTasks,
                              @JsonProperty("calculatedPerformanceAndRating") List<String> calculatedPerformanceAndRating,
                              @JsonProperty("calculatedPerformance") Double calculatedPerformance,
                              @JsonProperty("type") String type){

        this.user = user;
        this.numberOfTasks = numberOfTasks;
        this.calculatedPerformanceAndRating = calculatedPerformanceAndRating;
        this.calculatedPerformance = calculatedPerformance;
        this.type = type;
    }
}
