package mk.ukim.finki.emt.usermanagement.services;

import mk.ukim.finki.emt.usermanagement.domain.exceptions.*;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.usermanagement.services.form.UserEditForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserRegisterForm;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(UserLoginForm userLoginForm) throws InvalidArgumentsException, InvalidUserCredentialsException;

    Optional<User>  register(UserRegisterForm userRegisterForm) throws InvalidArgumentsException, PasswordsDoNotMatchException,
            UsernameAlreadyExistsException;

    User  edit(UserEditForm userEditForm) throws UserNotFoundException;

    void delete(UserId username) throws UserNotFoundException;

    //List<Task> listTasksByStatus(UserId username, TaskStatus status, List<Task> tasks);

    List<Task> listTasksByStatus(String username, TaskStatus status, List<Task> tasks);

    List<User> listAll();

    User findByUsername(String username) throws UserNotFoundException;

    MyUserTasksDto findTasksForTheUser(String username, List<Task> tasks);

    List<Integer> findNumberOfTasks(String username, List<Task> tasks);

    Optional<UserPerformanceDto> showUserPerformance(String chosenUsername, String chosenType,
                                                     String dateFrom, String dateTo, List<Task> tasks);

    List<Integer> filterByDate(String username, String dateFrom, String dateTo, List<Task> tasks);

    List<Integer> findNumberOfTasksByType(String username, String type, List<Task> tasks);

    List<String> calculatePerformanceAndRating(List<Integer> numberOfTasks);

    List<UsersDto> showUsersDto();

    User findById(UserId userId);

    boolean setTaskAssigned(TaskId taskId, UserId userId);

    boolean setTaskOwned(TaskId taskId, UserId userId);
}
