package tfg.backend.infrastructure.rest;

import tfg.backend.application.services.UserService;
import tfg.backend.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
// http://localhost:8085
@RequestMapping("/api/v1/users")
// http://localhost:8085/api/v1/users
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    // http://localhost:8085/api/v1/users/4
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userService.findById(id);
    }

}
