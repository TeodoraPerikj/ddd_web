package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

@Getter
public class UserLoginReturnedDto implements ValueObject {
    @JsonProperty("username")
    private final String username;

    @JsonProperty("id")
    private final String id;

    @JsonProperty("role")
    private final String role;

    @JsonProperty("ownedTask")
    private final boolean ownedTask;

    public UserLoginReturnedDto() {
//        this.username = UserId.randomId(UserId.class);
        this.username = "";
        this.id = "";
        this.role = "";
        this.ownedTask = false;
    }

    @JsonCreator
    public UserLoginReturnedDto(@JsonProperty("username") String username,
                                @JsonProperty("id") String id,
                                @JsonProperty("role") String role,
                                @JsonProperty("ownedTask") boolean ownedTask) {
        this.username = username;
        this.id = id;
        this.role = role;
        this.ownedTask = ownedTask;
    }
}
