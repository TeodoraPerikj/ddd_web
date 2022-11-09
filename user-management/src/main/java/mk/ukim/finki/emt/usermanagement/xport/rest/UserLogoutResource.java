package mk.ukim.finki.emt.usermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.usermanagement.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/logout")
@AllArgsConstructor
public class UserLogoutResource {

    private final UserService userService;

    @GetMapping
    public boolean logout() {
        return this.userService.logoutUser();
    }

}