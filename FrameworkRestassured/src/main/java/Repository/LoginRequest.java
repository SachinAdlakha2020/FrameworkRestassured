package Repository;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="Request")
public class LoginRequest {
	@JacksonXmlProperty(localName="Login")
	public String login;
	@JacksonXmlProperty(localName="Password")
	public String password;
}
