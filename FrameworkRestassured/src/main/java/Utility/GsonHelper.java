package Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	public static String ToJson(Object data) {
		// EmployeeData data = GetEmployeeObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		String jsonString = gson.toJson(data, Object.class);
		System.out.println("jsonString: " + jsonString);
		return jsonString;

	}
	
	public static Object ToObject(String jsonString,Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		object = gson.fromJson(jsonString, object.getClass());
		return object;

	}
	
}
