package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadOneProduct {
	SoftAssert softAssert;

	public ReadOneProduct() {
		softAssert = new SoftAssert();
	}

	@Test
	public void readOneProduct() {

		/*
		 * given: all input details(base
		 * URI,Headers,Payload/Body,QueryParameters,Authorization) when: submit api
		 * requests(Http method,Endpoint/Resource) then: validate response(status code,
		 * Headers, responseTime, Payload/Body)
		 * 
		 * EndPoint_Url: https://techfios.com/api-prod/api/product/read_one.php HTTP
		 * Method: GET Authorization: basic auth Query Params: id=4095 Header/headers:
		 * Content-Type:application/json status code: 200 OK
		 */

		Response response = given().log().all().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json").auth().basic("demo@techfios.com", "abc123")
				.queryParam("id", "4315").when().log().all().get("read_one.php").then().log().all().extract()
				.response();

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
		String productID = jason.get("id");
		// Hard Assert: //Assert.assertEquals(productID, "4309");
		softAssert.assertEquals(productID, "4315", "Product Id not matching!");

		String productName = jason.get("name");
		// Hard Assert: //Assert.assertEquals(productName, "Smart Devices");
		softAssert.assertEquals(productName, "Amazing Monitors by Obaid", "Product names are not matching!");

		String productPrice = jason.get("price");
		// Hard Assert: //Assert.assertEquals(productPrice, "399");
		softAssert.assertEquals(productPrice, "350", "Prices are not matching!");
		System.out.println("productPrice: " + productPrice);

		softAssert.assertAll();

	}

}
