package TestSuiteEmployee;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import DataHelper.RegisterEmployeeHelper;
import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.Employee;
import Repository.RegisterEmployee;
import io.restassured.response.Response;

public class TestRegisterEmployee  extends BaseClass {
	@Test
	public void TestRegisterEmployeeWithValidData() throws URISyntaxException, IOException {

		System.out.println("TestRegisterEmployeeWithValidData");
		//Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RegisterEmployeeHelper regEmpHelper = new RegisterEmployeeHelper();
		
		//Read the test data from excel and convert into the List of Register Employee Object
		List<RegisterEmployee> inputDataList =regEmpHelper.GetRegisterEmployeeExcel();
		
		int inputDataRowsCount = inputDataList.size();
		List<RegisterEmployee> outputDataList = new ArrayList<RegisterEmployee>();
		Repository.RegisterEmployee outputData = null;
		//Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			//Serialize the employee object to json
			String jsonRequestData = regEmpHelper.ToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			//Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("register", headers, jsonRequestData);
			//De-Serialize the json into the employee object
			outputData = regEmpHelper.ToObject(responseData.asString());
			//Add the employee data in list 
			outputDataList.add(outputData);
		}
		
		//write the input and output with comparision in output file
		regEmpHelper.WriteOutput(inputDataList, outputDataList);

	}
}
