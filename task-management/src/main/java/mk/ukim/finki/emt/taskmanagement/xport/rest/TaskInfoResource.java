package mk.ukim.finki.emt.taskmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.TaskInfoDto;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/taskInfo")
@AllArgsConstructor
public class TaskInfoResource {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskInfoDto> showTaskInfo2(@PathVariable String id,
                                                     @RequestParam("username") String activeUser){

        TaskId taskId = new TaskId(id);

        return this.taskService.showTaskInfo(taskId, activeUser)
                .map(taskInfoDto -> ResponseEntity.ok().body(taskInfoDto))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
