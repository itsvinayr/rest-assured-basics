package practice;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NestedResourceTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetNestedComments() {
        given()
            .pathParam("postId", 1)
        .when()
            .get("/posts/{postId}/comments")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("$", hasSize(greaterThan(0)))
            .body("[0].postId", equalTo(1))
            .body("[0]", hasKey("id"))
            .body("[0]", hasKey("name"))
            .body("[0]", hasKey("email"))
            .body("[0]", hasKey("body"));
    }

    @Test
    public void testGetNestedCommentsAlternative() {
        Response response = given()
            .when()
                .get("/posts/1/comments")
            .then()
                .statusCode(200)
                .extract()
                .response();

        // Print response body to console
        response.prettyPrint();
    }
}

