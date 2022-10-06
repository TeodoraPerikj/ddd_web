package mk.ukim.finki.emt.taskmanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.exceptions.*;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.repository.TaskRepository;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.taskmanagement.service.TaskService;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskCreateForm;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskEditForm;
import mk.ukim.finki.emt.taskmanagement.xport.client.UserClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;

    @Override
    public Optional<Task> create(TaskCreateForm taskForm) {

        Objects.requireNonNull(taskForm, "task create form must not be null!");

        LocalDateTime localStartDate = LocalDateTime.parse(taskForm.getStartDate());
        LocalDateTime localDueDate = LocalDateTime.parse(taskForm.getDueDate());

        long daysToWorkOnTask = java.time.temporal.ChronoUnit.DAYS
                .between( localStartDate.toLocalDate(), localDueDate.toLocalDate()) ;

        if(taskForm.getEstimationDays() > daysToWorkOnTask) {
            throw new EstimationDaysGreaterThanDaysToWorkOnTaskException(taskForm.getEstimationDays(), daysToWorkOnTask);
        }

        if(localStartDate.isAfter(localDueDate))
            throw new LocalDateTimeException();
        
        //TODO: POST to User Management Application to set TaskId Assigned and TaskId Owned

        Task task = Task.build(taskForm.getTitle(), taskForm.getDescription(), localStartDate,
                localDueDate, taskForm.getEstimationDays(), taskForm.getCreator(), taskForm.getAssignee());

        if(!this.userClient.setTaskAssigned(task.getId(), task.getAssignee()))
            throw new CannotAssignedTask(task.getAssignee().getId(), task.getId().toString());
        if(!this.userClient.setTaskOwned(task.getId(), task.getCreator()))
            throw new CannotOwnedTask(task.getCreator().getId(), task.getId().toString());

        return Optional.of(taskRepository.saveAndFlush(task));
    }

    @Override
    public Optional<Task> edit(TaskId id, TaskEditForm taskForm) {

        Objects.requireNonNull(taskForm, "task edit form must not be null!");

        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.changeTitle(taskForm.getTitle());
        task.changeDescription(taskForm.getDescription());

        LocalDateTime localStartDate = LocalDateTime.parse(taskForm.getStartDate());
        LocalDateTime localDueDate = LocalDateTime.parse(taskForm.getDueDate());

        if(localStartDate.isAfter(localDueDate))
            throw new LocalDateTimeException();

        task.changeStartDate(localStartDate);
        task.changeDueDate(localDueDate);

        long daysToWorkOnTask = java.time.temporal.ChronoUnit.DAYS
                .between( localStartDate.toLocalDate(), localDueDate.toLocalDate()) ;

        if(taskForm.getEstimationDays() > daysToWorkOnTask) {
            throw new EstimationDaysGreaterThanDaysToWorkOnTaskException(taskForm.getEstimationDays(), daysToWorkOnTask);
        }

        task.changeEstimationDays(taskForm.getEstimationDays());
        task.changeAssignee(taskForm.getAssignee());

        return Optional.of(taskRepository.saveAndFlush(task));
    }

    @Override
    public void delete(TaskId id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }

    @Override
    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return Optional.of(taskRepository.findById(id).orElseThrow(TaskNotFoundException::new));
    }

    @Override
    public List<EachTaskDto> findEachTask() {
        List<Task> tasks = this.taskRepository.findAll();
        List<EachTaskDto> eachTaskDtos = new ArrayList<>();

        for(Task task : tasks){

//            UserId assignee = task.getAssignee();
//
//            UserId creator = task.getCreator();

            User assignee = this.userClient.findById(task.getAssignee());
            User creator = this.userClient.findById(task.getCreator());

            //TODO: Find by Username and then by Id

            CommentId commentId = task.getComment();

            EachTaskDto eachTaskDto = new EachTaskDto(task.getId().toString(), task.getTitle(), task.getDescription(),
                    task.getStartDate(), task.getDueDate(), task.getEstimationDays(), task.getStatus(),
                    assignee.getUsername(), creator.getUsername(), commentId.toString());

            eachTaskDtos.add(eachTaskDto);
        }
        return eachTaskDtos;
    }

    @Override
    public Optional<TaskInfoDto> showTaskInfo(TaskId taskId, String userId) {
        Task task = null;

        if(this.findById(taskId).isPresent())
            task = this.findById(taskId).get();

        String textAndType = this.filter(task, userId);
        UserId assignee = task.getAssignee();

        TaskInfoDto taskInfoDto = new TaskInfoDto(task, textAndType, assignee.getId());

        return Optional.of(taskInfoDto);
    }

    @Override
    public String filter(Task task, String username) {
        //User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        // FIND BY USERNAME

        LocalDateTime dateTime = LocalDateTime.now();

        if(task.getStartDate().isAfter(dateTime))
            return "Not opened yet!";
        else if(task.getDueDate().isBefore(dateTime))
            return "Task is closed!";
        else {

            if(task.getStatus().equals(TaskStatus.TODO)){

                if (task.getAssignee().getId().equals(username))
                    return "Start Task";

                return "Join Task";
            } else if(task.getStatus().equals(TaskStatus.InProgress)) {

                if (task.getAssignee().getId().equals(username))
                    return "Continue Task";

                return "Join Task";
            } else if(task.getStatus().equals(TaskStatus.Done)){
                return "Finished";
            } else {
                return "Task has been canceled";
            }

        }
    }

    @Override
    public Optional<WorkOnTaskDto> showWorkOnTaskPage(TaskId taskId) {
        Task task = null;

        if(this.findById(taskId).isPresent())
            task =this.findById(taskId).get();

        if(task.getStatus().equals(TaskStatus.TODO)){
            if(this.changeStatus(taskId, TaskStatus.InProgress).isPresent())
                task = this.changeStatus(taskId, TaskStatus.InProgress).get();
        }

        UserId assignee = task.getAssignee();

        CommentId commentId = task.getComment();

        WorkOnTaskDto workOnTaskDto = new WorkOnTaskDto(task, assignee.getId(), commentId.getId());

        return Optional.of(workOnTaskDto);
    }

    @Override
    public Optional<Task> changeStatus(TaskId id, TaskStatus status) {

        Task task = this.taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        task.changeStatus(status);

        return Optional.of(this.taskRepository.save(task));
    }
}
