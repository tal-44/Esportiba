package tfg.backend.application.services;

import tfg.backend.domain.model.User;
import tfg.backend.domain.port.IUserRepository;

public class UserService {
    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }
    public User save(User user) {
        return this.iUserRepository.save(user);
    }
    public User findByEmail(String email) {
        return this.iUserRepository.findByEmail(email);
    }
    public User findById(Integer id) {
        return this.iUserRepository.findById(id);
    }
}
