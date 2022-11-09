package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.UserLoginDto;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.UserLoginReturnedDto;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import org.apache.coyote.Response;
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
    public ResponseEntity<UserLoginReturnedDto> login2(@RequestBody UserLoginDto userLoginDto){

        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setUsername(username);
        userLoginForm.setPassword(password);

        Optional<UserLoginReturnedDto> user = this.userService.login(userLoginForm);

        return user.map(user1 -> ResponseEntity.ok().body(user1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<UserLoginReturnedDto> getLoggedUser(){
        Optional<UserLoginReturnedDto> activeUser = this.userService.getLoggedUser();

        return activeUser.map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
