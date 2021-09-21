package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.persistence.UserRepository;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User toSave) {
        toSave.setPassword(encoder.encode(toSave.getPassword()));
        return userRepository.save(toSave);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return user.map(UserDetailsImpl::new).get();
    }
}
