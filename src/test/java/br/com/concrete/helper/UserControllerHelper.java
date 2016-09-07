package br.com.concrete.helper;

import br.com.concrete.model.User;

import static io.restassured.RestAssured.given;

public class UserControllerHelper {

    public static User createUserExpecting201(User user) {
        return given().body(user).and().header("Content-type","application/json")
                .expect().statusCode(201)
                .when().post("/user")
                .andReturn().as(User.class);
    }
}
