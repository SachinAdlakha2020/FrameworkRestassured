package Model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class RestAPiHelper {

	
	public static Response GetResponse(String UriPath) throws URISyntaxException {
		Response response = RestAssured.given()
				.accept(ContentType.JSON)
		.when()
		.get(new URI(UriPath));
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		System.out.println(response.getStatusCode());
		return response;
	}
	
	public static Response GetResponse(String UriPath , Map<String,String> headers) throws URISyntaxException {
		System.out.println( RestAssured.baseURI +RestAssured.basePath +UriPath);
		Response response = RestAssured
		.given()			
		.headers(headers)
		.when()
		.get(new URI("users?page=2"));
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		return response;
	}
	
	public static Response GetResponse(String UriPath , Map<String,String> headers,String val1, String val2, int val3) throws URISyntaxException {
		System.out.println( RestAssured.baseURI +RestAssured.basePath +UriPath);
		Response response = (Response) RestAssured
		.given()			
		.headers(headers)
		.when()
		.get(new URI("users?page=2"))
		.then()
		.body("page",equalToObject(2));
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		return response;
	}
	
	public static Response PostRequest(String UriPath , Map<String,String> headers,String content) throws URISyntaxException {
		
		String Url = RestAssured.baseURI +RestAssured.basePath +UriPath;
		System.out.println(Url);
		Response response = RestAssured
		.given()
		.log()
		.all()
		.accept(ContentType.JSON)
		.headers(headers)
		.and()
		.body(content)
		.when()
		.post(new URI(Url));
		
		
		
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		return response;
	}
	
public static Response PostRequestAsObject(String UriPath , Map<String,String> headers,Object object) throws URISyntaxException {
		
		String Url = RestAssured.baseURI +RestAssured.basePath +UriPath;
		System.out.println(Url);
		Response response = RestAssured
		.given()
		.log()
		.all()
		.accept(ContentType.JSON)
		.headers(headers)
		.and()
		.body(object)
		.when()
		.post(new URI(Url));
					
		System.out.println(response.asString());
		System.out.println(response.statusCode());
		return response;
	}
	
}
