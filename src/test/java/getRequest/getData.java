package getRequest;

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

public class getData {

	private RequestSpecification httpRequest;

	@BeforeClass
	public void setup() {
		// Setting BaseURI once

		RestAssured.baseURI = "https://agco-fuse-trackers-test.herokuapp.com:443";
		httpRequest = RestAssured.given();

	}

	@Test(priority = 0, description = "Basic Auth") // Agco
	public void checkAuthorizationTest() {

		Response response = httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com", "password1234")
				.given().contentType("application/json").queryParam("name", "tahaih").when().get("/models").then()
				.extract().response();

		String bodyResponse = response.getBody().asString();
		Reporter.log("body response as string" + bodyResponse);

		System.out.println("Response body is " + bodyResponse);
		ValidatableResponse res = RestAssured.given().param("name", "tahaihello123451212").when().get().then();
		res.statusCode(200);

		Reporter.log("Verified response code successfully" + bodyResponse, true); // for log reporting
		System.out.println("Response header is" + response.getStatusLine());
		Reporter.log("Response body is " + response.getStatusLine()); // for log repoting
		System.out.println("Response header is" + response.time());
		Reporter.log("Response header is" + response.time()); // for log repoting

	}

	@Test(priority = 1, description = "Post")
	public void createModel() {

		// JSON = JavaeScript Object Notation
		// JSONObject implements the specification of JSON
		JSONObject obj2 = new JSONObject();
		// JSONObject obj3 = new JSONObject();
		// obj3.put("name", "onemorename1212");
		obj2.put("name", "tahahello123");
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		list1.add(obj2);
		// list1.add(obj3);
		JSONObject obj = new JSONObject();
		obj.put("models", list1);
		System.out.println("i am a object \n" + obj.toString());
		System.out.println("testtest " + obj);
		// DSL = Domain Specific Language
		// builder pattern
		Response response = httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com", "password1234")
				.given().contentType("application/json").body(obj.toString()) // Instead of sending an object, send a
																				// string representation of an object.
																				// This String is a JSON.
				.when().post("/models").then().using().extract().response();
		int statusCode = response.getStatusCode();
		System.out.println(statusCode + " this is post");

		System.out.println("This is body" + response.getBody().prettyPrint());

	}

	/*
	 * @Test(priority = 2, description = "Post") public void httpPostMethod() {
	 * 
	 * // Rest API's URL String restAPIUrl =
	 * "https://agco-fuse-trackers-test.herokuapp.com:443/models";
	 * 
	 * // API Body String apiBody =
	 * "{ \"models\": [ { \"name\": \"TahaTest9874\" } ]}";
	 * 
	 * // Building request by using requestSpecBuilder RequestSpecBuilder builder =
	 * new RequestSpecBuilder();
	 * 
	 * // Set API's Body builder.setBody(apiBody);
	 * 
	 * // Setting content type as application/json
	 * builder.setContentType("application/json");
	 * 
	 * RequestSpecification requestSpec = builder.build();
	 * 
	 * // Making post request with authentication or leave blank if you don't have
	 * // credentials like: basic("","") Response response =
	 * httpRequest.given().auth().preemptive()
	 * .basic("ac2test.agcoadmin@agcocorp.com",
	 * "password1234").spec(requestSpec).when().post(restAPIUrl);
	 * 
	 * JSONObject JSONResponseBody = new JSONObject(response.body().asString());
	 * 
	 * // Get the desired value of a parameter
	 * 
	 * }
	 */

}
