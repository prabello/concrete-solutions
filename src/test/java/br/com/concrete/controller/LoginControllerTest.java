package br.com.concrete.controller;

import br.com.concrete.model.User;
import br.com.concrete.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class LoginControllerTest {

    private LoginController loginController;

    private User user;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userRepository = spy(UserRepository.class);
        loginController = new LoginController(userRepository);
        user = new UserBuilder().build();
        doReturn(Optional.of(new UserBuilder().build())).when(this.userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void login(){
        ResponseEntity<?> responseEntity = loginController.login(user);
        System.out.println(responseEntity.toString());
    }

}