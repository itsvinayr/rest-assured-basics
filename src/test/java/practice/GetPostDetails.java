package practice;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GetPostDetails {

    @Test
    public void test(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", notNullValue())
            .body("userId", equalTo(1))
            .body("body", notNullValue());
    }
    
}
