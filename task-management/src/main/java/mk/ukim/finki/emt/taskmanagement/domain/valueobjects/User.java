package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

@Getter
public class User implements ValueObject {

    @JsonProperty("id")
    private final UserId id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("surname")
    private final String surname;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("password")
    private final String password;

    public User() {
        this.id = UserId.randomId(UserId.class);
        this.name = "";
        this.surname = "";
        this.username = "";
        this.password = "";
    }

    @JsonCreator
    public User(@JsonProperty("username") String username,
            //@JsonProperty("id") UserId id,
                @JsonProperty("name") String name,
                @JsonProperty("surname") String surname,
                @JsonProperty("password") String password) {
        this.id = UserId.randomId(UserId.class);
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }
}
