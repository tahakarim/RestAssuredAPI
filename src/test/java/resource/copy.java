package resource;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class copy {

	private RequestSpecification httpRequest;

	@BeforeClass
	public void setup() {
		// Setting BaseURI once

		RestAssured.baseURI = "https://test.com";
		httpRequest = RestAssured.given();

	}

	@Test(priority = 0, description = "Basic Auth")
	public void checkAuthorizationTest() {

		Response response = httpRequest.auth().preemptive().basic("test", "test").given()
				.contentType("application/json").param("name", "asdasdh").when().get("/models").then().extract()
				.response();

		String bodyResponse = response.getBody().asString();
		Reporter.log("body response as string" + bodyResponse);

		System.out.println("Response body is " + bodyResponse);
		ValidatableResponse res = RestAssured.given().param("name", "asdasdh").when().get().then();
		res.statusCode(200);

	}

	@Test(priority = 1, description = "Post")
	public void createModes() {

		JSONObject obj2 = new JSONObject();
		obj2.put("name", "testsds");
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		list1.add(obj2);
		JSONObject obj = new JSONObject();
		obj.put("models", list1);
		System.out.println("i am a object \n" + obj.toString());
		System.out.println("testtest " + obj);
		Response response = httpRequest.auth().preemptive().basic("test", "test").given()
				.contentType("application/json").body(obj.toString()).when().post("/models").then().using().extract()
				.response();
		int statusCode = response.getStatusCode();
		System.out.println(statusCode + " this is post");
		System.out.println("This is body" + response.getBody().prettyPrint());

	}
}
