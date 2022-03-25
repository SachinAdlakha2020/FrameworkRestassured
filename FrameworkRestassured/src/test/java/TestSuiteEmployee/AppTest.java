package TestSuiteEmployee;

import java.net.URI;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws URISyntaxException 
     */
    @Test
    public void shouldAnswerWithTrue() throws URISyntaxException
    {
        Response response = RestAssured.when().get(new URI("https://reqres.in/api/users?page=2"));
        System.out.println(response.asString());
        System.out.println(response.statusCode());
        System.out.println(response.getStatusCode());
    }
    
}
