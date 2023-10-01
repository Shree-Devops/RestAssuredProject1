package pojo.SpecBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.*;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class EcommerceAPItest {

	public static void main(String[] args) {
	
		LoginRequest login = new LoginRequest();
		login.setUserEmail("06shreemohini@gmail.com");
		login.setUserPassword("Navodaya1!");
		
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").build();
		
//Generating Auth token
		
		RequestSpecification loginReq = given().log().all().spec(reqSpec).header("Content-Type", "application/json").body(login);
		
		LoginResponse loginResponse  = loginReq.when().post("api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		System.out.println("Token - " + loginResponse.getToken());
		System.out.println("UserId - " + loginResponse.getUserId());
		
//send attachment in api call
//add product
		
		RequestSpecification addProductReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("authorization", loginResponse.getToken()).setContentType(ContentType.MULTIPART).build();
		
		RequestSpecification addProductReq = given().log().all().spec(addProductReqSpec)
				.param("productAddedBy", loginResponse.getUserId())
				.param("productCategory", "fashion").param("productFor", "men")
				.param("productName", "Laptop").param("productSubCategory", "shirts")
				.param("productPrice", "11500").param("productDescription", "lenova")
				.multiPart("productImage", new File("C:\\Shree\\ShreeWork\\study\\image.jpeg"));
				
		String addProductResponse  = addProductReq.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		
// create Order
		
		RequestSpecification createOrderReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("authorization", loginResponse.getToken()).setContentType(ContentType.JSON).build();
		
		CreateOrderDetailsRequest createOrderDetailsReq =  new CreateOrderDetailsRequest();
		createOrderDetailsReq.setCountry("India");
		createOrderDetailsReq.setProductOrderedId(productId);
		
		List<CreateOrderDetailsRequest> createOrderDetailsReqList = new ArrayList<CreateOrderDetailsRequest>();
		createOrderDetailsReqList.add(createOrderDetailsReq);
		
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setOrders(createOrderDetailsReqList);
		
		RequestSpecification createOrderReq = given().spec(createOrderReqSpec).body(createOrderRequest).log().all();
		
		CreateOrderResponse createOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().as(CreateOrderResponse.class);
		
		for (int i=0; i< createOrderResponse.getOrders().size(); i++)
		{
			String orderId = createOrderResponse.getOrders().get(i);
			System.out.println("OrderId - " +i + " = " + orderId);
			
			//View Order details
			
			RequestSpecification viewOrderDetailsReqSpec =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
					.addHeader("authorization", loginResponse.getToken()).addQueryParam("id", orderId)
					.setContentType(ContentType.JSON).build();
			
			RequestSpecification viewOrderDetailsReq = given().spec(viewOrderDetailsReqSpec).log().all();
			
			ViewOrderDetailsResponse viewOrderDetailsResponse = viewOrderDetailsReq.when().get("/api/ecom/order/get-orders-details")
					.then().log().all().extract().response().as(ViewOrderDetailsResponse.class);
			
			System.out.println("Response OrderId - " + viewOrderDetailsResponse.getData().get_id());
			System.out.println("Response Product Id - " + viewOrderDetailsResponse.getData().getProductOrderedId());
			System.out.println("Response message - " + viewOrderDetailsResponse.getMessage());
		}
			
// Sending Path parameter  -   Delete Product 
		
		RequestSpecification deleteProductReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addHeader("authorization", loginResponse.getToken()).addPathParam("productId", productId).build();
		
		RequestSpecification deleteProductReq =  given().spec(deleteProductReqSpec).log().all();
		
		String deleteProductResponse = given().spec(deleteProductReq).when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		JsonPath jjs =  new JsonPath(deleteProductResponse);
		jjs.get("message");
				
	}

}
