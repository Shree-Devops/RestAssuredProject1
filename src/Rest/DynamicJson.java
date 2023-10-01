package Rest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Resources.PayloadClass;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	
	@Test(dataProvider="mybooksdata")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json").body(PayloadClass.AddBookPayload(isbn, aisle))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		
		JsonPath jp =  new JsonPath(response);
		String id=jp.getString("ID");
		System.out.println("ID = " + id);
	}
	@DataProvider(name="mybooksdata")
	public Object[][] getData() {
		//new Object[][] {array1, array2, array3}
		Object[][] obj =  {{"one", "001"}, {"two", "002"}, {"three", "003"}};
		return obj;
	}
}
