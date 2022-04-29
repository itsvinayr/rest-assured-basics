package day2;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import payloads.PayLoads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonParse {
    @Test
    public void test1(){
        JsonPath jsonPath = new JsonPath(PayLoads.getProductPrice());
        // get courses size
        int count = jsonPath.getInt("courses.size()");
        System.out.println("course size : "+count);

        // print purchase amount
        String purchaseAmount = jsonPath.getString("dashboard.purchaseAmount");
        System.out.println("Purchase amount is "+purchaseAmount);

        //get first course title
        String title = jsonPath.getString("courses[0].title");
        System.out.println("title of course is : "+title);

        //print all courses and their respective titles
        for(int i=0; i<count; i++){
            System.out.println("title of course is : "+jsonPath.getString("courses["+i+"].title"));
        }
    }

    @Test
    public void parseJsonFile() throws IOException {
        System.out.println(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"//src/test//resources//complex.json"))));
    }
}
