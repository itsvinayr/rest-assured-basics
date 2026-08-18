package practice;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class GetAllPosts {

    @Test
    public void getAllPosts() {
        // Set the Base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/posts") // Hits the collection endpoint
        .then()
            .statusCode(200) 
            .log().body()    // Prints the entire array to the console
            
            // Validations for the JSON Array
            .body("$", hasSize(100))                // Verifies total items = 100
            .body("id", hasItems(1, 2, 3, 100))     // Verifies specific IDs exist
            .body("title", everyItem(notNullValue())) // Ensures every item has a title
            .body("userId", everyItem(notNullValue())); // Ensures every item has a userId
    }
}

