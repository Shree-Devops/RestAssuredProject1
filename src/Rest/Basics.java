package Rest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

public class Basics {

	public static void main(String[] args) throws IOException {
		
		// Given - all input details
		// When - Submit the request - Resources, http method
		// Then - Validate the response
		// Convert content of the file to String = Content of file into Byte -> Byte data into String
		
		//AddPlace, UpdatePlace with new address, GetPlace to validate new place
		
		// Add place *********************************************************************************************************************
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qa123").header("Content-Type", "application/json")
//				.body("{\r\n" + 
//				"  \"location\": {\r\n" + 
//				"    \"lat\": -38.383494,\r\n" + 
//				"    \"lng\": 33.427362\r\n" + 
//				"  },\r\n" + 
//				"  \"accuracy\": 50,\r\n" + 
//				"  \"name\": \"Frontline house\",\r\n" + 
//				"  \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
//				"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
//				"  \"types\": [\r\n" + 
//				"    \"shoe park\",\r\n" + 
//				"    \"shop\"\r\n" + 
//				"  ],\r\n" + 
//				"  \"website\": \"https://rahulshettyacademy.com\",\r\n" + 
//				"  \"language\": \"French-IN\"\r\n" + 
//				"}\r\n" + 
//				"")
				.body(new String (Files.readAllBytes(Paths.get("C:\\Shree\\ShreeWork\\Projects\\RestAssuredProject1\\src\\Resources\\AddPlacePayload.json"))))
				.when().post("maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200)//.body("scope", equalTo("APP"))//validating value of scope variable inside body
				.header("Server", "Apache/2.4.52 (Ubuntu)")// validating that response is coming from correct server
				.extract().response().asString();
		
		System.out.println("Response after adding the Address " + response);
		JsonPath jadd = new JsonPath(response);//for passing Json -> it will convert it into Json
		String placeId = jadd.getString("place_id");//it will fetch the placeid value in response js Json
		System.out.println("Place id for added address - " + placeId);
		
		// Delete  *********************************************************************************************************************
//		given().log().all().queryParam("key", "qaclick123").body("{\r\n"
//				+ "\"place_id\": \"" + placeId + "\"\r\n"
//				+ "}")
//		.when().delete("maps/api/place/delete/json")
//		.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"));
//		
//		// Get place *********************************************************************************************************************
//		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
//				.when().get("maps/api/place/get/json")
//				.then().log().all().assertThat().statusCode(404).body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists"))
//				.extract().response().asString();
		
		// Update place *********************************************************************************************************************
		String newAddress = "70 Summer walk, USA";
		
		given().log().all().queryParam("key", "qa123").header("Content-Type", "application/json").body("{\r\n" + 
				"\"place_id\":\"" + placeId + "\",\r\n" + 
				"\"address\":\"" + newAddress + "\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// GetPlace *********************************************************************************************************************
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp = new JsonPath(getResponse);
		System.out.println("Get API Response in JSON format - " + jp);
		String currentAddress = jp.get("address");
		System.out.println("currentAddress from get API - " + currentAddress);
		Assert.assertEquals(currentAddress, newAddress);
		
	}
}