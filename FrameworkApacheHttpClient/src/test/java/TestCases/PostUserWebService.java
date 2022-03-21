package TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import Model.RestApiHelper;
import Model.RestResponse;
import ObjectRepository.PageDetails;
import ObjectRepository.UserDetail;
import Utility.DataFromExcel;
import Utility.URLs;

public class PostUserWebService {
	
	@Test
	public void postData() throws IOException {
		//String Url = "https://reqres.in/api/users";
		//String content = "{\"name\": \"Sachin\",\"job\": \"QA\"}";
		
		Object[][] data =DataFromExcel.ReadDataFromExcel("C:\\Users\\Dell\\Downloads\\TestDataInExcel.xlsx");
		
		Map<String, String> headers = new Hashtable<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		String content = getJson(data);
		
		RestResponse restResponse = RestApiHelper.performPostRequest(URLs.POST_USERS, content, headers);
		
		System.out.println(restResponse.getStatusCode());
		System.out.println(restResponse.getResponseBody());
		
	}
	
	private String getJson(Object[][] data) {
		
		//Object[][] data=ReadDataFromExcel();
		int value = data.length;
		ArrayList<UserDetail> arrUserDetails = new ArrayList<>();
		UserDetail details = null;
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			details = new UserDetail();
			for (int j = 0; j < objects.length; j++) {
				System.out.println(objects[j]);
				
				switch (j) {
				case 0:
					details.name =  (String) objects[j];
					break;
				case 1:
					details.job =  (String) objects[j];
					break;
				/*case 2:
					details.id =  (Double) objects[j];
					break;*/

				default:
					break;
				}
					
			}
			arrUserDetails.add(details);
		}		
		Gson json = new Gson();
		String jsonString = json.toJson(details);
		System.out.println(jsonString);
		return jsonString;
		
	}

}
