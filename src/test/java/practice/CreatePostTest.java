package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePostTest {

    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testCreateResource() {
        // 1. Prepare Request Payload using a Map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        // 2. Send POST Request and Validate Response Inline
        given()
            .header("Content-type", "application/json; charset=UTF-8")
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .log().all() // Prints response console log
            .statusCode(201) // JSONPlaceholder returns 201 for successful creation
            .body("id", notNullValue())
            .body("title", equalTo("foo"))
            .body("body", equalTo("bar"))
            .body("userId", equalTo(1));
    }
}
