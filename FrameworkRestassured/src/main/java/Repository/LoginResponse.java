package Repository;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName="Response")
public class LoginResponse {
	@JacksonXmlProperty(localName ="ResponseCode")
	public int responseCode;
	@JacksonXmlProperty(localName = "ResponseMessage")
	public String responseMessage;
}
