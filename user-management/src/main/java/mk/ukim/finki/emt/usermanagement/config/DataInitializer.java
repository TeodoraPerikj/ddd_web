package mk.ukim.finki.emt.usermanagement.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

//@Component
//@AllArgsConstructor
//public class DataInitializer {
//
//    private final UserRepository userRepository;
//
//    @PostConstruct
//    public void initData() {
//        User u1 = User.build("User 2", "User 2", "User 2");
//        User u2 = User.build("User 3", "User 3", "User 3");
//        if (userRepository.findAll().isEmpty()) {
//            userRepository.saveAll(Arrays.asList(u1, u2));
//        }
//    }
//
//}
