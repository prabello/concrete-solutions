package br.com.concrete.helper;

import br.com.concrete.model.Login;
import br.com.concrete.model.User;

import static io.restassured.RestAssured.given;

public class LoginControllerHelper {
    public static User login(Login login) {
        return given().body(login).and().header("Content-type", "application/json")
                .expect().statusCode(200).when().post("/login").andReturn().as(User.class);
    }
}
