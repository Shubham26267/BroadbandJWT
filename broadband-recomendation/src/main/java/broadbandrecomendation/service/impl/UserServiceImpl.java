package broadbandrecomendation.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import broadbandrecomendation.exception.InvalidRequestException;
import broadbandrecomendation.model.Role;
import broadbandrecomendation.model.User;
import broadbandrecomendation.repository.UserRepository;
import broadbandrecomendation.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidRequestException("Username already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidRequestException("Email already registered");
        }

        
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        
        if (user.getRole() == null)
            user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}