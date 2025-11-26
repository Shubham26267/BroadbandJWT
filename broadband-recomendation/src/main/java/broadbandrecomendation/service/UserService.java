package broadbandrecomendation.service;

import java.util.Optional;

import broadbandrecomendation.model.User;

public interface UserService {
	 User registerUser(User user);
	 Optional<User> findByUsername(String username);

}
