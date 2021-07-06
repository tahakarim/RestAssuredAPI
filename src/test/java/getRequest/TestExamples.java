package getRequest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TestExamples {

	@BeforeClass
	public void startup() {

		baseURI = "https://reqres.in/api";

	}

	@Test
	public void test1() {
		Response response = given().get("/users?page=2");

		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
		System.out.println(response.getBody().prettyPrint());
		System.out.println(response.getStatusLine());
		System.out.println(response.statusLine());
		System.out.println(response.getContentType());
		System.out.println(response.headers());
		System.out.println(response.header("Content-type"));
		System.out.println(response.getSessionId());

		// int statuscode = 201;
		// Assert.assertEquals(response.getStatusCode(), statuscode);
		//
	}

	@Test
	public void test_2() {
		// Basic auth
		given().auth().basic("abc", "password").when().get("/users?page=2").then().statusCode(200).body("data[1].id",
				equalTo(8)); //

	}
	//
	// @Test
	// public void test_get() {
	//
	// given().when().get("/users?page=2").then().statusCode(200).body("data.first_name",
	// hasItems("Michael", "Lindsay"));
	// }
	//
	// @Test
	// public void test_post() {
	//
	// JSONObject request = new JSONObject(); // using simple json library to
	// convert String to json to create payload
	// request.put("name", "Taha");
	// request.put("Job", "Student");
	// System.out.println(request.toJSONString());
	// given().when().header("Content-Type",
	// "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
	// .body(request).post("/users").then().statusCode(201).log().all();
	//
	// }
	//
	// @Test
	// public void test_put() {
	//
	// JSONObject request = new JSONObject(); // using simple json library to
	// convert String to json to create payload
	// request.put("name", "Taha");
	// request.put("Job", "learner");
	// System.out.println(request.toJSONString());
	// given().header("Content-Type",
	// "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
	// .body(request).put("/users/2").then().statusCode(200).log().all();
	//
	// }
	//
	// @Test
	// public void test_patch() {
	//
	// JSONObject request = new JSONObject(); // using simple json library to
	// convert String to json to create payload
	// request.put("name", "Taha");
	// request.put("Job", "learner");
	// System.out.println(request.toJSONString());
	// given().header("Content-Type",
	// "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
	// .body(request).patch("/users/2").then().statusCode(200).log().all();
	//
	// }
	//
	// @Test
	// public void test_delete() {
	//
	// given().when().delete("/users/2").then().statusCode(204).log().all();
	//
	// }
}
