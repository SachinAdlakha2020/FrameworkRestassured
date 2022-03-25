package TestSuiteEmployee;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.EmployeeData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson; 
public class PostEmployee extends BaseClass {

	@Test( enabled=false )
	public void PostUserUsingStringJson() throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		String json = "{\"name\": \"morpheus\",\"job\": \"leader" + "\"}";
		System.out.println(json);

		Response response = RestAPiHelper.PostRequest("users", headers, json);
	}

	@Test( enabled=false )
	public void PostUserUsingStringObject() throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		String json = GetEmployeeToJson();
		System.out.println(json);
		
		Response response = RestAPiHelper.PostRequest("users", headers, json);
		
		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);
		
	}
	
	@Test
	public void PostUserUsingObject() throws URISyntaxException {
		
		System.out.println("PostUserUsingObject");
		Map<String, String> headers = new Hashtable<>();
		//Object object = (Object)GetEmployeeObject();
		
		Response response = RestAPiHelper.PostRequestAsObject("users", headers,GetEmployeeObject());
		
		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);
		
	}
	
	private EmployeeData GetEmployeeObject() {
		EmployeeData data = new EmployeeData();
		data.name="Sachin Adlakha";
		data.job = "QA Manager";
		return data;
	}

	private String GetEmployeeToJson() {
		EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString =  gson.toJson(data, EmployeeData.class);
		return jsonString;

	}

	private EmployeeData GetJsonToEmployee(String jsonString) {
		Gson gson = new Gson();
		EmployeeData data = gson.fromJson(jsonString, EmployeeData.class);
		return data;

	}

}
