package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;

@Getter
public class UserLoginDto implements ValueObject {

    @JsonProperty("username")
    private final UserId username;

    @JsonProperty("password")
    private final String password;

    public UserLoginDto() {
        this.username = UserId.randomId(UserId.class);
        this.password = "";
    }

    @JsonCreator
    public UserLoginDto(@JsonProperty("username") UserId username,
                        @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
