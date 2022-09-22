package mk.ukim.finki.emt.usermanagement.services;

import mk.ukim.finki.emt.usermanagement.domain.exceptions.*;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.MyUserTasksDto;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskStatus;
import mk.ukim.finki.emt.usermanagement.services.form.UserEditForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserRegisterForm;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(UserLoginForm userLoginForm) throws InvalidArgumentsException, InvalidUserCredentialsException;

    User  register(UserRegisterForm userRegisterForm) throws InvalidArgumentsException, PasswordsDoNotMatchException,
            UsernameAlreadyExistsException;

    User  edit(UserEditForm userEditForm) throws UserNotFoundException;

    void delete(UserId username) throws UserNotFoundException;

    //List<Task> listTasksByStatus(UserId username, TaskStatus status);

    List<User> listAll();

    User findByUsername(UserId username) throws UserNotFoundException;

    //MyUserTasksDto findTasksForTheUser(UserId username);

    //List<Integer> findNumberOfTasks(UserId username);
}
