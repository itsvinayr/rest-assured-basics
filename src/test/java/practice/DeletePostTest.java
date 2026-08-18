package practice;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class DeletePostTest {

    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testDeleteResource() {
        // Send DELETE Request and Validate Response
        given()
            .pathParam("postId", 1) // Dynamically sets the ID to delete
        .when()
            .delete("/posts/{postId}")
        .then()
            .log().all() // Prints response console log
            .statusCode(200) // JSONPlaceholder returns 200 OK for deletes
            // JSONPlaceholder returns an empty object {} which equates to an empty string size or structure validation
            .body(equalTo("{}")); 
    }
}

