package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdatePostTest {

    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testUpdateResource() {
        // 1. Prepare Request Payload using a Map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 1);
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        // 2. Send PUT Request and Validate Response
        given()
            .header("Content-type", "application/json; charset=UTF-8")
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .put("/posts/1") // Appending the resource ID path parameter
        .then()
            .log().all() // Prints response console log
            .statusCode(200) // JSONPlaceholder returns 200 OK for updates
            .body("id", equalTo(1))
            .body("title", equalTo("foo"))
            .body("body", equalTo("bar"))
            .body("userId", equalTo(1));
    }
}

