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

public class PatchPostTest {

    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testPatchResource() {
        // 1. Prepare Request Payload with only the fields to be updated
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "foo");

        // 2. Send PATCH Request and Validate Response
        given()
            .header("Content-type", "application/json; charset=UTF-8")
            .contentType(ContentType.JSON)
            .pathParam("postId", 1) // Dynamically injects the ID into the URL
            .body(requestBody)
        .when()
            .patch("/posts/{postId}") // {postId} is replaced by 1
        .then()
            .log().all() // Prints response console log
            .statusCode(200) // Returns 200 OK for successful patch
            .body("id", equalTo(1))
            .body("title", equalTo("foo")) // Validates updated title
            .body("body", notNullValue())  // Remaining fields stay intact
            .body("userId", notNullValue());
    }
}

