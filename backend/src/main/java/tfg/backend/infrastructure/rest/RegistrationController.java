package tfg.backend.infrastructure.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tfg.backend.application.services.RegistrationService;
import tfg.backend.domain.model.User;

@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationController(RegistrationService registrationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        log.info("Clave encriptada: {}", passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(registrationService.register(user), HttpStatus.CREATED);
    }

}
