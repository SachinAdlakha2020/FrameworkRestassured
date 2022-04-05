package TestSuiteEmployee;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import DataHelper.RegisterEmployeeHelper;
import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.Employee;
import Repository.RegisterEmployee;
import Utility.GsonHelper;
import io.restassured.response.Response;

public class TestRegisterEmployee  extends BaseClass {
	@Test (enabled=true)
	public void TestRegisterEmployeeWithValidData() throws URISyntaxException, IOException {

		
		Reports.logger = Reports.extent.startTest("Test Register employee with valid data");		
			
		System.out.println("TestRegisterEmployeeWithValidData");
		//Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RegisterEmployeeHelper regEmpHelper = new RegisterEmployeeHelper();
		
		//Read the test data from excel and convert into the List of Register Employee Object
		List<RegisterEmployee> inputDataList =regEmpHelper.GetRegisterEmpValidData();
		
		int inputDataRowsCount = inputDataList.size();
		List<RegisterEmployee> outputDataList = new ArrayList<RegisterEmployee>();
		Repository.RegisterEmployee outputData = null;
		//Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			//Serialize the employee object to json
			String jsonRequestData = GsonHelper.ToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			//Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("register", headers, jsonRequestData);
			//De-Serialize the json into the employee object
			outputData = new RegisterEmployee();
			outputData = (RegisterEmployee) GsonHelper.ToObject(responseData.asString(), outputData);
			//Add the employee data in list 
			outputDataList.add(outputData);
		}
		
		//write the input and output with comparision in output file
		regEmpHelper.WriteOutputValidData(inputDataList, outputDataList);
		Reports.logger.log(LogStatus.PASS, "Test Register employee with valid data");
		
	}
	
	@Test (enabled=true)
	public void TestRegisterEmployeeWithInValidData() throws URISyntaxException, IOException {

		Reports.logger = Reports.extent.startTest("Test Register employee with invalid data");	
		
		//Reports.endReport();
		System.out.println("TestRegisterEmployeeWithInvaliValidData");
		//Create the header for request
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RegisterEmployeeHelper regEmpHelper = new RegisterEmployeeHelper();
		
		//Read the test data from excel and convert into the List of Register Employee Object
		List<RegisterEmployee> inputDataList =regEmpHelper.GetRegisterEmpInvalidData();
		
		int inputDataRowsCount = inputDataList.size();
		List<RegisterEmployee> outputDataList = new ArrayList<RegisterEmployee>();
		RegisterEmployee outputData = null;
		//Iterate through all the rows
		for (int i = 0; i < inputDataRowsCount; i++) {
			//Serialize the employee object to json
			String jsonRequestData = GsonHelper.ToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			//Call the api and read the response data
			Response responseData = RestAPiHelper.PostRequest("register", headers, jsonRequestData);
			//De-Serialize the json into the employee object
			outputData = new RegisterEmployee();
			outputData = (RegisterEmployee) GsonHelper.ToObject(responseData.asString(), outputData);
			//Add the employee data in list 
			outputDataList.add(outputData);
		}		
		//write the input and output with comparision in output file
		regEmpHelper.WriteOutputInvalidData(inputDataList, outputDataList);
		Reports.logger.log(LogStatus.PASS, "Test Register employee with invalid data");
	}
}
