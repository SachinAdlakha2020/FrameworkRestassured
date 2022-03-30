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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import Model.BaseClass;
import Model.RestAPiHelper;
import Repository.EmployeeData;
import Utility.DataFromExcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;

import com.google.gson.Gson;

public class PostEmployee extends BaseClass {

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
	public void PostUserUsingStringObject() throws URISyntaxException {
		Map<String, String> headers = new Hashtable<>();
		String json = GetEmployeeToJson();
		System.out.println(json);
		// Call the post request
		Response response = RestAPiHelper.PostRequest("users", headers, json);

		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	@Test
	public void PostUserUsingExcel() throws URISyntaxException, IOException {

		System.out.println("PostUserUsingObject");
		Map<String, String> headers = new Hashtable<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		List<EmployeeData> inputDataList = DataList();
		int inputDataRowsCount = inputDataList.size();
		List<EmployeeData> outputDataList = new ArrayList<EmployeeData>();
		EmployeeData outputData = null;
		for (int i = 0; i < inputDataRowsCount; i++) {
			String jsonRequestData = GetEmployeeToJson(inputDataList.get(i));
			System.out.println("Json Request: " + jsonRequestData);
			Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);
			outputData = GetJsonToEmployee(responseData.asString());
			outputDataList.add(outputData);
		}

		WriteOutput(inputDataList, outputDataList);

	}

	@Test(enabled = false)
	public void PostUserUsingObject() throws URISyntaxException {

		System.out.println("PostUserUsingExcel");
		Map<String, String> headers = new Hashtable<>();
		// Object object = (Object)GetEmployeeObject();

		Response response = RestAPiHelper.PostRequestAsObject("users", headers, GetEmployeeObject());

		EmployeeData data = GetJsonToEmployee(response.asString());
		System.out.println("Id: " + data.id);
		System.out.println("CreatedAt: " + data.createdAt);

	}

	private EmployeeData GetEmployeeObject() {
		EmployeeData data = new EmployeeData();
		data.name = "Sachin Adlakha";
		data.job = "QA Manager";
		return data;
	}

	private String GetEmployeeToJson() {
		EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, EmployeeData.class);
		return jsonString;

	}

	private String GetEmployeeToJson(EmployeeData data) {
		// EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, EmployeeData.class);
		return jsonString;

	}

	private EmployeeData GetJsonToEmployee(String jsonString) {
		Gson gson = new Gson();
		EmployeeData data = gson.fromJson(jsonString, EmployeeData.class);
		return data;

	}

	private List<EmployeeData> DataList() throws IOException {

		List<EmployeeData> listData = null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\TestDataInExcel.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			// Assign the data in Object
			listData = new ArrayList<EmployeeData>();
			int row = 0;
			for (Object[] objects : obj) {
				EmployeeData data = new EmployeeData();
				data.name = (String) obj[row][0];
				System.out.println("data.name: " + data.name);
				data.job = (String) obj[row][1];
				listData.add(data);
				++row;
			}

			/*
			 * EmployeeData data=new EmployeeData(); data.name= (String)obj[0][0]; data.job=
			 * (String)obj[0][1];
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;

	}

	private void WriteOutput(List<EmployeeData> inputDataList, List<EmployeeData> outputDataList) {

		//Creating the unique file name with current date and time.
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String currentDateTime = formatter.format(date);
		String fileName = "EmployeeResults_" +  currentDateTime + ".xlsx";
		String outputFilePath = "E:\\Automation\\TestData\\Output\\" + fileName;
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		Row rows = null;

		Cell cells = null;
		int rowsSize = inputDataList.size();

		if (rowsSize > 0) {
			rows = sheet.createRow(0);
			DataFromExcel.AddHeader(rows, cells, 0,GetHeadersList());
		}

		for (int row = 0; row < rowsSize; row++) {

			System.out.println("Rows Count: " + row);
			rows = sheet.createRow(row + 1);

			int cellCount = 0;
			// Compare the outcome and store in excel
			String result = "true";

			if (inputDataList.get(row).name.equals(outputDataList.get(row).name)) {
				result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).name, outputDataList.get(row).name,
						result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).name, outputDataList.get(row).name,
						result);
				System.out.println("Cells Count: " + cellCount);
			}
			if (inputDataList.get(row).job.equals(outputDataList.get(row).job)) {
				result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).job, outputDataList.get(row).job,
						result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).job, outputDataList.get(row).job,
						result);
				System.out.println("Cells Count: " + cellCount);
			}
		}

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(outputFilePath));
			workbook.write(out);
			out.close();
			System.out.println("Outputfile written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<String> GetHeadersList() {
		ArrayList<String> headersList=new ArrayList<String>();
		headersList.add("Actual Name");headersList.add("Expected Name");headersList.add("Result Name");
		headersList.add("Actual Job");headersList.add("Expected Job");headersList.add("Result Job");
		return headersList;
	}	
	
	
}