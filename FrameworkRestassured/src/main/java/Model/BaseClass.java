package Model;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;

public class BaseClass {

	public static String recordResult;
	public static String fieldResult;
	
	
	@BeforeClass
	public static void SetUp() {
		//RestAssured.baseURI ="https://reqres.in/";
		//RestAssured.basePath ="api/";	
		RestAssured.baseURI ="https://reqbin.com/";
		RestAssured.basePath ="echo";
		
		
		
	}

	@BeforeMethod
	public void InitializeValues() {
		recordResult="Pass";
		fieldResult="Pass";
	}
}
