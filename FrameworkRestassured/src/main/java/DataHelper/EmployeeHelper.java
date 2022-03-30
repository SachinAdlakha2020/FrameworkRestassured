package DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Repository.Employee;
import Utility.DataFromExcel;

public class EmployeeHelper {

	public void WriteOutput(List<Employee> inputDataList, List<Employee> outputDataList) {

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
