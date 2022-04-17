package mockService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WireMockTest {

	static int port = 8080;
	static String host ="localhost";
	static WireMockServer server=new WireMockServer(8080);
	
	@BeforeTest
	public static void startServer() {
		server.start();			
	}
	
	@Test
	public static void getOrder() {
		//startServer();
		ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
		//mockResponse.withStatus(200).withBodyFile("resources/order.xml");
		mockResponse.withStatus(200).withHeader("Accept", "text/xml").withBodyFile("order.xml");
		WireMock.configureFor(host, port);
		WireMock.stubFor(WireMock.get("/orders").willReturn(mockResponse));
		//stopServer();
	}
	
	@AfterTest
	public static void stopServer() {
		server.shutdown();
	}
  
}
