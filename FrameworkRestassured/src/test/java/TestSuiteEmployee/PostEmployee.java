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
		String json = GetEmployeeToJson();
		System.out.println(json);
		// Call the post request
		Response response = RestAPiHelper.PostRequest("users", headers, json);

		Employee data = GetJsonToEmployee(response.asString());
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
		List<Employee> inputDataList = GetEmployeeFromExcel();
		int inputDataRowsCount = inputDataList.size();
		List<Employee> outputDataList = new ArrayList<Employee>();
		Employee outputData = null;
		for (int i = 0; i < inputDataRowsCount; i++) {
			String jsonRequestData = GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			outputData = GetJsonToEmployee(responseData.asString());
			outputDataList.add(outputData);
		}

		employeeHelper.WriteOutput(inputDataList, outputDataList);

	}

	@Test(enabled = false)
	public void PostUserUsingObject() throws URISyntaxException {

		System.out.println("PostUserUsingExcel");
		Map<String, String> headers = new Hashtable<>();
		// Object object = (Object)GetEmployeeObject();

		Response response = RestAPiHelper.PostRequestAsObject("users", headers, GetEmployeeObject());

		Employee data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	private Employee GetEmployeeObject() {
		Employee data = new Employee();
		data.name = "Sachin Adlakha";
		data.job = "QA Manager";
		return data;
	}

	private String GetEmployeeToJson() {
		Employee data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, Employee.class);
		return jsonString;

	}

	private String GetEmployeeToJson(Employee data) {
		// EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, Employee.class);
		return jsonString;

	}

	private Employee GetJsonToEmployee(String jsonString) {
		Gson gson = new Gson();
		Employee data = gson.fromJson(jsonString, Employee.class);
		return data;

	}

	private List<Employee> GetEmployeeFromExcel() throws IOException {

		List<Employee> listData = null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\TestDataInExcel.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			// Assign the data in Object
			listData = new ArrayList<Employee>();
			int row = 0;
			for (Object[] objects : obj) {
				Employee data = new Employee();
				data.name = (String) obj[row][0];
				System.out.println("data.name: " + data.name);
				data.job = (String) obj[row][1];
				listData.add(data);
				++row;
			}

			/*
			 * EmployeeData data=new EmployeeData(); data.name= (String)obj[0][0]; data.job=
			 * (String)obj[0][1];
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;

	}

	
	
}