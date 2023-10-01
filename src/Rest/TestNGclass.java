package Rest;

import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import Resources.PayloadClass;

public class TestNGclass {
	
	@Test
	public void functions() {
		// *****************************************************************************************************************************			
		/* 	1. Print No of courses returned by API
			2. Print Purchase Amount
			3. Print Title of the first course
			4. Print All course titles and their respective Prices
			5. Print no of copies sold by RPA Course
			6. Verify if Sum of all Course prices matches with Purchase Amount
		 */
		
		JsonPath js = new JsonPath(PayloadClass.AddComplexPayload());
		
		int courseCount = js.getInt("courses.size()");
		System.out.println("Total no of courses returned by API - " + courseCount);
		System.out.println("Purchase Amount - " + js.get("dashboard.purchaseAmount"));
		System.out.println("Title of the first course - " +  js.get("courses[0].title"));
		
		for (int i=0; i< courseCount; i++ )
		{
			System.out.println("Title- " + js.get("courses[" + i +"].title") + ", price- " + js.get("courses[" +i+ "].price"));
		}
		
		for (int i=0; i< courseCount; i++ )
		{
			if(js.get("courses[" +i+ "].title").equals("RPA"))
			{
				System.out.println("No of copies sold by RPA Course - " + js.get("courses[" +i+ "].copies"));
			}
		}
		
		int totalPrice =0;
		for(int i=0; i<courseCount; i++) {
			int price = js.getInt("courses[" +i+ "].price") * js.getInt("courses[" +i+ "].copies");
			totalPrice = price + totalPrice;
		}
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), totalPrice);
			
	}

}
