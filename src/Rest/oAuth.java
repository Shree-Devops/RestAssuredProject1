package Rest;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import java.util.List;
import pojo.GetCourses;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
public class oAuth {
	
	public static void main(String[] args) throws InterruptedException {
		// Commented below lines of code as Google is no more allowing login through automation scripts.
/**
		System.setProperty("webdriver.chrome.driver", "C:\Shree\ShreeWork\Apps\\drivers\\chromedriver\\chromedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client"); 
		
		WebDriver driver =  new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?access_type=offline&client_id=587594460880-u53ikl5ast2sup28098ofsm9iku8vvm6.apps.googleusercontent.com&code_challenge=HJJyHKK9KoUrlL2FHeVnCgnwM523rjYECkHtVU9lFmY&code_challenge_method=S256&include_granted_scopes=true&prompt=select_account%20consent&redirect_uri=https%3A%2F%2Fsso.teachable.com%2Fidentity%2Fcallbacks%2Fgoogle%2Fcallback&response_type=code&scope=email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile%20openid%20profile&state=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJpYXQiOjE2ODY4MzU4NDksImV4cCI6MTY4NjgzNzY0OSwianRpIjoiNmMxNWUxNzUtMzY3NC00YzFjLTk1ODctZGVjZTVjMWJiMmQ1IiwiaXNzIjoic2tfeno4dHc2ZGciLCJzdWIiOiIxN2p3cURKTmN0bi01OUZzOTcwZkFPY1BSdndPNGgxbDgxRkhoVlNtWWVyYndaVi1lQVd5MlBZSzVnQTd1NjdmQ1Z4T3VfVUdZS2hqaENDdk5rX015USJ9.4Yyup7c8Ry_8C4TPuoRxZ7G4AJFaJyCe5sBB4pGVi70&service=lso&o2v=2&flowName=GeneralOAuthFlow");
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&amp;auth_url=https://accounts.google.com/o/oauth2/v2/auth&amp;client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&amp;response_type=code&amp;redirect_uri=https://rahulshettyacademy.com/getCourse.php
		driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("ranubaghel671994@gmail.com");
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		driver.findElement(By.xpath("//body/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/span[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]")).sendKeys("Navodaya1!");
		driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		driver.wait(4000);
		String currentUrl = driver.getCurrentUrl();
**/	
		String currentUrl = "https://sso.teachable.com/identity/callbacks/google/callback?state=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJpYXQiOjE2ODY4NDE2NjksImV4cCI6MTY4Njg0MzQ2OSwianRpIjoiZjJlMDEzNjQtN2JkYi00MTQ2LTkwZmUtODQ4ZDVjMDcwYWQzIiwiaXNzIjoic2tfeno4dHc2ZGciLCJzdWIiOiJRSmQzUjE3eFlBejNza2VsODUzYkJQVEdyNEtjaTFnWnQ0UmpZa3lGOWZmYUQwQWM4Y3NSQ1N6V0VXbnp0NFM5cUJTTzZUendDSERFNm5JeEt1VC1nUSJ9.Deqt465-ZCtMWXC8Es_KBj7_cRCVl0e-AhsGoyuR_t8&code=4%2F0AbUR2VPmK5Ha9fSq-rsgg_tc4OGSe_vFVRkup1IB4cs43pSdb6cJ_qUuV-lw_ZDV-5TSXQ&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&authuser=0&prompt=consent";
		System.out.println("currentUrl - " + currentUrl);
		String codeValue = currentUrl.split("code=")[1];
		String actualCodeValue = codeValue.split("&scope=")[0];
				
		String getTokenResponse = given().log().all().urlEncodingEnabled(false)
				.queryParam("code", actualCodeValue)
				.queryParam("client_id", "587594460880-u53ikl5ast2sup28098ofsm9iku8vvm6.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
//				.queryParam("scope","email+profile")
//				.queryParam("response_type", "code")
				.queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(getTokenResponse);
		
		JsonPath jp = new JsonPath(getTokenResponse);
		String accessToken = jp.getString("access_token");
		
		
		String response = given().log().all()
				.queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		
	}

}
