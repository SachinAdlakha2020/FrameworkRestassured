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

import javax.print.attribute.HashAttributeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.Employee;
import Repository.RegisterEmployee;
import Utility.DataFromExcel;
import Utility.GsonHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DataHelper.EmployeeHelper;

public class TestCode extends BaseClass {

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
	public void PostUserUsingStringObject() throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		EmployeeHelper employeeHelper = new EmployeeHelper();
		String json = employeeHelper.GetEmployeeToJson();
		System.out.println(json);
		// Call the post request
		Response response = RestAPiHelper.PostRequest("users", headers, json);

		Employee data = employeeHelper.GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	@Test(enabled = false)
	public void PostUserUsingExcel() throws URISyntaxException, IOException {

		System.out.println("PostUserUsingObject");
		// Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		EmployeeHelper employeeHelper = new EmployeeHelper();
		// Read the test data from excel and convert into the List of Employee Object
		List<Employee> inputDataList = employeeHelper.GetEmployeeFromExcel();

		int inputDataRowsCount = inputDataList.size();
		List<Employee> outputDataList = new ArrayList<Employee>();
		Employee outputData = null;
		// Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			// Serialize the employee object to json
			String jsonRequestData = employeeHelper.GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			// Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			// De-Serialize the json into the employee object
			outputData = employeeHelper.GetJsonToEmployee(responseData.asString());
			// Add the employee data in list
			outputDataList.add(outputData);
		}

		// write the input and putput with comparision in output file
		employeeHelper.WriteOutput(inputDataList, outputDataList);

	}

	@Test(enabled = false)
	public void PostUserUsingObject() throws URISyntaxException {

		System.out.println("PostUserUsingExcel");
		Map<String, String> headers = new Hashtable<>();
		// Object object = (Object)GetEmployeeObject();
		EmployeeHelper employeeHelper = new EmployeeHelper();
		Response response = RestAPiHelper.PostRequestAsObject("users", headers, employeeHelper.GetEmployeeObject());

		Employee data = employeeHelper.GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	@Test (enabled=false )
	public void checkSerialization() {
		RegisterEmployee employee = new RegisterEmployee();
		employee.scenario = "Added Scenario";
		employee.email = "sachin.adlakha@xeeva.com";
		employee.password = "Xeeva123";
		employee.id = 4;
		employee.token = "28789-49823984-949";
		employee.error = "No error has come";

		/*Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		String toJson = gson.toJson(employee);
		System.out.println(toJson);*/
		
		String toJson1=GsonHelper.ToJson(employee);
		
		System.out.println(toJson1);
		
		
		RegisterEmployee employee1 = new RegisterEmployee();
		employee1= (RegisterEmployee) GsonHelper.ToObject(toJson1, employee1);
		
		
		//gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		/*gson = new Gson();
		employee1 = gson.fromJson(toJson, RegisterEmployee.class);*/
		System.out.println(employee1.scenario);
		System.out.println(employee1.email);
		System.out.println(employee1.password);
		System.out.println(employee1.id);
		System.out.println(employee1.token);
		System.out.println(employee1.error);

	}
	@Test (enabled=true )
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

}