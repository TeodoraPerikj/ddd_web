package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.exceptions.InvalidArgumentsException;
import mk.ukim.finki.emt.usermanagement.domain.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.emt.usermanagement.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.UsersDto;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.services.form.UserRegisterForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/user")
    public List<User> getAll() {
        return userService.listAll();
    }

    @GetMapping("/showUsersDto")
    public List<UsersDto> showUsersDto(){
        return this.userService.showUsersDto();
    }

    @GetMapping("/notAssignedUsers")
    public List<UsersDto> notAssignedUsers(){
        return this.userService.getNotAssignedUsers();
    }

    @GetMapping("/findByUsername")
    public User findByUsername(@RequestParam("username") String username){
        return this.userService.findByUsername(username);
    }

    @GetMapping("/findById")
    public User findById(@RequestParam("userId") UserId userId){
        return this.userService.findById(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterForm userRegisterForm) {

        Optional<User> user;
        try {
            user = this.userService.register(userRegisterForm);
        }catch (PasswordsDoNotMatchException exception){
            return ResponseEntity.badRequest().build();
        }catch (UsernameAlreadyExistsException exception){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(user);

    }

    @PostMapping("/setTaskAssigned")
    public Boolean setTaskAssigned(@RequestParam TaskId taskId,
                                @RequestParam UserId userId){

        if(this.userService.setTaskAssigned(taskId, userId))
            return true;

        return false;
    }

    @PostMapping("/setTaskOwned")
    public Boolean setTaskOwned(@RequestParam TaskId taskId,
                                   @RequestParam UserId userId){

        if(this.userService.setTaskOwned(taskId, userId))
            return true;

        return false;
    }

    @DeleteMapping("/deleteTaskAssigned")
    public Boolean deleteTaskAssigned(@RequestParam UserId userId){

        if(this.userService.deleteTaskAssigned(userId))
            return true;

        return false;
    }

    @DeleteMapping("/deleteTaskOwned")
    public Boolean deleteTaskOwned(@RequestParam UserId userId){

        if(this.userService.deleteTaskOwned(userId))
            return true;

        return false;
    }

    @PostMapping("/changeRole")
    public User changeRole(@RequestParam String username, @RequestParam String role){
        return this.userService.changeRole(username, role);
    }
}
