package TestSuiteEmployee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.print.attribute.HashAttributeSet;
import javax.xml.crypto.Data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.DataPerPage;

import Repository.Employee;
import Repository.LoginRequest;
import Repository.LoginResponse;
import Repository.Order;
import Repository.Order.Note;
import Repository.OrderFailResponse;
import Repository.OrderFailResponse.Errors;
import Repository.RegisterEmployee;
import Utility.DataFromExcel;
import Utility.GsonHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.internal.mapping.Jackson1Mapper;
import io.restassured.response.Response;
import mockService.WireMockTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Metadata;
import com.github.tomakehurst.wiremock.common.Metadata.Builder;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.trafficlistener.WiremockNetworkTrafficListener;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.ValueMatcher;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.relevantcodes.extentreports.LogStatus;

import DataHelper.EmployeeHelper;

public class TestCode extends WireMockTest {

	@Test(enabled = false)
	public void PostUserUsingStringJson() throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		String json = "{\"name\": \"morpheus\",\"job\": \"leader" + "\"}";
		System.out.println(json);

		Response response = RestAPiHelper.PostRequest("users", headers, json);
	}

	@Test(enabled = false)
	public void checkSerialization() {
		RegisterEmployee employee = new RegisterEmployee();
		employee.scenario = "Added Scenario";
		employee.email = "sachin.adlakha@xeeva.com";
		employee.password = "Xeeva123";
		employee.id = 4;
		employee.token = "28789-49823984-949";
		employee.error = "No error has come";

		/*
		 * Gson gson = new
		 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
		 * create(); String toJson = gson.toJson(employee); System.out.println(toJson);
		 */

		String toJson1 = GsonHelper.ToJson(employee);

		System.out.println(toJson1);

		RegisterEmployee employee1 = new RegisterEmployee();
		employee1 = (RegisterEmployee) GsonHelper.ToObject(toJson1, employee1);

		// gson = new
		// GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		/*
		 * gson = new Gson(); employee1 = gson.fromJson(toJson, RegisterEmployee.class);
		 */
		System.out.println(employee1.scenario);
		System.out.println(employee1.email);
		System.out.println(employee1.password);
		System.out.println(employee1.id);
		System.out.println(employee1.token);
		System.out.println(employee1.error);

	}

	@Test(enabled = false)
	private void GetUserDirectory() {
		System.out.println(System.getProperty("user.dir"));
	}

	/*
	 * public String ToJson(Object data) { // EmployeeData data =
	 * GetEmployeeObject(); Gson gson = new
	 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
	 * create(); String jsonString = gson.toJson(data, Object.class);
	 * System.out.println("jsonString: " + jsonString); return jsonString;
	 * 
	 * }
	 * 
	 * public Object ToObject(String jsonString,Object object) { Gson gson = new
	 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
	 * create(); object = gson.fromJson(jsonString, object.getClass()); return
	 * object;
	 * 
	 * }
	 */
	@Test(enabled = false)
	private void TestReports() {
		Reports.logger = Reports.extent.startTest("Test Name");
		// Reports.logger.
		Reports.logger.log(LogStatus.PASS, "Test Step 1");
		Reports.logger.log(LogStatus.PASS, "Test Step 2");
		Reports.logger.log(LogStatus.PASS, "Test Step 3");
	}

	@Test(enabled = false)
	private void TestDataObject() {
		DataPerPage dataPerPage = new DataPerPage();
		dataPerPage.page = 2;
		dataPerPage.per_page = 5;
		dataPerPage.total = 40;
		dataPerPage.total_pages = 8;

		DataPerPage.UserData userData = dataPerPage.new UserData();
		userData.id = 4;
		userData.email = "sachin.adlakha@xeeva.com";
		userData.first_name = "Sachin";
		userData.last_name = "Adlakha";
		userData.avatar = "https://avatar.atlassian.net/browse/PS-2844";

		DataPerPage.UserData userData1 = dataPerPage.new UserData();
		userData1.id = 4;
		userData1.email = "sachin.adlakha@xeeva.com";
		userData1.first_name = "Sachin";
		userData1.last_name = "Adlakha";
		userData1.avatar = "https://avatar.atlassian.net/browse/PS-2844";

		ArrayList<DataPerPage.UserData> dataList = new ArrayList<>();
		dataList.add(userData);
		dataList.add(userData1);

		dataPerPage.data = dataList;

		DataPerPage.Support sup = dataPerPage.new Support();
		sup.url = "https://url.atlassian.net/browse/PS-2844";
		sup.text = "support text";

		dataPerPage.support = sup;

		// dataPerPage.userData.add(null)

		GsonHelper.ToJson(dataPerPage);

	}
	
	@Test(enabled = false)
	private void TestOrderXml() throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		Order order =new Order();
		order.ignoreOrdering="Y";
		order.orderHeaderKey=(float) 480989081;
		
		ArrayList<Note> noteList= new ArrayList<Note>();
		Note notes=new Note();
		notes.noteText= "Added Notes";
		notes.operation ="Added Operation";
		noteList.add(notes);
		order.Note=noteList;		
		String xmlString= mapper.writeValueAsString(order);		
		System.out.println(xmlString);		
	}
	
	@Test(enabled = false)
	private void TestOrderFailResponseXml() throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		OrderFailResponse orderFail =new OrderFailResponse();
		orderFail.orderHeaderKey=(float) 480989081;
		
		ArrayList<Errors> errorList= new ArrayList<Errors>();
		Errors error=new Errors();
		error.message= "Order is not get created";
		
		errorList.add(error);
		orderFail.errorList=errorList;
		String xmlString= mapper.writeValueAsString(orderFail);		
		System.out.println(xmlString);		
	}
	
	@Test(enabled = true)
	private void TestLoginRequestXml() throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.login ="loginText";
		loginRequest.password ="loginPassword";
		
		String xmlString= mapper.writeValueAsString(loginRequest);		
		System.out.println(xmlString);	
		LoginRequest loginRequestDe= mapper.readValue(xmlString, LoginRequest.class);
		System.out.println(loginRequestDe.login);
	}
	
	@Test(enabled = true)
	private void TestLoginResponeXml() throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.responseCode =100;
		loginResponse.responseMessage ="Response Message";
		
		String xmlString= mapper.writeValueAsString(loginResponse);		
		System.out.println(xmlString);		
	}
	
	@Test(enabled = false)
	public void getMockCreateOrderSuccessTest() throws JsonMappingException, JsonProcessingException {
		System.out.println("i am getMockCreateOrderSuccessTest()");
		
		WireMockTest.getOrderSuccess();
		Response response = RestAssured.given().accept(ContentType.XML).when().get("/created");
		XmlMapper mapper = new XmlMapper();
		Order order=mapper.readValue(response.asString(), Order.class);		
		Assert.assertEquals(order.Note.get(0).noteText, "Test debit memo-1");
		System.out.println(order.orderHeaderKey);
		System.out.println(order.Note.get(0).noteText);
		System.out.println(response.getStatusCode());	
		System.out.println(response.asString());
		
	}
	
	@Test(enabled = false)
	public void getMockUpdateOrderFailureTest() throws JsonMappingException, JsonProcessingException {
		System.out.println("i am getMockUpdateOrderFailureTest()");
		
		WireMockTest.getOrderFailure();
		Response response = RestAssured.given().accept(ContentType.XML).when().get("/updated");
		XmlMapper mapper = new XmlMapper();
		OrderFailResponse orderFailResponse=mapper.readValue(response.asString(), OrderFailResponse.class);		
		Assert.assertEquals(orderFailResponse.errorList.get(0).message, "Order is not get created");
		System.out.println(orderFailResponse.orderHeaderKey);
		System.out.println(response.getStatusCode());	
		System.out.println(response.asString());
		
	}
	

	
}