package br.com.concrete.controller;

import br.com.concrete.builder.UserBuilder;
import br.com.concrete.helper.LoginControllerHelper;
import br.com.concrete.helper.UserControllerHelper;
import br.com.concrete.model.Login;
import br.com.concrete.model.Message;
import br.com.concrete.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    private User user;
    private User userToFail;
    private User userToGetDetails;

    @Before
    public void setUp(){
        user = new UserBuilder().build();
        userToFail = new UserBuilder().email("fail@fail.com").build();
        userToGetDetails = new UserBuilder().email("user@email.com").password("banana").build();
    }

    @Test
    public void ensureThatCanCreateNewUser(){
        User returnUser = UserControllerHelper.createUserExpecting201(this.user);
        assertNotNull(returnUser.getCreated());
        assertNotNull(returnUser.getToken());
    }



    @Test
    public void ensureThatCantCreateUserWithSameEmail(){
        UserControllerHelper.createUserExpecting201(this.userToFail);

        Message returnMessage = given().body(this.userToFail).and().header("Content-type", "application/json")
                .expect().statusCode(409)
                .when().post("/user").andReturn().as(Message.class);

        assertTrue("E-mail já existente".equals(returnMessage.toString()));
    }

    @Test
    public void searchUser(){
        User createdUser = UserControllerHelper.createUserExpecting201(this.userToGetDetails);
        User loggedInUser = LoginControllerHelper.login(new Login(createdUser.getEmail(), "banana"));

        User userFromProfile = given().basePath("user")
                .and().header("Content-type", "application/json")
                .and().header("token",loggedInUser.getToken())
                .expect().statusCode(200)
                .when().get(loggedInUser.getUuid()).andReturn().as(User.class);

        assertEquals(loggedInUser.getEmail(),userFromProfile.getEmail());
        assertEquals(loggedInUser.getCreated(),userFromProfile.getCreated());
    }

}