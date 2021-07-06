package getRequest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestonlocalAPI {

	private static String payload = "{\n" + "  \"firstName\": \"Taha1\",\n" + "  \"lastName\": \"somename\",\n"
			+ "  \"subjectId\": \"1\"\n" + "}";

	@BeforeClass

	public void startup() {

		baseURI = "http://localhost:3000";
	}

	// @Test(priority = 0)
	// public void get() {
	//
	// given().get("/users").then().statusCode(200).log().all(); // get request,
	// then validate statuscode response //
	// // is 200 log on to console
	//
	// }

	@Test(priority = 1)
	public void post() {

		JSONObject request = new JSONObject();
		request.put("firstName", "Grainger");
		request.put("lastName", "Company");
		request.put("subjectId", 1);

		System.out.println(request);
		System.out.println(request.toJSONString());
		given().when().headers("Content-Type", "Application/JSON").body(payload).post("/users").then().statusCode(201);

	}
	//
	// @Test(priority = 2)
	// public void put() {
	//
	// JSONObject request = new JSONObject();
	// request.put("firstName", "Grainger");
	// request.put("lastName", "organization");
	// request.put("subjectId", 3);
	// given().when().headers("Content-Type",
	// "application/json").accept(ContentType.JSON).body(request.toJSONString())
	// .put("/users/4").then().statusCode(200);
	//
	// }
	//
	// @Test(priority = 3)
	// public void patch() {
	//
	// JSONObject request = new JSONObject();
	// request.put("lastName", "organization");
	//
	// given().when().headers("Content-Type",
	// "application/json").accept(ContentType.JSON).body(request.toJSONString())
	// .patch("/users/4").then().statusCode(200);
	//
	// }
	//
	// @Test(priority = 4)
	// public void delete() {
	//
	// given().when().delete("/users/4").then().statusCode(200);
	//
	// }
	//
	// @Test(priority = 5)
	// public void justforfun() {
	//
	// Response res = given().param("firstName", "Taha1").when().get("/users/1");
	// // System.out.println(given().param("firstName",
	// // "Grainger").when().get("/users/1"));
	// // System.out.println(res);
	// System.out.println(res.getBody());
	// System.out.println(res.getBody().asString());
	// }
}
