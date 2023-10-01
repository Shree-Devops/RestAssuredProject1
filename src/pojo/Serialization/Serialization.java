package pojo.Serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;

public class Serialization {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		AddNewPlace a = new AddNewPlace();
		a.setAccuracy(50);
		a.setName("Frontline house");
		a.setPhone_number("(+91) 983 893 3937");
		a.setAddress("29, side layout, cohen 09");
		a.setWebsite("http://google.com");
		a.setLanguage("French-IN");
		
		Location loc =  new Location();
		loc.getLat();
		loc.getLng();
		a.setLocation(loc);
		
		ArrayList<String> typesList = new ArrayList<String>();
		typesList.add("shoe park");
		typesList.add("shop");
		a.setTypes(typesList);
		
		Response response = given().log().all().queryParam("key", "qaclick123")
		.body(a)   // sending object of Class as body
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.print("responseString" + responseString);
	}

}
