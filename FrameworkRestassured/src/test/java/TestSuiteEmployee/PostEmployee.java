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
import Utility.DataFromExcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;

import DataHelper.EmployeeHelper;

public class PostEmployee extends BaseClass {

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

	@Test
	public void PostUserUsingExcel() throws URISyntaxException, IOException {

		System.out.println("PostUserUsingObject");
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		EmployeeHelper employeeHelper = new EmployeeHelper();
		List<Employee> inputDataList = employeeHelper.GetEmployeeFromExcel();
		int inputDataRowsCount = inputDataList.size();
		List<Employee> outputDataList = new ArrayList<Employee>();
		Employee outputData = null;
		for (int i = 0; i < inputDataRowsCount; i++) {
			String jsonRequestData = employeeHelper.GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			outputData = employeeHelper.GetJsonToEmployee(responseData.asString());
			outputDataList.add(outputData);
		}

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



	
	
	
}