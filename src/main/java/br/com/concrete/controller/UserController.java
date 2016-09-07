package br.com.concrete.controller;

import br.com.concrete.model.Message;
import br.com.concrete.model.User;
import br.com.concrete.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody User user){
        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());
        if(userWithSameEmail.isPresent()){
            return new ResponseEntity<Message>(new Message("E-mail já existente"),HttpStatus.CONFLICT);
        }
        user = userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> searchUser(@PathVariable String uuid,@RequestHeader("token") String token){
        if(token == null){
            return new ResponseEntity<Message>(new Message("Não autorizado"),HttpStatus.UNAUTHORIZED);
        }
        Optional<User> userFound = userRepository.findByUuid(uuid);
        if(userFound.isPresent()){
            boolean tokenIsValid = userFound.get().getToken().equals(token);
            boolean isOnTime = userFound.get().isLoggedIn();
            if(tokenIsValid && isOnTime){
                return new ResponseEntity<User>(userFound.get(), HttpStatus.OK);
            }
            return new ResponseEntity<Message>(new Message("Sessão inválida"),HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Message>(new Message("Usuário não encontrado"), HttpStatus.NOT_FOUND);
    }
}
