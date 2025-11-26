package broadbandrecomendation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import broadbandrecomendation.model.Role;
import broadbandrecomendation.model.User;
import broadbandrecomendation.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✔ MATCHING THE PROPERTY NAMES YOU USED
    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        // 1) Fixed ADMIN credentials
        if (identifier.equals(adminUsername)) {

            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));

        
            String encodedPass = passwordEncoder.encode(adminPassword);

            return new org.springframework.security.core.userdetails.User(
                    adminUsername,
                    encodedPass,
                    authorities
            );
        }

        // 2) DB lookup by username
        Optional<User> userOpt = userRepository.findByUsername(identifier);

        // 3) If not found ->s lookup by email
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByEmail(identifier);
        }

        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("User not found: " + identifier);
        }

        User user = userOpt.get();

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}