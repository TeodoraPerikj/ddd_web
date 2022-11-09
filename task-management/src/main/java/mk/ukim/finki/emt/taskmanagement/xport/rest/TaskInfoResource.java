package mk.ukim.finki.emt.taskmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.TaskInfoAndActiveUserDto;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.TaskInfoDto;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserLoginReturnedDto;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import mk.ukim.finki.emt.taskmanagement.xport.client.UserClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/taskInfo")
@AllArgsConstructor
public class TaskInfoResource {

    private final TaskService taskService;
    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoAndActiveUserDto> showTaskInfo2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        UserLoginReturnedDto activeUser = this.userClient.getActiveUser();

        Optional<TaskInfoDto> taskInfoDto = this.taskService.showTaskInfo(taskId, activeUser.getUsername());

        TaskInfoDto foundTaskInfo = null;

        if(taskInfoDto.isPresent())
            foundTaskInfo = taskInfoDto.get();

        String creatorUsername = this.userClient.findById(foundTaskInfo.getTask().getCreator()).getUsername();

        Optional<TaskInfoAndActiveUserDto> taskAndActiveUser = Optional.of(new TaskInfoAndActiveUserDto(
                foundTaskInfo.getTask(), foundTaskInfo.getTextAndType(), foundTaskInfo.getAssignee(),
                creatorUsername, activeUser.getUsername(), activeUser.getRole()));

        return taskAndActiveUser.map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
