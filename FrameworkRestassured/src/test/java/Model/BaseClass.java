package Model;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class BaseClass {

	@BeforeClass
	public static void SetUp() {
		RestAssured.baseURI ="https://reqres.in/";
		RestAssured.basePath ="api/";
		
	}
}
