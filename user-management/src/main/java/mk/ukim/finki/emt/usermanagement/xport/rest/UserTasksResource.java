package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.MyUserTasksDto;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/findUser")
@AllArgsConstructor
public class UserTasksResource {

    private final UserService userService;

//    @GetMapping
//    public MyUserTasksDto findUser(@RequestParam String username) {
//
//        UserId userId = new UserId(username);
//
//        return this.userService.findTasksForTheUser(userId);
//    }
}
