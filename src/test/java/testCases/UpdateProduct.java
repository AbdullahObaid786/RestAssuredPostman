package testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateProduct {

	SoftAssert softAssert;
	Map<String, String> createPayloadMap;
	Map<String, String> updatedPayloadMap;
	String expectedProductName;
	String expectedProductPrice;
	String expectedProductDescription;
	String firstProductId;

	public UpdateProduct() {

		softAssert = new SoftAssert();
	}

	public Map<String, String> createPayloadMap() {

		createPayloadMap = new HashMap<String, String>();

		createPayloadMap.put("name", "Amazing Laptops by Obaid");
		createPayloadMap.put("price", "1099");
		createPayloadMap.put("description", "The best Laptops for amazing QA Automations.");
		createPayloadMap.put("category_id", "2");

		return createPayloadMap;

	}

	public Map<String, String> updatedPayloadMap() {

		updatedPayloadMap = new HashMap<String, String>();
		updatedPayloadMap.put("id", firstProductId);
		updatedPayloadMap.put("name", "Obaid's Amazing Laptops 4.0");
		updatedPayloadMap.put("price", "2599");
		updatedPayloadMap.put("description", "The Updated Laptops for amazing QA Automations.");
		updatedPayloadMap.put("category_id", "2");

		expectedProductName = updatedPayloadMap.get("name");
		System.out.println("expected product Name: " + expectedProductName);

		expectedProductPrice = updatedPayloadMap.get("price");
		System.out.println("expected product Price: " + expectedProductPrice);

		expectedProductDescription = updatedPayloadMap.get("description");
		System.out.println("expected product Description: " + expectedProductDescription);

		return updatedPayloadMap;

	}

	@Test(priority = 0)
	public void createNewProduct() {

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
				.basic("demo@techfios.com", "abc123").body(createPayloadMap()).when().post("/create.php").then()
				.extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		softAssert.assertEquals(actualResponseStatus, 201, "Status Codes are not matching! ");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
		softAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8",
				"Response Content_Types are not matching!");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jason = new JsonPath(actualResponseBody);
		String productMessage = jason.get("message");
		softAssert.assertEquals(productMessage, "Product was created.", "Product Messages are not matching!");

		softAssert.assertAll();

	}

	@Test(priority = 1)
	public void readAllProducts() {

		Response response = given().baseUri("https://techfios.com/api-prod/api/product")
				.header("content-Type", "application/json; charset=UTF-8").auth().basic("demo@techfois.com", "abc123")
				.when().get("/read.php").then().extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		// Hard Assert: Assert.assertEquals(actualResponseStatus, 200);
		softAssert.assertEquals(actualResponseStatus, 200);

		String actualResponseContentType = response.getHeader("content-Type");
		System.out.println("actual Response Content-Type: " + actualResponseContentType);
		// Hard Assert:Assert.assertEquals(actualResponseContentType, "application/json;
		// charset=UTF-8");
		softAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");

		String actualResponseBody = response.getBody().asString();
		// System.out.println("actual Response Body: " + actualResponseBody);

		JsonPath jason = new JsonPath(actualResponseBody);
		firstProductId = jason.get("records[0].id");

		System.out.println("first Product Id:" + firstProductId);

		softAssert.assertAll();

	}

	@Test(priority = 2)
	public void updateProduct() {

		Response response = given().log().all().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").auth().basic("demo@techfios.com", "abc123")
				.body(updatedPayloadMap()).when().put("/update.php ").then().extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		// Hard Assert: //Assert.assertEquals(actualResponseStatus, 201);
		softAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response Content Type: " + actualResponseContentType);
		// Hard Assert: //Assert.assertEquals(actualResponseContentType,
		// "application/json");
		softAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");

		String actualReponseBody = response.getBody().asString();
		System.out.println("actual Reponse Body: " + actualReponseBody);

		JsonPath jason = new JsonPath(actualReponseBody);
		String actualProductMessage = jason.get("message");
		// Hard Assert: //Assert.assertEquals(productID, "4309");
		softAssert.assertEquals(actualProductMessage, "Product was updated.", "Product Message not matching!");

		softAssert.assertAll();

	}

	@Test(priority = 3)
	public void readOneProduct() {

		Response response = given().log().all().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json").auth().basic("demo@techfios.com", "abc123")
				.queryParam("id", firstProductId).when().get("read_one.php").then().extract().response();

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		// Hard Assert: //Assert.assertEquals(actualResponseStatus, 201);
		softAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response Content Type: " + actualResponseContentType);
		// Hard Assert: //Assert.assertEquals(actualResponseContentType,
		// "application/json");
		softAssert.assertEquals(actualResponseContentType, "application/json");

		String actualReponseBody = response.getBody().asString();
		System.out.println("actual Reponse Body: " + actualReponseBody);

		JsonPath jason = new JsonPath(actualReponseBody);
		String actualProductID = jason.get("id");
		// Hard Assert: //Assert.assertEquals(productID, "4309");
		softAssert.assertEquals(actualProductID, firstProductId, "Product Id not matching!");

		String actualProductName = jason.get("name");
		// Hard Assert: //Assert.assertEquals(productName, "Smart Devices");
		softAssert.assertEquals(actualProductName, expectedProductName, "Product names are not matching!");

		String actualProductPrice = jason.get("price");
		// Hard Assert: //Assert.assertEquals(productPrice, "399");
		softAssert.assertEquals(actualProductPrice, expectedProductPrice, "Prices are not matching!");

		String actualProductDescription = jason.get("description");
		// Hard Assert: //Assert.assertEquals(productPrice, "399");
		softAssert.assertEquals(actualProductDescription, expectedProductDescription,
				"Product Descriptions are not matching!");

		softAssert.assertAll();

	}

}
