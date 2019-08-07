package API.fileUpload;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.logging.Logger;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.*;
import org.testng.annotations.*;

public class APIUploadFile {

	final String apiKey = "17971d2ab6468ee4804caa3ea32374e4c7012f57";
	String url = "https://platform.rescale.com/api/v2/";
	String fileName = "/Users/arajagopalan/Downloads/file_test";

	static Logger logger = Logger.getLogger(APIUploadFile.class.getName());

	/**
	 * Testng test method to upload the file in rescale platform
	 * 
	 * @param - pass the end point for file upload using the uploadEndPoint
	 *          dataProvider
	 */
	@Test(dataProvider = "uploadEndPoint")
	public void uploadFile(String endPoint) {
		Response resp = this.getSpec().multiPart(new File(fileName)).post(url + endPoint);

		Assert.assertEquals(resp.getStatusCode(), 201);

		logger.info("verified the status code of the file upload");
		JsonPath path = new JsonPath(resp.asString());
		Assert.assertEquals(path.get("owner"), "ambikar@gmail.com");
		logger.info("Verified the owner of the file upload");

	}

	/**
	 * method to get the list of files for a user based on the API token and verify
	 * that it contains the file uploaded through the previous method
	 * 
	 * @param pass the name of the file to be asserted with the list of files
	 *             through the fileListEndPoint dataProvider
	 */
	@Test(dataProvider = "fileListEndPoint", dependsOnMethods = "uploadFile")
	public void verifyFileUpload(String files) {

		Response resp = this.getSpec().get(url + files);
		JsonPath path = new JsonPath(resp.asString());

		System.out.println("status code for verifyFileUpload: " + resp.getStatusCode());
		System.out.println(path.prettyPrint());

		Assert.assertEquals(resp.getStatusCode(), 200);

	}

	/**
	 * Data provider passing the endpoint for file upload
	 * 
	 * @return upload file endPoint
	 */
	@DataProvider
	public Object[][] uploadEndPoint() {
		return new Object[][] { { "files/contents/" } };
	}

	/**
	 * Data provider passing the endpoint for file list
	 * 
	 * @return endPoint for listing the files
	 */

	@DataProvider
	public Object[][] fileListEndPoint() {
		return new Object[][] { { "files/" } };
	}

	/**
	 * getter method which returns the RequestSpecification object which can be
	 * reused between various API calls
	 * 
	 * @return RequestSpecification object
	 */
	private RequestSpecification getSpec() {
		return given().headers("Content-Type", "multipart/form-data", "Authorization", "Token " + apiKey);
	}
}
