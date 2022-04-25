package mockService;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToXmlPattern;

public class WireMockTest {

	static int port = 8080;
	static String host ="localhost";
	static WireMockServer server=new WireMockServer(8080);
	
	@BeforeTest
	public static void startServer() {
		
		server.start();
		WireMock.configureFor(host, port);
	}
	
	@Test(enabled=false)
	public static void getOrderSuccess() {
		ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
		mockResponse.withStatus(200).withHeader("Accept", "text/xml").withBodyFile("order.xml");
		WireMock.stubFor(WireMock.get("/created").willReturn(mockResponse));
	}
	
	@Test (enabled=false)
	public static void getOrderFailure() {
		ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
		mockResponse.withStatus(200).withHeader("Accept", "text/xml").withBodyFile("orderFail.xml");
		WireMock.stubFor(WireMock.get("/updated").willReturn(mockResponse));
	}
	
	//@Test (enabled=false)
	public static void GetLoginPassWithOutPayload(String UrlPath) {
		stubFor(post(urlEqualTo("/epic" + UrlPath))
			        .willReturn(aResponse()
			          .withStatus(200)
			          .withHeader("Accept", "application/xml")
			          .withHeader("Content-Type", "application/xml")
			          .withBodyFile("loginSuccess.xml")));
	}
	
	//@Test (enabled=true)
		public static void GetLoginPassWithFullPayload(String UrlPath) {
			
			stubFor(post(urlEqualTo("/epic" + UrlPath))
					.withRequestBody(equalToXml("<Request><Login>loginText</Login><Password>loginPassword</Password></Request>"))
				        .willReturn(aResponse()
				        		 .withStatus(200)
						          .withHeader("Accept", "application/xml")
						          .withHeader("Content-Type", "application/xml")
						          .withBodyFile("loginSuccess.xml")));
		}
	
		//@Test (enabled=true)
		public static void GetLoginPassWithPartialPayload(String UrlPath) {
			
			stubFor(post(urlEqualTo("/epic" + UrlPath))
					.withRequestBody(matchingXPath("/Request/Login",equalToXml("<Login>loginText</Login>")))
					.withRequestBody(matchingXPath("/Request/Password",equalToXml("<Password>loginPassword</Password>")))
				        .willReturn(aResponse()
				        		 .withStatus(200)
						          .withHeader("Accept", "application/xml")
						          .withHeader("Content-Type", "application/xml")
						          .withBodyFile("loginSuccess.xml")));
			
			//matchingXPath("Login",equalToXml("<Login>Logintext</Login>"))
		}
	@AfterTest
	public static void stopServer() {
		server.shutdown();
	}
  
}
