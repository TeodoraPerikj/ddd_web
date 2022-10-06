package mk.ukim.finki.emt.usermanagement.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.repository.UserRepository;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskId;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
//
//@Component
//@AllArgsConstructor
//public class DataInitializer {
//
//    private final UserRepository userRepository;
//
//    @PostConstruct
//    public void initData() {
//        User u1 = User.createUserWithTasks("user1", "User Name 1", "User Surname 1", "user1",
//                TaskId.randomId(TaskId.class), TaskId.randomId(TaskId.class));
//        User u2 = User.createUserWithTasks("user2","User Name 2", "User Surname 2", "user2",
//                TaskId.randomId(TaskId.class), TaskId.randomId(TaskId.class));
//        if (userRepository.findAll().isEmpty()) {
//            userRepository.saveAll(Arrays.asList(u1, u2));
//        }
//    }
//
//}
