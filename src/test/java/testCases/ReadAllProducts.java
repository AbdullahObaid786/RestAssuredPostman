package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadAllProducts {

	SoftAssert softAssert;

	@Test
	public void readAllProducts() {

		softAssert = new SoftAssert();

		/*
		 * given: all input details(base
		 * URI,Headers,Payload/Body,QueryParameters,Authorization) when: submit api
		 * requests(Http method,Endpoint/Resource) then: validate response(status code,
		 * Headers, responseTime, Payload/Body)
		 * 
		 * EndPoint_Url: https://techfios.com/api-prod/api/product/read.php HTTP Method:
		 * GET Authorization: Basic Auth Header/headers: Content-Type:application/json;
		 * charset=UTF-8 status code: 200
		 * 
		 */

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
		System.out.println("actual Response Body: " + actualResponseBody);

		JsonPath jason = new JsonPath(actualResponseBody);
		String firstProductId = jason.get("records[0].id");

		if (firstProductId != null) {
			System.out.println("Product exists. ");
		} else {
			System.out.println("Product does not exist! ");
		}

	}

}
