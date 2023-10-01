package Rest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;

import Resources.PayloadClass;
import io.restassured.filter.session.SessionFilter;

public class JIRAaUTOMATION {
		
	public static void main(String[] args) {
		
		String username = "ShreeBaghel";
		String password = "Navodaya1!";
		
		RestAssured.baseURI = "http://localhost:8080/";	
		
		SessionFilter session = new SessionFilter();
		
		String sessionResponse = given().relaxedHTTPSValidation().log().all().header("content-type", "application/json").filter(session)
				.body(PayloadClass.CreateJiraSession(username, password))
				.when().post("/rest/auth/1/session/")
				.then().log().all().assertThat().statusCode(200).body("session.name", equalTo("JSESSIONID"))
				.extract().response().asString();
		
		JsonPath jp = new JsonPath(sessionResponse);
		String jsonIdValue = jp.get("session.value");
		
// Create JIRA Issue

		String issueResponse = given().log().all().header("content-type", "application/json").header("cookie", "JSESSIONID=" + jsonIdValue).body(PayloadClass.CreateJiraIssue())
				.when().post("/rest/api/2/issue/")
				.then().log().all().assertThat().statusCode(201)
				.extract().response().asString();
		JsonPath jp1 = new JsonPath(issueResponse);
		String issueId = jp1.get("id");
		
// Add comment in JIRA using SessionFilter instead of .header("cookie", "JSESSIONID=" + jsonIdValue)
		String addedComment = "My JIRA comment.";
		String commentResponse = given().log().all().pathParam("issueIdOrKey", issueId).filter(session).header("content-type", "application/json")
				.body(PayloadClass.AddJiraComment(addedComment))
				.when().post("/rest/api/2/issue/{issueIdOrKey}/comment")
				.then().log().all().assertThat().statusCode(201)
				.extract().response().asString();
		JsonPath jp2 = new JsonPath(commentResponse);
		String addedCommentId = jp2.get("id");

// Add attachment in Issue
		// curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		
		given().log().all().filter(session).header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
				.pathParam("issueIdOrKey", issueId).multiPart("file", new File("C:\\Shree\\ShreeWork\\study\\Rest Assured API.txt"))
				.when().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
				.then().log().all().assertThat().statusCode(200);

// Get Issue Details and validate if commentaddedId is present
		String issueDetailsResponse = given().log().all().filter(session).pathParam("issueIdOrKey", issueId).queryParam("fields", "comment")
				.header("content-type", "application/json")
				.when().get("/rest/api/2/issue/{issueIdOrKey}")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
				
		JsonPath jp3 = new JsonPath(issueDetailsResponse);
		int size = jp3.getInt("fields.comment.comments.size()");

		for (int i=0; i< size; i++)
		{			
			if(jp3.get("fields.comment.comments[" +i+ "].id").equals(addedCommentId))
			{				
				System.out.println("Added comment is present");
				System.out.println("addedCommentId - " + jp3.get("fields.comment.comments[" +i+ "].body"));
				Assert.assertEquals(jp3.get("fields.comment.comments[" +i+ "].body"), addedCommentId);
			}
		}

	}

}
