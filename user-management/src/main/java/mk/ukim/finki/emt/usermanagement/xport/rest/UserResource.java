package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
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

        return this.userService.register(userRegisterForm)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
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

}
