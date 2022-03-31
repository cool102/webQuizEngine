package engine.businesslayer;

import engine.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUserToDb(User user) {
        userRepository.save(user);
    }

    public List<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
