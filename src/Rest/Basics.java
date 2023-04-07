package Rest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;

public class Basics {

	public static void main(String[] args) {
		
		// Given - all input details
		// When - Submit the request - Resources, http method
		// Then - Validate the response
		
		//AddPlace, UpdatePlace with new address, GetPlace to validate new place
		
		// Add place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qa123").header("Content-Type", "application/json").body("{\r\n" + 
				"  \"location\": {\r\n" + 
				"    \"lat\": -38.383494,\r\n" + 
				"    \"lng\": 33.427362\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Frontline house\",\r\n" + 
				"  \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
				"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoe park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"https://rahulshettyacademy.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}\r\n" + 
				"").when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200)//.body("scope", equalTo("APP"))//validating value of scope varible inside body
		.header("Server", "Apache/2.4.18 (Ubuntu)")// validating that response is coming from correct server
		.extract().response().asString();
		
		System.out.println("Response after adding the Address " + response);
		JsonPath js = new JsonPath(response);//for passing Json -> it will convert it into Json
		String placeId = js.getString("place_id");//it will fetch the placeid value in response js Json
		System.out.println("Place id for added address - " + placeId);
		
		// Update place
		
		String newAddress = "70 Summer walk, USA";
		
		given().log().all().queryParam("key", "qa123").header("Content-Type", "application/json").body("{\r\n" + 
				"\"place_id\":\"" + placeId + "\",\r\n" + 
				"\"address\":\"" + newAddress + "\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// GetPlace
		
		String getResponse = given().log().all().queryParam("key", "qa123").queryParam("place_id", placeId)
		.when().get("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp = new JsonPath(getResponse);
		String currentAddress = jp.get("address");
		System.out.println(currentAddress);
		Assert.assertEquals(currentAddress, newAddress);
		
		// print title of the first course
		String title = js.get("cources[0].title");
		int count = js.getInt("cources.size()");
		System.out.println(title);
		
		//print All course title and their respective prices
		
		for (int i=0;i<count;i++) {
			System.out.println("Title = " + js.get("cources[" + i + "].title"));
			System.out.println("Price = " + js.get("cources[" + i + "].price"));
		}
		
		//print copies of course where title = RPA
		
		for (int i=0; i<count;i++) {
			if (js.get("cources[" + i + "].title") == "RPA") {
				System.out.println("Copies = " + js.get("cources[" + i + "].copies"));
				break;
			}
		}
		
		//verify if sum of all course prices matches with purchase amount
		int sum = 0;
		for (int i=0; i<count; i++) {
			int price = js.get("courses[" + i + "].price");
			int copies = js.get("courses[" + i + "].copies");
			sum = sum+ (price * copies);
		}
		if (js.get("dashboard.purchaseAmount").equals(sum)) 
			System.out.println("matching");
		else 
			System.out.println("matching");		
	}
}