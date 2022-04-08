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
import Repository.DataPerPage;
import Repository.DataPerPage.UserData;
import Repository.Employee;
import Utility.DataFromExcel;
import Utility.GsonHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;

import DataHelper.EmployeeHelper;

public class TestEmployee extends BaseClass {


	@Test (enabled=false)
	public void TestEmployeeWithValidData() throws URISyntaxException, IOException {

		System.out.println("PostUserUsingObject");
		//Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		EmployeeHelper employeeHelper = new EmployeeHelper();
		//Read the test data from excel and convert into the List of Employee Object
		List<Employee> inputDataList = employeeHelper.GetEmployeeFromExcel();
		
		int inputDataRowsCount = inputDataList.size();
		List<Employee> outputDataList = new ArrayList<Employee>();
		Employee outputData = null;
		//Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			//Serialize the employee object to json
			String jsonRequestData = employeeHelper.GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			//Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			//De-Serialize the json into the employee object
			outputData = employeeHelper.GetJsonToEmployee(responseData.asString());
			//Add the employee data in list 
			outputDataList.add(outputData);
		}
		
		//write the input and output with comparision in output file
		employeeHelper.WriteOutput(inputDataList, outputDataList);

	}
	
	@Test (enabled=true)
	public void TestListPerPage() throws URISyntaxException, IOException {

		System.out.println("TestListPerPage");
		//Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		EmployeeHelper employeeHelper = new EmployeeHelper();
		//Read the test data from excel and convert into the List of Employee Object
		List<DataPerPage> inputDataList = employeeHelper.GetPerDataList();
		System.out.println(inputDataList.get(0).per_page);
		System.out.println(inputDataList.get(0).data.get(0).id);
		System.out.println(inputDataList.get(0).support.scenario);
		
		
		List<UserData> data= inputDataList.get(0).data;
		System.out.println(data.get(0).id);
		DataPerPage outputData =new DataPerPage();
		Response responseData = RestAPiHelper.GetResponse("users?page=2");
		List<DataPerPage> outputDataList = new ArrayList<DataPerPage>();
		outputData =(DataPerPage) GsonHelper.ToObject(responseData.asString(), outputData);
		outputDataList.add(outputData);
		
		System.out.println(outputData.per_page);
		System.out.println(outputData.data.get(0).id);
		System.out.println(outputData.support.url);
		
		/*int inputDataRowsCount = inputDataList.size();
		List<Employee> outputDataList = new ArrayList<Employee>();
		Employee outputData = null;
		//Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			//Serialize the employee object to json
			String jsonRequestData = employeeHelper.GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			//Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			//De-Serialize the json into the employee object
			outputData = employeeHelper.GetJsonToEmployee(responseData.asString());
			//Add the employee data in list 
			outputDataList.add(outputData);
		}
		
		//write the input and output with comparision in output file*/
		//employeeHelper.WriteOutput(inputDataList, outputDataList);
		employeeHelper.WriteOutputDataPerPageValidData(inputDataList, outputDataList, null);
	}



}