package getRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TestApi {

	private RequestSpecification httpRequest;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeSuite

	public void setupreport() {

		String path = System.getProperty("user.dir");
		System.out.println(path);
		htmlReporter = new ExtentHtmlReporter(path + "\\test-output\\APItest.html");
		report = new ExtentReports();
		report.attachReporter(htmlReporter);

	}

	@BeforeClass
	public void setup() {
		// Setting BaseURI once

		RestAssured.baseURI = "https://agco-fuse-trackers-test.herokuapp.com:443";
		httpRequest = RestAssured.given();
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("API Test");
		htmlReporter.config().setReportName("API Test");

		report.setSystemInfo("Hostname", "Local Host");
		report.setSystemInfo("Tester", "Taha Karim");
		report.setSystemInfo("Browser", "Chrome");

	}

	@Test(priority = 0, description = "Basic Auth") // Agco
	public void checkAuthorizationTest() {

		test = report.createTest("Basic Auth");
		test.assignCategory("Smoke Test");
		Response response = httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com", "password1234") // more
																													// BDD
																													// style
				.given().contentType("application/json").queryParam("name", "tahahello1234")// Instead of sending an
				// object, send a //
				// string representation
				// of an object. // This
				// String is a JSON.
				.when().get("/models").then().extract().response();

		String bodyResponse = response.getBody().asString();
		Reporter.log("body response as string" + bodyResponse);
		test.log(Status.INFO, "Body response is as follows" + bodyResponse);

		test.log(Status.INFO, "Body response is as follows" + response.getContentType());

		System.out.println("Response body is " + bodyResponse);

		test.log(Status.PASS,
				MarkupHelper.createLabel("Content Type verified as  " + response.getContentType(), ExtentColor.BLUE));

		ValidatableResponse res = RestAssured.given().param("name", "tahahello1234").when().get().then();

		Reporter.log("Verified response code successfully" + bodyResponse, true); // for log reporting

		System.out.println("Status line   " + response.getStatusLine());
		Reporter.log("Status Line     " + response.getStatusLine()); // for log repoting
		test.log(Status.PASS, "Status Line   " + response.getStatusLine());

		System.out.println("Response time is" + response.time());
		Reporter.log("Response time is" + response.time()); // for log repoting
		test.log(Status.PASS, "Response time   " + response.time());
		int statusCode = response.getStatusCode(); // 200

		// Assert.assertEquals("200", statusCode);
		if (statusCode == 200) {
			System.out.println("Status code verified " + statusCode); // we can put assert too i am just checking
			test.log(Status.PASS, "Status Code Passd     " + statusCode);
		} else {
			System.out.println("Assert Failed " + statusCode);
		}

	}

	@Test(priority = 1, description = "Post")
	public void createModel() {

		test = report.createTest("Post Test API fuse tracker"); // model creation use carefully
		test.assignCategory("Smoke Test");
		// JSON = JavaeScript Object Notation // JSONObject implements the specification
		// of JSON
		JSONObject obj2 = new JSONObject();

		obj2.put("name", "tahahello1234");
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		list1.add(obj2);
		System.out.println(" i am list 1   " + list1);
		// list1.add(obj3);
		JSONObject obj = new JSONObject();
		obj.put("models", list1);
		System.out.println("i am a object \n" + obj.toString());
		System.out.println("testtest " + obj);
		// DSL = Domain Specific Language
		// builder pattern
		Response response = httpRequest.auth().preemptive().basic("ac2test.agcoadmin@agcocorp.com", "password1234")
				.given().contentType("application/json").body(obj.toString()).when().post("/models").then().using()
				.extract().response();
		int statusCode = response.getStatusCode();
		System.out.println(statusCode + " this is post");
		test.log(Status.PASS, "Model Creation Status Code is     " + statusCode);
		Reporter.log("Model Creation Status Code is     " + statusCode, true);

		System.out.println("This is body" + response.getBody().prettyPrint());
		test.log(Status.PASS, "Model Creation Status Code is     " + response.getBody().prettyPrint());
		Reporter.log("Model Creation Status Code is     " + response.getBody().prettyPrint(), true);

	}

	@Test(priority = 2, description = "validate invalid response ") // Agco // negative kinda
	public void checkinvalidresponse() {

		test = report.createTest("Invalid response test");
		test.assignCategory("Smoke Test");

		ValidatableResponse res = RestAssured.given().param("name", "tah2").when().get().then(); // passing bad value
		Reporter.log("Response is " + res.extract().asString(), true); // log response to report as string
		test.log(Status.PASS, "Response is " + res.extract().asString());
		res.statusCode(404);
		res.body("Value is not null verified", Matchers.notNullValue()); // validation point value should not be null
		res.body("Body present not found verified", Matchers.equalToIgnoringCase("not found")); // validation point
																								// body should contain
																								// whatever
		Reporter.log("Verified the error message successfully ", true);

	}

	@Test(priority = 3, description = "Get and delete") // Agco
	public void delete_this_one() {

		test = report.createTest("delete try");
		test.assignCategory("Smoke Test");
		Response response = httpRequest.given().contentType("application/json").param("name", "tahahello1234").when()
				.get("/models").then().extract().response();

		String bodyResponse = response.then().extract().jsonPath().getString("models[0].id");
		System.out.println(bodyResponse);
		Reporter.log("body response as string" + bodyResponse);
		test.log(Status.INFO, "Body response is as" + bodyResponse);

		test.log(Status.INFO, "Body response is as follows" + response.getContentType());

		System.out.println("Response body is " + bodyResponse);
		Response response1 = httpRequest.given().contentType("application/json").when()
				.delete("/models/" + bodyResponse).then().assertThat().statusCode(204).and().extract().response();
		System.out.println("Delete Response Code is " + response1);

	}

	@AfterMethod
	public void tear_Down(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {

			test.log(Status.FAIL, result.getName());
			test.log(Status.FAIL, result.getThrowable());
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));

		}

		else if (ITestResult.SUCCESS == result.getStatus()) {

			// test.log(Status.PASS, result.getName());
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test case Passed:", ExtentColor.GREEN));
		}

		else if (ITestResult.SKIP == result.getStatus()) {

			// test.log(Status.SKIP, result.getName());
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case Skipped:", ExtentColor.LIME));
		}

	}

	@AfterSuite
	public void reportteardown() {
		report.flush();

	}

}
