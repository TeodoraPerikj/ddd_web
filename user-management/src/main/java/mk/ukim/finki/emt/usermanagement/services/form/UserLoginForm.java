package mk.ukim.finki.emt.usermanagement.services.form;

import lombok.Data;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginForm {

//    @NotNull
//    private UserId username;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
