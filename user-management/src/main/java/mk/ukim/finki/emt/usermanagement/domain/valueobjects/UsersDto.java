package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;

@Getter
public class UsersDto implements ValueObject {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("surname")
    private final String surname;

//    @JsonProperty("username")
//    private final UserId username;

    @JsonProperty("username")
    private final String username;

    private UsersDto(){
        this.name = "";
        this.surname = "";
        //this.username = UserId.randomId(UserId.class);
        this.username = "";
    }

    @JsonCreator
    public UsersDto(@JsonProperty("name") String name,
                    @JsonProperty("surname") String surname,
                    @JsonProperty("username") String username){
                    //UserId username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }
}
