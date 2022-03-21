package TestCases;

import com.google.gson.Gson;

import ObjectRepository.EmployeeFullInformation;
import ObjectRepository.EmployeeInformation;
import ObjectRepository.SupportInformation;

public class CreateJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Added comments.
		Gson gson = new Gson();
		EmployeeInformation dat= new EmployeeInformation(1, "Sadlakha@Xeeva.com", "Sachin", "Adlakha", "Avatar");
		SupportInformation support = new SupportInformation("xeeva.com", "Works on Spend Data");
		EmployeeFullInformation emp = new EmployeeFullInformation();
		emp.emp = dat;
		emp.support =  support;
		String createJson = gson.toJson(emp);
		System.out.println(createJson);
	}
	
}