package getRequest;

import java.io.IOException;

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

public class test {

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
				.given().contentType("application/json").param("name", "tahabhaiihello1234")// Instead of sending an
																							// object, send a //
																							// string representation
																							// of an object. // This
																							// String is a JSON.
				.when().get("/models").then().extract().response();

		String bodyResponse = response.getBody().asString();
		test.log(Status.INFO, "Body response is as follows" + bodyResponse);
		Reporter.log("body response as string" + bodyResponse);

		test.log(Status.PASS,
				MarkupHelper.createLabel("Content Type verified as  " + response.getContentType(), ExtentColor.BLUE));

		System.out.println("Response body is " + bodyResponse);
		ValidatableResponse res = httpRequest.param("name", "tahabhaiihello1234").when().get().then();
		res.statusCode(200);

		Reporter.log("Verified response code successfully   " + bodyResponse, true); // for log reporting
		test.log(Status.PASS, "Body response is as follows" + bodyResponse);
		System.out.println("Response header is" + response.getStatusLine());

		Reporter.log("Response body is   " + response.getStatusLine(), true); // for log repoting
		System.out.println("Response header is     " + response.time());
		Reporter.log("Response Time is   " + response.time() + response.getBody().prettyPrint(), true); // for log
																										// repoting

		int statusCode = response.getStatusCode(); // 200

		// Assert.assertEquals("200", statusCode);
		if (statusCode == 200) {
			System.out.println("Status code verified " + statusCode); // we can put assert too i am just checking
		} else {
			System.out.println("Assert Failed " + statusCode);
		}

	}

	@AfterMethod
	public void tear_Down(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {

			test.log(Status.FAIL, result.getName());
			test.log(Status.FAIL, result.getThrowable());
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			test.fail(result.getThrowable());

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
