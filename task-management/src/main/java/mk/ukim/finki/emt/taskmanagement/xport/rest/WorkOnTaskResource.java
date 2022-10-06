package mk.ukim.finki.emt.taskmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.Comment;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.CommentId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.TaskStatus;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.WorkOnTaskDto;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/workOnTask")
@AllArgsConstructor
public class WorkOnTaskResource {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<WorkOnTaskDto> showWorkOnTaskPage2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        return this.taskService.showWorkOnTaskPage(taskId)
                .map(workOnTaskDto -> ResponseEntity.ok().body(workOnTaskDto))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{id}/finishTask")
    public ResponseEntity<Task> finishTask2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        return this.taskService.changeStatus(taskId, TaskStatus.Done)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{id}/cancelTask")
    public ResponseEntity<Task> cancelTask2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        return this.taskService.changeStatus(taskId, TaskStatus.Canceled)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
