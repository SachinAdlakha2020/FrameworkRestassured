package TestCases;

import java.io.IOException;



import Model.RestApiHelper;
import Model.RestResponse;

public class GetRequest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
			RestResponse rest= RestApiHelper.performGetRequest("https://reqres.in/api/unknown",null);
			System.out.println(rest.getStatusCode());
			System.out.println(rest.getResponseBody());
		}
	}

