package Resources;

public class PayloadClass {
	
	public static String Addpayload() {
		String payload = "{\r\n" + 
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
				"";
		return payload;
	}
	
	public static String AddComplexPayload() {
		String payload = "{\r\n"
				+ "  \"dashboard\": {\r\n"
				+ "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n"
				+ "  },\r\n"
				+ "  \"courses\": [\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n"
				+ "      \"price\": 50,\r\n"
				+ "      \"copies\": 6\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Cypress\",\r\n"
				+ "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n"
				+ "      \"copies\": 10\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return payload;
	}
	public static String AddBookPayload (String isbnValue, String aisleValue) {
		String payload = "{\r\n"
				+ "\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\"" +isbnValue+ "\",\r\n"
				+ "\"aisle\":\"" +aisleValue+ "\",\r\n"
				+ "\"author\":\"John foe\"\r\n"
				+ "}";
		return payload;
	}
	public static String CreateJiraSession (String username, String password) {
		String payload = "{\r\n"
				+ "    \"username\": \"" +username+ "\",\r\n"
				+ "    \"password\": \"" +password+ "\"\r\n"
				+ "}";
		return payload;
	}
	public static String CreateJiraIssue () {
		String payload = "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"PROJ\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Bug1Summary\",\r\n"
				+ "        \"description\": \"Bug1Description\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return payload;
	}
	public static String AddJiraComment (String comment) {
		String payload = "{\r\n"
				+ "    \"body\": \"" +comment+ "\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
		return payload;
	}
	
}
