package TestCases;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.RestApiHelper;
import Model.RestResponse;
import ObjectRepository.PageDetails;

public class GetUnknownListTest {

	@Test
	public void validateUserList() {
		
		Map<String,String> headers= new HashMap<String,String>();
		headers.put("Accept", "application/json");
		RestResponse rest =RestApiHelper.performGetRequest("https://reqres.in/api/users?page=2",headers);
		Assertions.assertEquals(HttpStatus.SC_OK, rest.getStatusCode());
		Assertions.assertEquals(true, rest.getResponseBody().contains("total"));
		GsonBuilder builder = new GsonBuilder();
		Gson gson= builder.serializeNulls().setPrettyPrinting().create();
		PageDetails data1=gson.fromJson(rest.getResponseBody(), PageDetails.class);
	
		
		System.out.println(data1.page);
		System.out.println(data1.total_pages);
		System.out.println(data1.data.get(0).id);
		System.out.println(data1.data.get(0).email);
		
		}
}
