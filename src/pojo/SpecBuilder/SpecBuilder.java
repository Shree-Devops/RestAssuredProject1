package pojo.SpecBuilder;

import pojo.Serialization.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;

public class SpecBuilder {
	
	public static void main(String[] args) {
		
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
		
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		RequestSpecification req = given().spec(reqSpec).log().all().body(a);
		
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
				
		Response response = req.when().post("/maps/api/place/add/json")
		.then().spec(responseSpec).log().all().extract().response();
		
		String responseString = response.asString();
		System.out.print("responseString" + responseString);
	}

}
