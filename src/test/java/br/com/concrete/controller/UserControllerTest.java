package br.com.concrete.controller;

import br.com.concrete.model.Message;
import br.com.concrete.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    private User user;
    private User userToFail;

    @Before
    public void setUp(){
        user = new UserBuilder().build();
        userToFail = new UserBuilder().email("fail@fail.com").build();
    }

    @Test
    public void ensureThatCanCreateNewUser(){
        User returnUser = given().body(this.user).and().header("Content-type","application/json")
                .expect().statusCode(201)
                .when().post("/user")
                .andReturn().as(User.class);
        assertNotNull(returnUser.getCreated());
        assertNotNull(returnUser.getToken());
    }

    @Test
    public void ensureThatCantCreateUserWithSameEmail(){
        given().body(this.userToFail).and().header("Content-type","application/json")
                .expect().statusCode(201)
                .when().post("/user");

        Message returnMessage = given().body(this.userToFail).and().header("Content-type", "application/json")
                .expect().statusCode(409)
                .when().post("/user").andReturn().as(Message.class);

        assertTrue("E-mail já existente".equals(returnMessage.toString()));
    }

    @Test
    public void searchUser(){

    }

}