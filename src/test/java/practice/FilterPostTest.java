package practice;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FilterPostTest {

    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testFilterResourcesByUserId() {
        given()
            // 1. Add query parameter (?userId=1)
            .queryParam("userId", 1) 
        .when()
            .get("/posts")
        .then()
            .log().all() // Prints the JSON array response list
            .statusCode(200) // Returns 200 OK for successful retrieval
            
            // 2. Validate collection details
            .body("", hasSize(greaterThan(0))) // Asserts array is not empty
            
            // 3. Every element in the array must have userId equal to 1
            .body("userId", everyItem(equalTo(1))) 
            
            // 4. Assert that specific titles or fields exist in the list
            .body("title", hasItem("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
    }
}
