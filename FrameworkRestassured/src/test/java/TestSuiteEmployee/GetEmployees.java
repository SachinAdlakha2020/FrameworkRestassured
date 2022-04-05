package TestSuiteEmployee;

import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetEmployees extends BaseClass {

	
	@Test(enabled=false)
	public void GetEmpListWithHeader()   throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		headers.put("Accept", "application/json");
		
		
		//Response response = RestAPiHelper.GetResponse("users?page=2", headers);
		Response response = RestAPiHelper.GetResponse("users?page=2", headers,"michael.lawson@reqres.in","",2);
				
	}
	
	/*@Test
	public void GetEmployeesCheckWithStatusCode() throws URISyntaxException {
		int statusCode = RestAssured.given()
		.accept(ContentType.JSON)
		.when()
		.get(new URI("users?page=2"))
		.thenReturn()
		.statusCode();
		Assert.assertEquals(HttpStatus.SC_OK, statusCode);
		System.out.println(statusCode);
		
	}
	
	@Test
	public void GetEmployeesCheckWithStatusCodeAssertThat() throws URISyntaxException {
		 RestAssured.given()
		.accept(ContentType.JSON)		
		.when()
		.get(new URI("users?page=2"))
		.then()
		.assertThat()
		.statusCode(HttpStatus.SC_OK);
	
		
	}*/
	
}
