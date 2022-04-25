package Repository;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.gson.annotations.Expose;

import Repository.DataPerPage.UserData;


@JacksonXmlRootElement
@JsonPropertyOrder({"ignoreOrdering","orderHeaderKey","Note"})

public class Order {

	@JacksonXmlProperty(isAttribute = true, localName = "IgnoreOrdering")
	public String ignoreOrdering;
	@JacksonXmlProperty(isAttribute = true, localName = "OrderHeaderKey")
	public Float orderHeaderKey;
	@JacksonXmlElementWrapper(localName = "Notes")
	public ArrayList<Note> Note;
	//@JacksonXmlRootElement
	@JsonPropertyOrder({"operation","noteText"})
	public static class Note {
		@JacksonXmlProperty(isAttribute = true, localName = "NoteText")
		public String noteText;
		@JacksonXmlProperty(isAttribute = true, localName = "Operation")		
		public String operation;
	}
}
