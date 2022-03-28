package TestSuiteEmployee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

		List<EmployeeData> inputData = DataList();
		String jsonRequestData = GetEmployeeToJson(inputData.get(0));

		Response responseData = RestAPiHelper.PostRequest("users", headers, jsonRequestData);

		EmployeeData outputData = GetJsonToEmployee(responseData.asString());
		System.out.println("Id: " + outputData.id);
		System.out.println("CreatedAt: " + outputData.createdAt);
		System.out.println("Compare Objects: " + inputData.get(0).equals(outputData));

		WriteOutput(inputData.get(0), outputData);

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

			for (Object[] objects : obj) {
				EmployeeData data = new EmployeeData();
				data.name = (String) obj[0][0];
				data.job = (String) obj[0][1];
				listData.add(data);
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

	private void WriteOutput(EmployeeData inputData, EmployeeData outputData) {

		String outputFilePath = "E:\\Automation\\TestData\\Output\\TestDataInExcel.xlsx";
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		for (int row = 0; row <= 1; row++) {

			Row rows = sheet.createRow(row);
			int cellCount = 0;
			Cell cells = null;
			// Compare the outcome and store in excel
			String result = "true";
			System.out.println("Rows Count: " + row);
			if (inputData.name.equals(outputData.name)) {
				result = "Pass";
				cellCount = AddCells(rows, cells, cellCount, inputData.name, outputData.name, result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = AddCells(rows, cells, cellCount, inputData.name, outputData.name, result);
				System.out.println("Cells Count: " + cellCount);
			}			
			if (inputData.job.equals(outputData.job)) {
				result = "Pass";
				cellCount = AddCells(rows, cells, cellCount, inputData.job, outputData.job, result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "fail";
				cellCount = AddCells(rows, cells, cellCount, inputData.job, outputData.job, result);
				System.out.println("Cells Count: " + cellCount);
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(outputFilePath));
			workbook.write(out);
			out.close();
			System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int AddCells(Row rows, Cell cells, int cellCount, String inputValue, String outputValue, String result) {
		System.out.println("name: " + result);
		cells = rows.createCell(cellCount);
		cells.setCellValue(inputValue);
		cells = rows.createCell(++cellCount);
		cells.setCellValue(outputValue);
		cells = rows.createCell(++cellCount);
		cells.setCellValue(result);
		++cellCount;
		return cellCount;
	}
}