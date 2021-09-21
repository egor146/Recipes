package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.User;
import recipes.business.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            user.setRoles("ROLE_USER");
            user.setActive(true);
            userService.save(user);
        }
    }
}
