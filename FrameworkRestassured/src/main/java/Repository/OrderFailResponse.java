package Repository;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import Repository.Order.Note;

@JacksonXmlRootElement(localName = "Order")
public class OrderFailResponse {

	@JacksonXmlProperty(isAttribute = true, localName = "OrderHeaderKey")
	public Float orderHeaderKey;
	@JacksonXmlElementWrapper(localName = "Errors")	
	@JacksonXmlProperty(localName = "Error")
	public ArrayList<Errors> errorList;	
	public static class Errors {
		@JacksonXmlProperty(isAttribute = true, localName = "message")
		public String message;
	}
}
