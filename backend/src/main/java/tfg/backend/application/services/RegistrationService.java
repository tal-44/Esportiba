package tfg.backend.application.services;

import tfg.backend.domain.model.User;
import tfg.backend.domain.port.IUserRepository;

public class RegistrationService {

    private final IUserRepository iUserRepository;

    public RegistrationService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User register(User user) {
        // Here you can add logic for registration, like validation, password hashing, etc.
        return iUserRepository.save(user);
    }
}
