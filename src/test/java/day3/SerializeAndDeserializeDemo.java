package day3;

import day3.deserialize.pojo.GetPlace;
import day3.serialize.pojo.AddPlace;
import day3.serialize.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import payloads.PayLoads;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SerializeAndDeserializeDemo {

    private String place_id;

    @Test
    public void testSerialize(){
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy("50");
        addPlace.setName("vinay");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("vinay, side layout, cohen 09");
        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        addPlace.setTypes(types);
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");
        Location location = new Location();
        location.setLat("-38.383494");
        location.setLng("33.427362");
        addPlace.setLocation(location);
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(addPlace).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        place_id = jsonPath.getString("place_id");
    }

    @Test(dependsOnMethods = "testSerialize")
    public void testDeserialize(){
        RestAssured.baseURI=RestAssured.baseURI = "https://rahulshettyacademy.com";
        GetPlace getPlace = given().log().all().queryParam("key", "qaclick123").
                queryParam("place_id", place_id).expect().defaultParser(Parser.JSON)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200)
                .extract().as(GetPlace.class);

        System.out.println(getPlace.getAccuracy());
    }

}
