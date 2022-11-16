package mk.ukim.finki.emt.usermanagement.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.exceptions.*;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import mk.ukim.finki.emt.usermanagement.domain.repository.UserRepository;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import mk.ukim.finki.emt.usermanagement.services.form.UserEditForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserLoginForm;
import mk.ukim.finki.emt.usermanagement.services.form.UserRegisterForm;
import mk.ukim.finki.emt.usermanagement.xport.client.CommentClient;
import mk.ukim.finki.emt.usermanagement.xport.client.TaskClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskClient taskClient;
    private final CommentClient commentClient;
    private User loggedUser;

    public UserServiceImpl(UserRepository userRepository, TaskClient taskClient, CommentClient commentClient){
        this.userRepository = userRepository;
        this.taskClient = taskClient;
        this.commentClient = commentClient;
        this.loggedUser = new User();
    }

    @Override
    public Optional<UserLoginReturnedDto> login(UserLoginForm userLoginForm) {

        Objects.requireNonNull(userLoginForm, "user login must not be null!");

        if(userLoginForm.getUsername() == null || userLoginForm.getPassword() == null )
            throw new InvalidArgumentsException();

        Optional<User> user = this.userRepository.
                findByUsernameAndPassword(userLoginForm.getUsername(), userLoginForm.getPassword());

        userRepository.saveAndFlush(user.get());

        this.setLoggedUser(user.get());

        boolean ownedTask = user.get().getTaskOwned() != null ? true : false;

        UserLoginReturnedDto userLoginReturnedDto = new UserLoginReturnedDto(user.get().getUsername(),
                user.get().getId().getId(), user.get().getRole().toString(), ownedTask);

//        return Optional.of(user.orElseThrow(InvalidUserCredentialsException::new));
        return Optional.of(userLoginReturnedDto);
    }

    private void setLoggedUser(User user) {
        loggedUser = user;
    }

    @Override
    public Optional<User> register(UserRegisterForm userRegisterForm) {

        Objects.requireNonNull(userRegisterForm, "user registration must not be null!");

        if(userRegisterForm.getUsername() == null || userRegisterForm.getPassword() == null)
            throw new InvalidArgumentsException();

        if(!userRegisterForm.getPassword().equals(userRegisterForm.getRepeatedPassword()))
            throw new PasswordsDoNotMatchException();

        if(this.userRepository.findByUsername(userRegisterForm.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(String.valueOf(userRegisterForm.getUsername()));

        User user = User.build(userRegisterForm.getUsername(), userRegisterForm.getName(),
                userRegisterForm.getSurname(), userRegisterForm.getPassword());

        userRepository.saveAndFlush(user);

        return Optional.of(user);
    }

    @Override
    public boolean logoutUser() {
        loggedUser = new User();
        return true;
    }

    @Override
    public User edit(UserEditForm userEditForm) {

        Objects.requireNonNull(userEditForm, "user edit form must not be null!");

        User user = this.userRepository.findByUsername(userEditForm.getUsername())
                .orElseThrow(UserNotFoundException::new);

        user.changeName(userEditForm.getName());
        user.changeSurname(userEditForm.getSurname());

        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public boolean delete(UserId userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if(!this.taskClient.deleteTaskAssigned(user.getTaskAssigned()))
            throw new CannotDeleteTaskAssigned(user.getId().getId(), user.getTaskAssigned().getId());

        if(!this.taskClient.deleteTaskOwned(user.getTaskOwned()))
            throw new CannotDeleteTaskOwned(user.getId().getId(), user.getTaskOwned().getId());

        if(!this.commentClient.deleteComments(userId))
            throw new CannotDeleteComments(user.getId().getId());

        userRepository.delete(user);

        return true;
    }

    @Override
    public List<Task> listTasksByStatus(String username, TaskStatus status, List<Task> tasks) {

        List<Task> tasksByUser = findTasksByUser(username, tasks)
                .stream().distinct().collect(Collectors.toList());

        return this.findTasksByStatus(status, tasksByUser).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<UserPerformanceDto> showUserPerformance(String chosenUsername, String chosenType,
                                                            String dateFrom, String dateTo,
                                                            List<Task> tasks) {

        //UserId userId = new UserId(chosenUsername);

        User user = this.findByUsername(chosenUsername);

        List<Integer> numberOfTasks;

        if(chosenType.equals("Weekly")){
            numberOfTasks = this.filterByDate(chosenUsername, dateFrom, dateTo, tasks);
        }else {
            numberOfTasks = this.findNumberOfTasksByType(chosenUsername, chosenType, tasks);
        }

        List<String> calculatedPerformanceAndRating = this.calculatePerformanceAndRating(numberOfTasks);

        String calculatedPerformance = String.format("%.2f",Double.valueOf(calculatedPerformanceAndRating.get(1)));

        String calculatedPerf = calculatedPerformance.replace(",",".");
        Double value = Double.valueOf(calculatedPerf);

        UserPerformanceDto userPerformanceDto = new UserPerformanceDto(user, numberOfTasks,
                calculatedPerformanceAndRating, value, chosenType);

        return Optional.of(userPerformanceDto);
    }

    @Override
    public List<Integer> filterByDate(String username, String dateFrom, String dateTo, List<Task> tasks) {
        User user = this.findByUsername(username);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = new ArrayList<>();

        if(user.getTaskAssigned() != null) {
            for (Task task : tasks) {

                String taskId = task.getId().getId();
                String taskAssignedId = user.getTaskAssigned().getId();

                if (taskId.equals(taskAssignedId)) {
                    allTasks.add(task);
                }
            }
        }

       // TaskId assignedTask = user.getTaskAssigned();

        //allTasks.add(assignedTask);

        LocalDate startDate = LocalDate.parse(dateFrom);
        LocalDate endDate = LocalDate.parse(dateTo);

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream().distinct()
                .filter(task -> (task.getStartDate().toLocalDate().isAfter(startDate)
                        && task.getStartDate().toLocalDate().isBefore(endDate))
                        || task.getStartDate().toLocalDate().isEqual(startDate)
                        || task.getStartDate().toLocalDate().isEqual(endDate)
                )
                .collect(Collectors.toList()).size();

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    @Override
    public List<Integer> findNumberOfTasksByType(String username, String type, List<Task> tasks) {
        User user = this.findByUsername(username);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = new ArrayList<>();

        if(user.getTaskAssigned() != null) {

            for (Task task : tasks) {

                String taskId = task.getId().getId();
                String taskAssignedId = user.getTaskAssigned().getId();
                String taskOwnedId = user.getTaskOwned().getId();

                if (taskId.equals(taskAssignedId)) {
                    allTasks.add(task);
                }else if(taskId.equals(taskOwnedId) && !allTasks.contains(task)){
                    allTasks.add(task);
                }
            }
        }

        //TaskId assignedTask = user.getTaskAssigned();

        //List<TaskId> allTasks = new ArrayList<>();

        //allTasks.add(assignedTask);

        Integer TODOTasks = 0;
        Integer openTasks = 0;
        Integer doneTasks = 0;
        Integer canceledTasks = 0;

        LocalDateTime timeNow = LocalDateTime.now();

        if(type.equals("Monthly")){

            TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

            canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks).stream().distinct()
                    .filter(task -> task.getStartDate().getMonth().equals(timeNow.getMonth()))
                    .collect(Collectors.toList()).size();

        } else if(type.equals("Yearly")){

            TODOTasks = filterByYear(this.findTasksByStatus(TaskStatus.TODO, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            openTasks = filterByYear(this.findTasksByStatus(TaskStatus.InProgress, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            doneTasks = filterByYear(this.findTasksByStatus(TaskStatus.Done, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());

            canceledTasks = filterByYear(this.findTasksByStatus(TaskStatus.Canceled, allTasks)
                    .stream().distinct().collect(Collectors.toList()), timeNow.getYear());
        }

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    @Override
    public List<String> calculatePerformanceAndRating(List<Integer> numberOfTasks) {
        Double calculatedPerformance;

        Integer numberOfTasksAssigned = numberOfTasks.get(0)+numberOfTasks.get(1)
                +numberOfTasks.get(2)+numberOfTasks.get(3);

        Integer numberOfDoneTasks = numberOfTasks.get(2);

        calculatedPerformance = Double.valueOf(numberOfDoneTasks)/Double.valueOf(numberOfTasksAssigned);
        calculatedPerformance*=100;

        String rate;

        if(calculatedPerformance<50)
            rate = "Underrated";
        else if(calculatedPerformance > 50)
            rate = "Overrated";
        else if(calculatedPerformance.equals(50.0))
            rate = "Fine";
        else rate = "Do not have assigned tasks to see performance";

        List<String> calculatedPerformanceAndRating = new ArrayList<>();
        calculatedPerformanceAndRating.add(rate);
        calculatedPerformanceAndRating.add(calculatedPerformance.toString());

        return calculatedPerformanceAndRating;
    }

    @Override
    public List<UsersDto> showUsersDto() {
        List<UsersDto> usersDtos = new ArrayList<>();

        for(User user:this.userRepository.findAll()){
            usersDtos.add(new UsersDto(user.getName(), user.getSurname(),
                    user.getUsername(), user.getRole().toString()));
        }

        return usersDtos;
    }

    @Override
    public List<UsersDto> getNotAssignedUsers() {
        List<UsersDto> usersDtos = new ArrayList<>();

        for(User user:this.userRepository.findAll()){

            if(user.getTaskAssigned() == null)
                usersDtos.add(new UsersDto(user.getName(), user.getSurname(),
                        user.getUsername(), user.getRole().toString()));
        }

        return usersDtos;
    }

    @Override
    public User findById(UserId userId) {
        return this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean setTaskAssigned(TaskId taskId, UserId userId) {

        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.changeTaskAssigned(taskId);

        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public boolean setTaskOwned(TaskId taskId, UserId userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.changeTaskOwned(taskId);

        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public boolean deleteTaskAssigned(UserId userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.changeTaskAssigned(null);

        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public boolean deleteTaskOwned(UserId userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.changeTaskOwned(null);

        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public Optional<UserLoginReturnedDto> getLoggedUser() {

        boolean ownedTask = loggedUser.getTaskOwned() != null? true : false;

        UserLoginReturnedDto userLoginReturnedDto = new UserLoginReturnedDto(loggedUser.getUsername(),
                loggedUser.getId().getId(), loggedUser.getRole().toString(), ownedTask);

        return Optional.of(userLoginReturnedDto);
    }

    @Override
    public User changeRole(String username, String role) {
        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Role newRole = Role.valueOf(role);

        user.changeRole(newRole);

        this.userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public MyUserTasksDto findTasksForTheUser(String username, List<Task> tasks) {
        List<Task> myToDoTasks;
        List<Task> myInProgressTasks;
        List<Task> myDoneTasks;
        List<Task> myCanceledTasks;
        List<Task> ownedTasks = new ArrayList<>();

        myToDoTasks = this.listTasksByStatus(username, TaskStatus.TODO, tasks);
        myInProgressTasks = this.listTasksByStatus(username, TaskStatus.InProgress, tasks);
        myDoneTasks = this.listTasksByStatus(username, TaskStatus.Done, tasks);
        myCanceledTasks = this.listTasksByStatus(username, TaskStatus.Canceled, tasks);

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        if(user.getTaskOwned() != null) {

            for (Task task : tasks) {

                String taskId = task.getId().getId();
                String taskOwned = user.getTaskOwned().getId();

                if (taskId.equals(taskOwned)) {
                    ownedTasks.add(task);
                }
            }
        }

        //TaskId ownedTask = user.getTaskOwned();

        //ownedTasks = this.taskService.findByCreator(username);

        //ownedTasks.add(ownedTask);

        List<Integer> numberOfTasks = this.findNumberOfTasks(username, tasks);

        UserLoginReturnedDto activeUser = this.getLoggedUser().get();

        MyUserTasksDto myUserTasksDto = new MyUserTasksDto(myToDoTasks, myInProgressTasks,
                myDoneTasks, myCanceledTasks, ownedTasks, numberOfTasks, activeUser.getUsername(),
                activeUser.getRole());

        return myUserTasksDto;
    }

    @Override
    public List<Integer> findNumberOfTasks(String username, List<Task> tasks) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        List<Integer> numberOfTasks = new ArrayList<>();

        List<Task> allTasks = new ArrayList<>();

        if(user.getTaskAssigned() != null) {
            for (Task task : tasks) {
                String taskId = task.getId().getId();
                String taskAssignedId = user.getTaskAssigned().getId();

                if (taskId.equals(taskAssignedId)) {
                    allTasks.add(task);
                }
            }

        }

        if(user.getTaskOwned() != null) {
            for (Task task : tasks) {
                String taskId = task.getId().getId();
                String taskOwnedId = user.getTaskOwned().getId();

                if (taskId.equals(taskOwnedId)) {
                    allTasks.add(task);
                }
            }

        }
        //TaskId assignedTask = user.getTaskAssigned();

        //allTasks.add(assignedTask);

        Integer TODOTasks = this.findTasksByStatus(TaskStatus.TODO, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer openTasks = this.findTasksByStatus(TaskStatus.InProgress, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer doneTasks = this.findTasksByStatus(TaskStatus.Done, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();
        Integer canceledTasks = this.findTasksByStatus(TaskStatus.Canceled, allTasks)
                .stream().distinct().collect(Collectors.toList()).size();

        numberOfTasks.add(TODOTasks);
        numberOfTasks.add(openTasks);
        numberOfTasks.add(doneTasks);
        numberOfTasks.add(canceledTasks);

        return numberOfTasks;
    }

    private List<Task> findTasksByUser(String username, List<Task> tasks){

        List<Task> tasksByUser = new ArrayList<>();

//        for(Task task : this.taskRepository.findAll()){
//
//            if (task.getAssignees().stream().distinct().filter(assignee -> assignee.getUsername().equals(username))
//                    .collect(Collectors.toList()).size() > 0)
//                tasksByUser.add(task);
//        }
//
//        return tasksByUser.stream().distinct().collect(Collectors.toList());

        User user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        //TaskId taskAssigned = user.getTaskAssigned();

        //tasksByUser.add(taskAssigned);

        if(user.getTaskAssigned() != null) {

            for (Task task : tasks) {
                String taskId = task.getId().getId();
                String taskAssignedId = user.getTaskAssigned().getId();

                if (taskId.equals(taskAssignedId)) {
                    tasksByUser.add(task);
                }
            }
        }

        return tasksByUser;
    }

    private List<Task> findTasksByStatus(TaskStatus status, List<Task> tasksByUser){

        List<Task> tasksByStatus = new ArrayList<>();

        for(Task task : tasksByUser){

            if(task.getStatus().equals(status))
                tasksByStatus.add(task);

        }

        return tasksByStatus.stream().distinct().collect(Collectors.toList());
    }

    private Integer filterByYear(List<Task> tasks, int year){

        Integer number = 0;

        for(Task task : tasks){

            if(task.getStartDate().getYear() == year)
                number++;
        }

        return number;
    }

}
