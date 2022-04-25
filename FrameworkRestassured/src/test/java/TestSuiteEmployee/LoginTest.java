package TestSuiteEmployee;

import static org.testng.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.google.common.base.Verify;

//import com.github.tomakehurst.wiremock.http.Response;

import DataHelper.LoginHelper;
import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.LoginRequest;
import Repository.LoginResponse;
import Utility.JacksonHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mockService.WireMockTest;
import static org.assertj.core.api.Assertions.*;

public class LoginTest extends WireMockTest{
	@Test(enabled= false)
	public void ValidLoginTest() throws URISyntaxException {
		LoginHelper loginHelper = new LoginHelper();
		LoginRequest loginRequest = loginHelper.GetLoginRequestData();
		
		
		String xmlPostString = JacksonHelper.ToXml(loginRequest);
		System.out.println("xmlPostString : " + xmlPostString);
		Map<String, String> headers= new Hashtable<String, String>();
		headers.put("Accept", "text/xml");
		headers.put("Content-Type", "text/xml");
		//https://reqbin.com/echo/post/xml
		Response response =RestAPiHelper.PostRequest1("/post/xml",headers,xmlPostString);
		
		LoginResponse loginResponse=(LoginResponse) JacksonHelper.ToObject(response.asString(), new LoginResponse());
		
		System.out.println(response.asString());
		assertEquals(loginResponse.responseCode, 0);
	
		
	}
	
	@Test(enabled= false)
	public void ValidLoginWithMockTestSample01() throws URISyntaxException {
		LoginHelper loginHelper = new LoginHelper();
		LoginRequest loginRequest = loginHelper.GetLoginRequestData();
		
		
		String xmlPostString = JacksonHelper.ToXml(loginRequest);
		System.out.println("xmlPostString : " + xmlPostString);
		Map<String, String> headers= new Hashtable<String, String>();
		headers.put("Accept", "text/xml");
		headers.put("Content-Type", "text/xml");
		//Mock Code ******  Start  *********
		WireMockTest.GetLoginPassWithFullPayload("/post/xml");
		Response response = RestAssured.given().when().log().all().accept(ContentType.XML).body(xmlPostString)
				.post("epic/post/xml");		
		//Mock Code *******  End   *********
	
		LoginResponse loginResponse=(LoginResponse) JacksonHelper.ToObject(response.asString(), new LoginResponse());
		
		System.out.println("output : " + response.asPrettyString());
		
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(loginResponse.responseCode).as("I am trying to match the values")
		.withFailMessage("Values are not matching : " + loginResponse.responseCode).isEqualTo(0);
		
		System.out.println("Check this code execute in case of failure");
		softly.assertAll();
		
		System.out.println(loginResponse.responseCode);
	
		
	}
	
	@Test(enabled= true)
	public void ValidLoginWithMockTestSample02() throws URISyntaxException {
		LoginHelper loginHelper = new LoginHelper();
		LoginRequest loginRequest = loginHelper.GetLoginRequestData();
		
		
		String xmlPostString = JacksonHelper.ToXml(loginRequest);
		System.out.println("xmlPostString : " + xmlPostString);
		Map<String, String> headers= new Hashtable<String, String>();
		headers.put("Accept", "text/xml");
		headers.put("Content-Type", "text/xml");
		//Mock Code ******  Start  *********
		WireMockTest.GetLoginPassWithPartialPayload("/post/xml");
		Response response = RestAssured.given().when().log().all().accept(ContentType.XML).body(xmlPostString)
				.post("epic/post/xml");		
		//Mock Code *******  End   *********
		LoginResponse loginResponse=(LoginResponse) JacksonHelper.ToObject(response.asString(), new LoginResponse());
		
		System.out.println("Response Body : " + response.asPrettyString());
		System.out.println("Response Code : " + response.asPrettyString());
		
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(loginResponse.responseCode).as("I am trying to match the values")
		.withFailMessage("Values are not matching : " + loginResponse.responseCode).isEqualTo(0);
		
		System.out.println("Check this code execute in case of failure");
		softly.assertAll();
		
		System.out.println(loginResponse.responseCode);
	
		
	}
}
