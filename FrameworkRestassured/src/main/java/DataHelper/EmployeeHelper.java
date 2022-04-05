package DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import Repository.Employee;
import Utility.DataFromExcel;
import Utility.Utilities;

public class EmployeeHelper {

	public List<Employee> GetEmployeeFromExcel() throws IOException {

		List<Employee> listData = null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\TestDataInExcel.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			// Assign the data in Object
			listData = new ArrayList<Employee>();
			int row = 0;
			for (Object[] objects : obj) {
				Employee data = new Employee();
				data.name = (String) obj[row][0];
				System.out.println("data.name: " + data.name);
				data.job = (String) obj[row][1];
				listData.add(data);
				++row;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;

	}

	public void WriteOutput(List<Employee> inputDataList, List<Employee> outputDataList) {

		String fileName = "EmployeeResults_" + Utilities.GetCurrentDatetime() + ".xlsx";
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
			DataFromExcel.AddHeader(rows, cells, 0, GetHeadersList());
		}

		for (int row = 0; row < rowsSize; row++) {

			System.out.println("Rows Count: " + row);
			rows = sheet.createRow(row + 1);

			int cellCount = 0;
			// Compare the outcome and store in excel
			String result = "true";
			if (inputDataList.get(row).name.equals(outputDataList.get(row).name)) {
				result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).name,
						outputDataList.get(row).name, result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).name,
						outputDataList.get(row).name, result);
				System.out.println("Cells Count: " + cellCount);
			}
			if (inputDataList.get(row).job.equals(outputDataList.get(row).job)) {
				result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).job,
						outputDataList.get(row).job, result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).job,
						outputDataList.get(row).job, result);
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
		ArrayList<String> headersList = new ArrayList<String>();
		headersList.add("Actual Name");
		headersList.add("Expected Name");
		headersList.add("Result Name");
		headersList.add("Actual Job");
		headersList.add("Expected Job");
		headersList.add("Result Job");
		return headersList;
	}

	public String GetEmployeeToJson() {
		Employee data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, Employee.class);
		return jsonString;

	}

	public String GetEmployeeToJson(Employee data) {
		// EmployeeData data = GetEmployeeObject();
		Gson gson = new Gson();
		String jsonString = gson.toJson(data, Employee.class);
		return jsonString;

	}

	public Employee GetJsonToEmployee(String jsonString) {
		Gson gson = new Gson();
		Employee data = gson.fromJson(jsonString, Employee.class);
		return data;

	}

	public Employee GetEmployeeObject() {
		Employee data = new Employee();
		data.name = "Sachin Adlakha";
		data.job = "QA Manager";
		return data;
	}
}
