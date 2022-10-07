package mk.ukim.finki.emt.taskmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskCreateForm;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskEditForm;
import mk.ukim.finki.emt.taskmanagement.xport.client.UserClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class TasksResource {

    private final TaskService taskService;
    private final UserClient userClient;

    @GetMapping(value = {"/api", "/api/tasks"})
    public List<EachTaskDto> getAllTasks(){
        return this.taskService.findEachTask();
    }

    @GetMapping("/api/tasks/edit-task/{id}")
    public ResponseEntity<Task> getEditTaskPage2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        Optional<Task> task = this.taskService.findById(taskId);

        return task.map(taskById -> ResponseEntity.ok().body(taskById))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/tasks/add")
    public ResponseEntity<Task> addNewTask2(@RequestBody TaskDto taskDto){
        //User assignedUsers = this.userService.findAssignedUsers(taskDto.getAssignee());

        /// GET USERS FROM CLIENT

        String title = taskDto.getTitle();
        String description = taskDto.getDescription();
        String startDate = taskDto.getStartDate();
        String dueDate = taskDto.getDueDate();
        Integer days = Integer.valueOf(taskDto.getEstimationDays());
        User creator = this.userClient.findByUsername(taskDto.getCreator());
        User assignee = this.userClient.findByUsername(taskDto.getAssignee());

        UserId creatorId = creator.getId();
        UserId assigneeId = assignee.getId();

        TaskCreateForm taskCreateForm = new TaskCreateForm();

        taskCreateForm.setTitle(title);
        taskCreateForm.setDescription(description);
        taskCreateForm.setStartDate(startDate);
        taskCreateForm.setDueDate(dueDate);
        taskCreateForm.setEstimationDays(days);
        taskCreateForm.setCreator(creatorId);
        taskCreateForm.setAssignee(assigneeId);

        return this.taskService.create(taskCreateForm)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/api/tasks/add/{id}")
    public ResponseEntity<Task> editTask2(@PathVariable String id, @RequestBody TaskDto taskDto){

        UserId userId = new UserId();
        TaskId taskId = new TaskId(id);

        if(taskDto.getAssignee() != null) {
            //TODO
            //userId = this.userService.findAssignedUsers(taskDto.getAssignee());
            User assignee = this.userClient.findByUsername(taskDto.getAssignee());
            userId = assignee.getId();
        }
        else{
            Task task = null;
            if(this.taskService.findById(taskId).isPresent())
                task = this.taskService.findById(taskId).get();

            assert task != null;
            userId = task.getAssignee();
        }

        String title = taskDto.getTitle();
        String description = taskDto.getDescription();
        String startDate = taskDto.getStartDate();
        String dueDate = taskDto.getDueDate();
        Integer days = Integer.valueOf(taskDto.getEstimationDays());

        TaskEditForm taskEditForm = new TaskEditForm();

        taskEditForm.setTitle(title);
        taskEditForm.setDescription(description);
        taskEditForm.setStartDate(startDate);
        taskEditForm.setDueDate(dueDate);
        taskEditForm.setEstimationDays(days);
        taskEditForm.setAssignee(userId);

        return this.taskService.edit(taskId, taskEditForm)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/api/tasks/delete/{id}")
    public ResponseEntity deleteTask2(@PathVariable String id){

        TaskId taskId = new TaskId(id);

        this.taskService.delete(taskId);

        if(this.taskService.findById(taskId).isPresent())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/findById")
    public ResponseEntity<Task> findById(@RequestParam("taskId") TaskId taskId){
        return this.taskService.findById(taskId)
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/setComment")
    public Boolean setComment(@RequestParam TaskId taskId,
                              @RequestParam CommentId commentId){

        return this.taskService.setComment(taskId, commentId);
    }

    @DeleteMapping("/api/deleteComment")
    public Boolean deleteComment(@RequestParam TaskId taskId){

        return this.taskService.deleteComment(taskId);
    }

    @DeleteMapping("/api/deleteTaskAssigned")
    public Boolean deleteTaskAssigned(@RequestParam TaskId taskId){

        return this.taskService.deleteTaskAssigned(taskId);
    }

    @DeleteMapping("/api/deleteTaskOwned")
    public Boolean deleteTaskOwned(@RequestParam TaskId taskId){

        return this.taskService.delete(taskId);
    }
}
