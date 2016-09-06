package br.com.concrete.controller;

import br.com.concrete.model.Message;
import br.com.concrete.model.User;
import br.com.concrete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            boolean passwordMatch = encoder.matches(user.getPassword(), foundUser.get().getPassword());
            if (passwordMatch) {
                User userToLogIn = foundUser.get();
                userToLogIn.setLastLogin(Calendar.getInstance());

                userToLogIn = userRepository.save(userToLogIn);

                return new ResponseEntity<User>(userToLogIn, HttpStatus.OK);
            }
            return new ResponseEntity<Message>(new Message("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Message>(new Message("Usuário e/ou senha inválidos"), HttpStatus.NOT_FOUND);
    }
}
