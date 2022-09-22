package mk.ukim.finki.emt.usermanagement.services.form;

import lombok.Data;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;

import javax.validation.constraints.NotNull;

@Data
public class UserRegisterForm {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private UserId username;

    @NotNull
    private String password;

    @NotNull
    private String repeatedPassword;
}
