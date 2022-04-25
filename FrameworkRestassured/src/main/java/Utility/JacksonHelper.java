package Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class JacksonHelper {

	
	public static String ToXml(Object data) {
		XmlMapper mapper = new XmlMapper();
		String xmlString=null;
		try {
			xmlString = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("xmlString: " + xmlString);
		return xmlString;

	}
	
	public static Object ToObject(String xmlString,Object object) {
		XmlMapper mapper = new XmlMapper();		
		try {
			object = mapper.readValue(xmlString, object.getClass());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;

	}
}
