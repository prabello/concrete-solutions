package br.com.concrete.controller;

import br.com.concrete.builder.UserBuilder;
import br.com.concrete.helper.LoginControllerHelper;
import br.com.concrete.helper.UserControllerHelper;
import br.com.concrete.model.Login;
import br.com.concrete.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class LoginControllerTest {

    private User user;
    private Login login;

    @Before
    public void setUp() {
        login = new Login("joao@silva.org","hunter2");
        user = new UserBuilder().email("joao@silva.org").password("hunter2").build();
    }

    @Test
    public void ensureThatRegisteredUserCanLogin() {
        UserControllerHelper.createUserExpecting201(this.user);

        User foundUser = LoginControllerHelper.login(this.login);

        assertNotNull(foundUser.getCreated());
        assertNotNull(foundUser.getToken());
    }

}