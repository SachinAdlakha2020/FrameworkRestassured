package TestSuiteEmployee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.print.attribute.HashAttributeSet;
import javax.xml.crypto.Data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.DataPerPage;

import Repository.Employee;
import Repository.RegisterEmployee;
import Utility.DataFromExcel;
import Utility.GsonHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.relevantcodes.extentreports.LogStatus;

import DataHelper.EmployeeHelper;

public class TestCode {

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
	public void checkSerialization() {
		RegisterEmployee employee = new RegisterEmployee();
		employee.scenario = "Added Scenario";
		employee.email = "sachin.adlakha@xeeva.com";
		employee.password = "Xeeva123";
		employee.id = 4;
		employee.token = "28789-49823984-949";
		employee.error = "No error has come";

		/*
		 * Gson gson = new
		 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
		 * create(); String toJson = gson.toJson(employee); System.out.println(toJson);
		 */

		String toJson1 = GsonHelper.ToJson(employee);

		System.out.println(toJson1);

		RegisterEmployee employee1 = new RegisterEmployee();
		employee1 = (RegisterEmployee) GsonHelper.ToObject(toJson1, employee1);

		// gson = new
		// GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		/*
		 * gson = new Gson(); employee1 = gson.fromJson(toJson, RegisterEmployee.class);
		 */
		System.out.println(employee1.scenario);
		System.out.println(employee1.email);
		System.out.println(employee1.password);
		System.out.println(employee1.id);
		System.out.println(employee1.token);
		System.out.println(employee1.error);

	}

	@Test(enabled = false)
	private void GetUserDirectory() {
		System.out.println(System.getProperty("user.dir"));
	}

	/*
	 * public String ToJson(Object data) { // EmployeeData data =
	 * GetEmployeeObject(); Gson gson = new
	 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
	 * create(); String jsonString = gson.toJson(data, Object.class);
	 * System.out.println("jsonString: " + jsonString); return jsonString;
	 * 
	 * }
	 * 
	 * public Object ToObject(String jsonString,Object object) { Gson gson = new
	 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
	 * create(); object = gson.fromJson(jsonString, object.getClass()); return
	 * object;
	 * 
	 * }
	 */
	@Test(enabled = false)
	private void TestReports() {
		Reports.logger = Reports.extent.startTest("Test Name");
		// Reports.logger.
		Reports.logger.log(LogStatus.PASS, "Test Step 1");
		Reports.logger.log(LogStatus.PASS, "Test Step 2");
		Reports.logger.log(LogStatus.PASS, "Test Step 3");
	}

	@Test
	private void TestDataObject() {
		DataPerPage dataPerPage = new DataPerPage();
		dataPerPage.page = 2;
		dataPerPage.per_page = 5;
		dataPerPage.total = 40;
		dataPerPage.total_pages = 8;

		DataPerPage.UserData userData = dataPerPage.new UserData();
		userData.id = 4;
		userData.email = "sachin.adlakha@xeeva.com";
		userData.first_name = "Sachin";
		userData.last_name = "Adlakha";
		userData.avatar = "https://avatar.atlassian.net/browse/PS-2844";

		DataPerPage.UserData userData1 = dataPerPage.new UserData();
		userData1.id = 4;
		userData1.email = "sachin.adlakha@xeeva.com";
		userData1.first_name = "Sachin";
		userData1.last_name = "Adlakha";
		userData1.avatar = "https://avatar.atlassian.net/browse/PS-2844";

		ArrayList<DataPerPage.UserData> dataList = new ArrayList<>();
		dataList.add(userData);
		dataList.add(userData1);

		dataPerPage.data = dataList;

		DataPerPage.Support sup = dataPerPage.new Support();
		sup.url = "https://url.atlassian.net/browse/PS-2844";
		sup.text = "support text";

		dataPerPage.support = sup;

		// dataPerPage.userData.add(null)

		GsonHelper.ToJson(dataPerPage);

	}
}