package mk.ukim.finki.emt.usermanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.exceptions.*;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.repository.UserRepository;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskStatus;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.services.form.UserEditForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserRegisterForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> login(UserLoginForm userLoginForm) {

        Objects.requireNonNull(userLoginForm, "user login must not be null!");

        if(userLoginForm.getUsername() == null || userLoginForm.getPassword() == null )
            throw new InvalidArgumentsException();

        Optional<User> user = this.userRepository.
                findByIdAndPassword(userLoginForm.getUsername(), userLoginForm.getPassword());

        userRepository.saveAndFlush(user.get());

        return Optional.of(user.orElseThrow(InvalidUserCredentialsException::new));
    }

    @Override
    public User register(UserRegisterForm userRegisterForm) {

        Objects.requireNonNull(userRegisterForm, "user registration must not be null!");

        if(userRegisterForm.getUsername() == null || userRegisterForm.getPassword() == null)
            throw new InvalidArgumentsException();

        if(!userRegisterForm.getPassword().equals(userRegisterForm.getRepeatedPassword()))
            throw new PasswordsDoNotMatchException();

        if(this.userRepository.findById(userRegisterForm.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(String.valueOf(userRegisterForm.getUsername()));

        User user = User.build(userRegisterForm.getName(), userRegisterForm.getSurname(), userRegisterForm.getPassword());

        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public User edit(UserEditForm userEditForm) {

        Objects.requireNonNull(userEditForm, "user edit form must not be null!");

        User user = this.userRepository.findById(userEditForm.getUsername())
                .orElseThrow(UserNotFoundException::new);

        user.changeName(userEditForm.getName());
        user.changeSurname(userEditForm.getSurname());

        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public void delete(UserId username) {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

//    @Override
//    public List<Task> listTasksByStatus(UserId username, TaskStatus status) {
//
//        List<Task> tasksByUser = findTasksByUser(username)
//                .stream().distinct().collect(Collectors.toList());
//
//        return this.findTasksByStatus(status, tasksByUser).stream().distinct().collect(Collectors.toList());
//    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(UserId username) {
        return userRepository.findById(username).orElseThrow(UserNotFoundException::new);
    }

//    @Override
//    public MyUserTasksDto findTasksForTheUser(UserId username) {
//        List<Task> myToDoTasks;
//        List<Task> myInProgressTasks;
//        List<Task> myDoneTasks;
//        List<Task> myCanceledTasks;
//        List<Task> ownedTasks;
//
//        myToDoTasks = this.listTasksByStatus(username, TaskStatus.TODO);
//        myInProgressTasks = this.listTasksByStatus(username, TaskStatus.InProgress);
//        myDoneTasks = this.listTasksByStatus(username, TaskStatus.Done);
//        myCanceledTasks = this.listTasksByStatus(username, TaskStatus.Canceled);
//        ownedTasks = this.taskService.findByCreator(username);
//
//        List<Integer> numberOfTasks = this.findNumberOfTasks(username);
//
//        MyUserTasksDto myUserTasksDto = new MyUserTasksDto(myToDoTasks, myInProgressTasks,
//                myDoneTasks, myCanceledTasks, ownedTasks, numberOfTasks);
//
//        return myUserTasksDto;
//    }

//    @Override
//    public List<Integer> findNumberOfTasks(UserId username) {
//        User user = this.userRepository.findByUsername(username)
//                .orElseThrow(UserNotFoundException::new);
//
//        List<Integer> numberOfTasks = new ArrayList<>();
//
//        List<Task> allTasks = user.getTaskAssigned().stream().distinct().collect(Collectors.toList());
//
//        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks)
//                .stream().distinct().collect(Collectors.toList()).size();
//        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks)
//                .stream().distinct().collect(Collectors.toList()).size();
//        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks)
//                .stream().distinct().collect(Collectors.toList()).size();
//        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks)
//                .stream().distinct().collect(Collectors.toList()).size();
//
//        numberOfTasks.add(TODOTasks);
//        numberOfTasks.add(openTasks);
//        numberOfTasks.add(doneTasks);
//        numberOfTasks.add(canceledTasks);
//
//        return numberOfTasks;
//    }

//    private List<Task> findTasksByUser(UserId username){
//
//        List<Task> tasksByUser = new ArrayList<>();
//
//        for(Task task : this.taskRepository.findAll()){
//
//            if (task.getAssignees().stream().distinct().filter(assignee -> assignee.getUsername().equals(username))
//                    .collect(Collectors.toList()).size() > 0)
//                tasksByUser.add(task);
//        }
//
//        return tasksByUser.stream().distinct().collect(Collectors.toList());
//    }

    private List<Task> findTasksByStatus(TaskStatus status, List<Task> tasksByUser){

        List<Task> tasksByStatus = new ArrayList<>();

        for(Task task : tasksByUser){

            if(task.getStatus().equals(status))
                tasksByStatus.add(task);

        }

        return tasksByStatus.stream().distinct().collect(Collectors.toList());
    }

}
