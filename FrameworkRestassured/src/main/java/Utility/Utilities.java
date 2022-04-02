package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

	public static String GetCurrentDatetime() {
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String currentDateTime = formatter.format(date);
		return currentDateTime;
	}
}
