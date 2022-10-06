package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.UserPerformanceDto;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.xport.client.TaskClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/showUserPerformance")
@AllArgsConstructor
public class UserPerformanceResource {

    private final UserService userService;
    private final TaskClient taskClient;

    @GetMapping
    public ResponseEntity<UserPerformanceDto> showUserPerformance2(@RequestParam String chosenUsername,
                                                                   @RequestParam String chosenType,
                                                                   @RequestParam(required = false) String dateFrom,
                                                                   @RequestParam(required = false) String dateTo){
        List<Task> tasks = taskClient.findAll();

        return this.userService.showUserPerformance(chosenUsername, chosenType,
                dateFrom, dateTo, tasks)
                .map(userPerformanceDto -> ResponseEntity.ok().body(userPerformanceDto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
