package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataFromExcel {


	public static Object[][] ReadDataFromExcel(String filePath) throws IOException {
		
		//FileInputStream fis = new FileInputStream("C:\\Users\\Dell\\Downloads\\TestDataInExcel.xlsx");
		FileInputStream fis = new FileInputStream(filePath);
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

}
