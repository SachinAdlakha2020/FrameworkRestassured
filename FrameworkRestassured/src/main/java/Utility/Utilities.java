package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Utilities {

	public static String GetCurrentDatetime() {
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd-HHmmss");
		Date date = new Date();
		String currentDateTime = formatter.format(date);
		return currentDateTime;
	}

	public static void addStepResult(String stepResult, String scenario, ExtentTest logger) {
		if (stepResult.equalsIgnoreCase("Pass")) {
			logger.log(LogStatus.PASS, scenario);

		} else {
			logger.log(LogStatus.FAIL, scenario);
		}

	}
}
