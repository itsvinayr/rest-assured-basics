package day1;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.PayLoads;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GoogleAPI {
    /**
     * Add place --> update place --> get place --> delete place
     */
    @Test
    public void teste2eflow(){
        // add place
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(PayLoads.getAddPlacePayload()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

        // get place
        JsonPath jsonPath = new JsonPath(response);
        String place = jsonPath.getString("place_id");

        //update place
        String newAddress = "Santome, Africa";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+place+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        // get place and validate address
        String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        jsonPath = new JsonPath(getResponse);
        String currentAddress = jsonPath.getString("address");
        Assert.assertTrue(currentAddress.equalsIgnoreCase(newAddress), "Address mismatch");

    }
}
