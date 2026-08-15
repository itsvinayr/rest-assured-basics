package graphql;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class GraphQLTest {

    @Test
    public void getCountryDetails() {

        String payload = "{"
                + "\"query\":\"query { country(code: \\\"BR\\\") { name capital currency emoji } }\""
                + "}";

        given()
            .baseUri("https://countries.trevorblades.com")
            .header("Content-Type", "application/json")
            .body(payload)
        .when()
            .post()
        .then()
            .statusCode(200)
            .body("data.country.name", equalTo("Brazil"))
            .body("data.country.capital", equalTo("Brasília"))
            .body("data.country.currency", equalTo("BRL"))
            .body("data.country.emoji", equalTo("🇧🇷"));
    }
}