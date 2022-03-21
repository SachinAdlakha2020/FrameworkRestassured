package TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hpsf.Array;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import ObjectRepository.UserDetail;

public class TestfileExecution {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Object[][] data=ReadDataFromExcel();
		int value = data.length;
		ArrayList<UserDetail> arrUserDetails = new ArrayList<>();
		UserDetail details = null;
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			details = new UserDetail();
			for (int j = 0; j < objects.length; j++) {
				System.out.println(objects[j]);
				
				switch (j) {
				case 0:
					details.name =  (String) objects[j];
					break;
				case 1:
					details.job =  (String) objects[j];
					break;
				/*
				 * case 2: details.id = (Double) objects[j]; break;
				 */

				default:
					break;
				}
					
			}
			arrUserDetails.add(details);
		}
		
		Gson json = new Gson();
		String jsonString = json.toJson(arrUserDetails);
		System.out.println(jsonString);
		
		//read the data from object
		//System.out.println("Print data length: " + data.length);
		//System.out.println("Reading thorugh two dimentional array: " + data[0][0]);
		//Iterator<Object[][]> value = data.iterator();
		
		
		/*ArrayList<Object[][]> data=ReadDataFromExcelByArrayList();
		ArrayList<UserDetail> arrDetails = new ArrayList<UserDetail>();
		for (Object[][] objects : data) {
		UserDetail details = new UserDetail();
			details.name =  (String) objects[0][0];
			details.job =  (String) objects[0][1];
			details.id =  (Double) objects[0][2];
			arrDetails.add(details);
		}
		
		Gson json = new Gson();
		String jsonString = json.toJson(arrDetails);
				System.out.println(jsonString);*/
		
	}

	public static Object[][] ReadDataFromExcel() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\Dell\\Downloads\\TestDataInExcel.xlsx");
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		int noOfSheets = workBook.getNumberOfSheets();
		System.out.println("# of sheets: " + noOfSheets);

		XSSFSheet sheet = workBook.getSheet("Sheet1");
		int r1 = sheet.getLastRowNum();
		System.out.println("# of rows: " + r1);
		XSSFRow firstrow = sheet.getRow(0);
		int c1 = firstrow.getLastCellNum();
		// c1++;
		System.out.println("# of columns: " + c1);
		Object[][] data = new Object[r1][c1];

		Iterator<Row> rows = sheet.iterator();
		r1 = 0;
		c1 = 0;
		FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
		while (rows.hasNext()) {
			Row row = rows.next();
			System.out.println("Print the rownum: " + row.getRowNum());
			if ((row.getRowNum() == 0))
				continue;

			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				Cell value = cells.next();
				System.out.println(value.getCellType());
				switch (value.getCellType()) {
				case STRING:
					System.out.println(value.getStringCellValue());
					data[r1][c1] = value.getStringCellValue();
					break;
				case NUMERIC:
					System.out.println(value.getNumericCellValue());
					data[r1][c1] = value.getNumericCellValue();
					break;
				case BOOLEAN:
					System.out.println(value.getBooleanCellValue());
					data[r1][c1] = value.getStringCellValue();
					break;
				}
				c1++;
			}
			r1++;c1=0;
		}
		return data;
	}

	public static ArrayList<Object[][]> ReadDataFromExcelByArrayList() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\Dell\\Downloads\\TestDataInExcel.xlsx");
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		int noOfSheets = workBook.getNumberOfSheets();
		System.out.println("# of sheets: " + noOfSheets);

		XSSFSheet sheet = workBook.getSheet("Sheet1");
		int r1 = sheet.getLastRowNum();
		System.out.println("# of rows: " + r1);
		XSSFRow firstrow = sheet.getRow(0);
		int c1 = firstrow.getLastCellNum();
		// c1++;
		System.out.println("# of columns: " + c1);
		Object[][] data = new Object[1][c1];
		ArrayList<Object[][]> listOfArrayData = new ArrayList<Object[][]>();
		// data.

		Iterator<Row> rows = sheet.iterator();
		r1 = 0;
		c1 = 0;

		while (rows.hasNext()) {
			Row row = rows.next();
			if (!(row.getRowNum() == 1))
				continue;

			Iterator<Cell> cells = row.cellIterator();

			while (cells.hasNext()) {
				Cell value = cells.next();
				System.out.println(value.getCellType());
				switch (value.getCellType()) {
				case STRING:
					System.out.println(value.getStringCellValue());
					data[0][c1] = value.getStringCellValue();
					break;
				case NUMERIC:
					System.out.println(value.getNumericCellValue());
					data[0][c1] = value.getNumericCellValue();
					break;
				case BOOLEAN:
					System.out.println(value.getBooleanCellValue());
					data[0][c1] = value.getStringCellValue();
					break;
				}
				c1++;
			}
			r1++;
			listOfArrayData.add(data);
		}
		return listOfArrayData;
	}

}
