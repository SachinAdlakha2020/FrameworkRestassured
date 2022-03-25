package TestSuiteEmployee;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.EmployeeData;
import Utility.DataFromExcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;

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

		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	@Test
	public void PostUserUsingExcel() throws URISyntaxException, IOException {

		System.out.println("PostUserUsingObject");
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		List<EmployeeData> inputData = DataList();
		String json = GetEmployeeToJson(inputData.get(0));
		
		Response response = RestAPiHelper.PostRequest("users", headers, json);

		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}
	
	@Test(enabled = false)
	public void PostUserUsingObject() throws URISyntaxException {

		System.out.println("PostUserUsingExcel");
		Map<String, String> headers = new Hashtable<>();
		// Object object = (Object)GetEmployeeObject();

		Response response = RestAPiHelper.PostRequestAsObject("users", headers, GetEmployeeObject());

		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	private EmployeeData GetEmployeeObject() {
		EmployeeData data = new EmployeeData();
		data.name = "Sachin Adlakha";
		data.job = "QA Manager";
		return data;
	}

	private String GetEmployeeToJson() {
		EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, EmployeeData.class);
		return jsonString;

	}
	
	
	private String GetEmployeeToJson(EmployeeData data) {
		//EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, EmployeeData.class);
		return jsonString;

	}

	private EmployeeData GetJsonToEmployee(String jsonString) {
		Gson gson = new Gson();
		EmployeeData data = gson.fromJson(jsonString, EmployeeData.class);
		return data;

	}

	private List<EmployeeData> DataList() throws IOException {
		
		List<EmployeeData> listData =null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\TestDataInExcel.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			
			// Assign the data in Object
			listData = new ArrayList<EmployeeData>();

			for (Object[] objects : obj) {
				EmployeeData data = new EmployeeData();
				data.name = (String) obj[0][0];
				data.job = (String) obj[0][1];
				listData.add(data);
			}

			/*
			 * EmployeeData data=new EmployeeData(); data.name= (String)obj[0][0]; data.job=
			 * (String)obj[0][1];
			 */

		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return listData;

	}
}
