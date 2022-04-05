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
import com.google.gson.GsonBuilder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Repository.Employee;
import Repository.RegisterEmployee;

import Utility.DataFromExcel;
import Utility.Utilities;

public class RegisterEmployeeHelper {

	public List<RegisterEmployee> GetRegisterEmpValidData() throws IOException {

		List<RegisterEmployee> listData = null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\RegisterEmployeeInput.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			// Assign the data in Object
			listData = new ArrayList<RegisterEmployee>();
			int row = 0;
			for (Object[] objects : obj) {
				RegisterEmployee data = new RegisterEmployee();
				data.scenario = (String) obj[row][0];
				data.email = (String) obj[row][1];
				data.password = (String) obj[row][2];
				data.id = (double) obj[row][3];
				data.token = (String) obj[row][4];
				data.error = (String) obj[row][5];
				listData.add(data);
				++row;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;

	}

	public List<RegisterEmployee> GetRegisterEmpInvalidData() throws IOException {

		List<RegisterEmployee> listData = null;
		try {
			// Access the file
			String filePath = "E:\\Automation\\TestData\\Input\\RegisterEmployeeInvalidInputs.xlsx";
			// read the data in two dimentioanl object
			Object[][] obj = DataFromExcel.ReadDataFromExcel(filePath);

			// Assign the data in Object
			listData = new ArrayList<RegisterEmployee>();
			int row = 0;
			for (Object[] objects : obj) {
				RegisterEmployee data = new RegisterEmployee();
				data.scenario = (String) obj[row][0];
				if (obj[row][1] != null) {
					data.email = (String) obj[row][1];
				} else {
					data.email = "";
				}
				if (obj[row][2] != null) {
					data.password = (String) obj[row][2];
				} else {
					data.password = "";
				}
				data.error = (String) obj[row][3];
				listData.add(data);
				++row;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;

	}

	public void WriteOutputValidData(List<RegisterEmployee> inputDataList, List<RegisterEmployee> outputDataList, ExtentTest logger) {

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Register Employee Data");

		String fieldResult = "Pass";
		String recordResult = "Pass";
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
			String scenario = "Scenario is not defined";
			int cellCount = 0;fieldResult = "Pass";recordResult = "Pass";

			if (!inputDataList.get(row).scenario.isEmpty()) {
				scenario = inputDataList.get(row).scenario;
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).scenario);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "Scenario is not added");
				System.out.println("Cells Count: " + cellCount);
			}
			if (!inputDataList.get(row).email.isEmpty()) {
				// result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).email);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "Email is not added");
				System.out.println("Cells Count: " + cellCount);
			}
			if (!inputDataList.get(row).password.isEmpty()) {
				// result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).scenario);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "password is not added");
				System.out.println("Cells Count: " + cellCount);
			}
			if (inputDataList.get(row).id == outputDataList.get(row).id) {
				fieldResult = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).id,
						outputDataList.get(row).id, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			} else {
				fieldResult = "Fail";recordResult = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).id,
						outputDataList.get(row).id, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			}
			if (inputDataList.get(row).token.equals(outputDataList.get(row).token)) {
				fieldResult = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).token,
						outputDataList.get(row).token, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			} else {
				fieldResult = "Fail";recordResult = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).token,
						outputDataList.get(row).token, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			}
			DataFromExcel.FinalStatusCell(rows,cells,cellCount,recordResult);
			Utilities.addStepResult(fieldResult, scenario, logger);
			
		}

		try {
			// Creating the unique file name with current date and time.

			String fileName = fieldResult + "_RegisterEmployeeResults_" + Utilities.GetCurrentDatetime() + ".xlsx";
			String outputFilePath = "E:\\Automation\\TestData\\Output\\" + fileName;
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(outputFilePath));
			workbook.write(out);
			out.close();
			System.out.println("Outputfile written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void WriteOutputInvalidData(List<RegisterEmployee> inputDataList, List<RegisterEmployee> outputDataList,ExtentTest logger) {

		
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Register Employee Invalid Data");

		String fieldResult = "Pass";
		String recordResult = "Pass";
		Row rows = null;

		Cell cells = null;
		int rowsSize = inputDataList.size();

		if (rowsSize > 0) {
			rows = sheet.createRow(0);
			DataFromExcel.AddHeader(rows, cells, 0, GetHeadersListInvalidData());
		}

		for (int row = 0; row < rowsSize; row++) {

			System.out.println("Rows Count: " + row);
			rows = sheet.createRow(row + 1);
			String scenario = "Scenario is not defined";
			int cellCount = 0;fieldResult = "Pass";recordResult = "Pass";
			// Compare the outcome and store in excel
			

			if (!inputDataList.get(row).scenario.isEmpty()) {
				scenario= inputDataList.get(row).scenario;
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).scenario);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "Scenario is not defined");
				System.out.println("Cells Count: " + cellCount);
			}
			if (!inputDataList.get(row).email.isEmpty()) {
				// result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).email);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "Email is not added");
				System.out.println("Cells Count: " + cellCount);
			}
			if (!inputDataList.get(row).password.isEmpty()) {
				// result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).scenario);
				System.out.println("Cells Count: " + cellCount);
			} else {
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, "password is not added");
				System.out.println("Cells Count: " + cellCount);
			}
			if (inputDataList.get(row).error.equals(outputDataList.get(row).error)) {
				fieldResult = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).error,
						outputDataList.get(row).error, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			} else {
				fieldResult = "Fail";recordResult="Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).error,
						outputDataList.get(row).error, fieldResult);
				System.out.println("Cells Count: " + cellCount);
			}
			DataFromExcel.FinalStatusCell(rows,cells,cellCount,recordResult);
			Utilities.addStepResult(fieldResult, scenario, logger);
		}

		try {
			
			// Creating the unique file name with current date and time.
			
			String fileName =  fieldResult + "_RegisterEmployeeInvalidResults_" + Utilities.GetCurrentDatetime() + ".xlsx";
			String outputFilePath = "E:\\Automation\\TestData\\Output\\" + fileName;
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
		headersList.add("Scenario");
		headersList.add("Email");
		headersList.add("Password");
		headersList.add("Id Expected");
		headersList.add("Id Actual");
		headersList.add("Id Result");
		headersList.add("Token Expected");
		headersList.add("Token Actual");
		headersList.add("Token Result");
		headersList.add("Final Result");
		return headersList;
	}

	private ArrayList<String> GetHeadersListInvalidData() {
		ArrayList<String> headersList = new ArrayList<String>();
		headersList.add("Scenario");
		headersList.add("Email");
		headersList.add("Password");
		headersList.add("Error Expected");
		headersList.add("Error Actual");
		headersList.add("Error Result");
		headersList.add("Final Result");
		return headersList;
	}

}
