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

public class DeleteUserTest {

	@Test
	public void deleteUserList() {
		
		
		RestResponse rest =RestApiHelper.performGetRequest("https://reqres.in/api/users/2",null);
		System.out.println(rest.getStatusCode());
		System.out.println(rest.getResponseBody());
		Assertions.assertEquals(HttpStatus.SC_OK, rest.getStatusCode());
		Assertions.assertEquals(true, rest.getResponseBody().contains("first_name"));
		
		}
}
