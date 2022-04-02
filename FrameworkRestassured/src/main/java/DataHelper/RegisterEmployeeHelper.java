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

import Repository.Employee;
import Repository.RegisterEmployee;
import Utility.DataFromExcel;

public class RegisterEmployeeHelper {

	public List<RegisterEmployee> GetRegisterEmployeeExcel() throws IOException {

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

	public void WriteOutput(List<RegisterEmployee> inputDataList, List<RegisterEmployee> outputDataList) {

		// Creating the unique file name with current date and time.
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String currentDateTime = formatter.format(date);
		String fileName = "RegisterEmployeeResults_" + currentDateTime + ".xlsx";
		String outputFilePath = "E:\\Automation\\TestData\\Output\\" + fileName;
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Register Employee Data");

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

			if (!inputDataList.get(row).scenario.isEmpty()) {
				// result = "Pass";
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
			if (inputDataList.get(row).id==outputDataList.get(row).id) {
				result = "Pass";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).id,
						outputDataList.get(row).id, result);
				System.out.println("Cells Count: " + cellCount);
			} else {
				result = "Fail";
				cellCount = DataFromExcel.AddCells(rows, cells, cellCount, inputDataList.get(row).id,
						outputDataList.get(row).id, result);
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
		headersList.add("Scenario");
		headersList.add("Email");
		headersList.add("Password");
		headersList.add("Id Expected");
		headersList.add("Id Actual");
		headersList.add("Id Result");
		headersList.add("Token Expected");
		headersList.add("Token Actual");
		headersList.add("Token Result");
		return headersList;
	}

	public String ToJson(RegisterEmployee data) {
		// EmployeeData data = GetEmployeeObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		String jsonString = gson.toJson(data, RegisterEmployee.class);
		System.out.println("jsonString: " + jsonString);
		return jsonString;

	}

	public RegisterEmployee ToObject(String jsonString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		RegisterEmployee data = gson.fromJson(jsonString, RegisterEmployee.class);
		return data;

	}

}
