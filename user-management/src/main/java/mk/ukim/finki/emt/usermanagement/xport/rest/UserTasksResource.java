package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.MyUserTasksDto;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.xport.client.TaskClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/findUser")
@AllArgsConstructor
public class UserTasksResource {

    private final UserService userService;
    private final TaskClient taskClient;

    @GetMapping
    public MyUserTasksDto findUser(@RequestParam String username) {

        //UserId userId = new UserId(username);
        List<Task> tasks = taskClient.findAll();

        return this.userService.findTasksForTheUser(username, tasks);
    }
}
