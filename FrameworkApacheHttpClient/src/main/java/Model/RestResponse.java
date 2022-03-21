package Model;

public class RestResponse {
	
	public int getStatusCode() {
		return statusCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	private int statusCode;
	private String responseBody;
	
	public RestResponse(int statusCode, String responseBody ) {
		this.statusCode= statusCode;
		this.responseBody = responseBody;
	}

}
