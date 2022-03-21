package TestCases;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import Model.RestApiHelper;
import Model.RestResponse;
import ObjectRepository.UserDetail;

public class PutUpdateUser {

	
	
	@Test
	public void PutData() {
		String Url = "https://reqres.in/api/users";
		UserDetail detail=new UserDetail();
		detail.name ="Sachin";
		detail.job ="QA";
		//detail.id=133;
		Gson gsonObj=new Gson();
		String content = gsonObj.toJson(detail);
		 //content = "{\"name\": \"Sachin\",\"job\": \"QA\"}";
		
		Map<String, String> headers = new Hashtable<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RestResponse restResponse = RestApiHelper.performPutRequest(Url, content, headers);
		
		System.out.println(restResponse.getStatusCode());
		System.out.println(restResponse.getResponseBody());
		detail = gsonObj.fromJson(restResponse.getResponseBody(), UserDetail.class);
		
		System.out.println(detail.name);
		System.out.println(detail.job);
		//System.out.println(detail.id);
		//System.out.println(detail.createdAt);
		
		
													}
}
