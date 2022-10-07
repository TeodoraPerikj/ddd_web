package mk.ukim.finki.emt.taskmanagement.service;

import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskCreateForm;
import mk.ukim.finki.emt.taskmanagement.service.form.TaskEditForm;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> create(TaskCreateForm taskForm);

    Optional<Task> edit(TaskId id, TaskEditForm taskForm);

    boolean delete(TaskId id);

    List<Task> listAll();

    Optional<Task> findById(TaskId id);

    List<EachTaskDto> findEachTask();

    Optional<TaskInfoDto> showTaskInfo(TaskId taskId, String activeUser);

    String filter(Task task, String username);

    Optional<WorkOnTaskDto> showWorkOnTaskPage(TaskId taskId);

    Optional<Task> changeStatus(TaskId id, TaskStatus status);

    Boolean setComment(TaskId taskId, CommentId commentId);

    Boolean deleteComment(TaskId taskId);

    Boolean deleteTaskAssigned(TaskId taskId);
}
