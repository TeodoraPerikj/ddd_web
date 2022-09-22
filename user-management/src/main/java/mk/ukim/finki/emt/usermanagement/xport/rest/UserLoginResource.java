package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.UserLoginDto;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/login")
@AllArgsConstructor
public class UserLoginResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> login2(@RequestBody UserLoginDto userLoginDto){

        UserId username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setUsername(username);
        userLoginForm.setPassword(password);

        Optional<User> user = this.userService.login(userLoginForm);

        return user.map(user1 -> ResponseEntity.ok().body(user1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
