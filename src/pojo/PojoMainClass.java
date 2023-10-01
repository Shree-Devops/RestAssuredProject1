package pojo;

import static io.restassured.RestAssured.given;
import java.util.List;
import org.testng.Assert;
import io.restassured.parsing.Parser;
import java.util.ArrayList;
import java.util.Arrays;

public class PojoMainClass {

	public static void main(String[] args) throws InterruptedException {

		// reference - C:\Shree\Projects\RestAssuredProject1\src\Resources\GetCoursesJsonBody.property
		
		GetCourses gc = given().log().all().expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);

		gc.getLinkedIn();
		gc.getCourses().getApi().get(0).getPrice();

		List<Api> api = gc.getCourses().getApi();
		for (int i = 0; i < api.size(); i++) {
			if (api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				api.get(i).getPrice();
			}
		}
		
		// Get the course names present in WebAutomation and store in an Array
		
		String [] expectedTitlesArray = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		List<String> expectedTitlesArrayList = Arrays.asList(expectedTitlesArray);
		
		ArrayList<String> addCourseTitles = new ArrayList<String>();
		System.out.println("courseTitles before added in new array- " + addCourseTitles);
		
		List<WebAutomation> webAutomation = gc.getCourses().getWebAutomation();
		
		for (int i=0; i<webAutomation.size(); i++) {
			addCourseTitles.add(webAutomation.get(i).getCourseTitle());
		}
		System.out.println("courseTitles after added in new array- " + addCourseTitles);
		Assert.assertTrue(addCourseTitles.equals(expectedTitlesArrayList));
		
		
	}
}
