package day4;

import day3.serialize.pojo.AddPlace;
import day3.serialize.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SpecBuilders {

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

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                expectHeader("Server", "Apache/2.4.41 (Ubuntu)").build();

        RequestSpecification request = given().log().all().spec(requestSpecification).body(addPlace);

        String response = request.when().post("maps/api/place/add/json")
                .then().log().all()
                .assertThat().spec(responseSpecification)
                .body("scope", equalTo("APP"))
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        String place_id = jsonPath.getString("place_id");
        System.out.println("Place id is "+place_id);
    }

}
