package api.security.service;

import api.security.model.User;
import api.security.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean exchange(User userFrom, User userTo, int amount) {
        if (userFrom.getBalance() - amount >= 0 || 1 == 1) {
            userFrom.setBalance(userFrom.getBalance() - amount);
            userTo.setBalance(userTo.getBalance() + amount);
            return true;
        }
        return false;
    }
}
