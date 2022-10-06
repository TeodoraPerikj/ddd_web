package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

//@Getter
//public class UserRegistrationDto implements ValueObject {
//
//    @JsonProperty("username")
//    private final String username;
//
//    @JsonProperty("password")
//    private final String password;
//
//    @JsonProperty("repeatedPassword")
//    private final String repeatedPassword;
//
//    @JsonProperty("name")
//    private final String name;
//
//    @JsonProperty
//    private final String surname;
//
//    private UserRegistrationDto(){
//        this.username = "";
//        this.password = "";
//        this.repeatedPassword = "";
//        this.name = "";
//        this.surname = "";
//    }
//
//    @JsonCreator
//    public UserRegistrationDto(@JsonProperty("username") String username,
//                               @JsonProperty("password") String password,
//                               @JsonProperty("repeatedPassword") String repeatedPassword,
//                               @JsonProperty("name") String name,
//                               @JsonProperty("surname") String surname){
//        //UserId username) {
//        this.username = username;
//        this.password = password;
//        this.repeatedPassword = repeatedPassword;
//        this.name = name;
//        this.surname = surname;
//    }
//}
