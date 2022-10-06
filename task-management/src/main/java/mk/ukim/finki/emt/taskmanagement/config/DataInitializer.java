package mk.ukim.finki.emt.taskmanagement.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import mk.ukim.finki.emt.taskmanagement.domain.repository.TaskRepository;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserId;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
//
//@Component
//@AllArgsConstructor
//public class DataInitializer {
//
//    private final TaskRepository taskRepository;
//
//    @PostConstruct
//    public void initData() {
//        Task t1 = Task.build("Task Title 1", "Task Description 1",
//                LocalDateTime.now(), LocalDateTime.of(2022, Month.DECEMBER, 28, 10, 20),
//                1, UserId.randomId(UserId.class), UserId.randomId(UserId.class));
//                //new UserId("user1"), new UserId("user1"));
//        Task t2 = Task.build("Task Title 2", "Task Description 2",
//                LocalDateTime.now(), LocalDateTime.of(2022, Month.DECEMBER, 29, 10, 20),
//                3, UserId.randomId(UserId.class), UserId.randomId(UserId.class));
//                //new UserId("user1"), new UserId("user1"));
//        if (taskRepository.findAll().isEmpty()) {
//            taskRepository.saveAll(Arrays.asList(t1, t2));
//        }
//    }
//
//}
