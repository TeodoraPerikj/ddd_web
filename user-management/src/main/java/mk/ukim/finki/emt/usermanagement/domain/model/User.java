package mk.ukim.finki.emt.usermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class User extends AbstractEntity<UserId> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("password")
    private String password;

    private User(){
        super(UserId.randomId(UserId.class));
    }

    public static User build(String name, String surname, String password){
        User user = new User();

        user.name = name;
        user.surname = surname;
        user.password = password;

        return user;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeSurname(String surname){
        this.surname = surname;
    }

    public void changePassword(String password){
        this.password = password;
    }

//    @JsonIgnore
//    @ManyToMany(mappedBy = "assignees", fetch = FetchType.EAGER)
//    private List<Task> taskAssigned;
//
//
//    @ManyToMany(mappedBy = "assignees", fetch = FetchType.EAGER)
//    private List<Task> taskAssigned;


//    @JsonIgnore
//    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
//    private List<Task> taskOwned;
////
//    public User() {
//    }
//
//    public User(String name, String surname, String password) {
//
//        this.name = name;
//        this.surname = surname;
//        this.password = password;
//        this.taskAssigned = new ArrayList<>();
//    }
}
