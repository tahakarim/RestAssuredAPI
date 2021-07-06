/*
 * package getRequest;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.json.JSONObject; import org.testng.annotations.BeforeClass; import
 * org.testng.annotations.Test;
 * 
 * import io.restassured.RestAssured; import io.restassured.http.Method; import
 * io.restassured.response.Response; import
 * io.restassured.specification.RequestSpecification;
 * 
 * public class getData {
 * 
 * // final static String ROOT_URI = //
 * "http://restapi.demoqa.com/utilities/weather/city";
 * 
 * private RequestSpecification httpRequest;
 * 
 * @BeforeClass public void setup() { // Setting BaseURI once
 * 
 * 
 * RestAssured.baseURI = "https://agco-fuse-trackers-test.herokuapp.com:443";
 * httpRequest = RestAssured.given();
 * 
 * }
 * 
 * @Test(priority = 0, description = "Basic Auth") // Agco public void
 * checkAuthorizationTest() {
 * 
 * // Passing the Basic Auth username and password
 * httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com",
 * "password1234"); httpRequest.header("Content-Type", "application/json");
 * 
 * // Expecting our Auth works and verifying status code // request method get
 * Response responseWithAuth = httpRequest.request(Method.GET,
 * "/models?name=tahabhaiihello1234"); String bodyResponse =
 * responseWithAuth.getBody().asString();
 * 
 * System.out.println("Response body is " + bodyResponse); int statusCode =
 * responseWithAuth.getStatusCode(); // 200
 * 
 * // Assert.assertEquals("200", statusCode); if (statusCode == 200) {
 * System.out.println("Status code verified " + statusCode); } else {
 * System.out.println("Assert Failed " + statusCode); }
 * 
 * }
 * 
 * @Test(priority = 1, description = "Post") public void createModel() {
 * 
 * // JSON = JavaeScript Object Notation // JSONObject implements the
 * specification of JSON JSONObject obj2 = new JSONObject(); JSONObject obj3 =
 * new JSONObject(); obj3.put("name", "onemorename1212"); obj2.put("name",
 * "tahabhaiihello123451212"); List<JSONObject> list1 = new
 * ArrayList<JSONObject>(); list1.add(obj2); list1.add(obj3); JSONObject obj =
 * new JSONObject(); obj.put("models", list1);
 * System.out.println("i am a object \n" + obj.toString());
 * System.out.println("testtest " + obj); // DSL = Domain Specific Language //
 * builder pattern Response response =
 * httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com",
 * "password1234") .given().contentType("application/json").body(obj.toString())
 * // Instead of sending an object, send a // string representation of an
 * object. // This String is a JSON.
 * .when().post("/models").then().using().extract().response(); int statusCode =
 * response.getStatusCode(); System.out.println(statusCode + " this is post");
 * 
 * System.out.println("This is Defect" + response.getBody().prettyPrint());
 * 
 * }
 * 
 * }
 */
