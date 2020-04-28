package org.startup.java;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

public class JacksonStreamingApiPocApplication {

	public static void main(String[] args) {
		System.out.println("Constructing JSON file by using Jackson Streaming API in Java");
		constructJSON("jacksondemo.json");
		System.out.println("done");
		
		System.out.println("Parsing JSON file by using Jackson Streaming API");
		parseJSON("Jacksondemo.json");
		System.out.println("done");
	}
	
	/*
	 * This method construct JSON String by using Jackson Streaming API.
	 */
	public static void constructJSON(String path) {
		try {
			JsonFactory jsonfactory = new JsonFactory();
			File jsonDoc = new File(path);
			JsonGenerator generator = jsonfactory.createJsonGenerator(jsonDoc, JsonEncoding.UTF8);
			
			generator.writeStartObject();
			generator.writeStringField("firstname", "Garrison");
			generator.writeStringField("lastname", "Paul");
			generator.writeNumberField("phone", 847332223);
			
			generator.writeFieldName("address");
			
			generator.writeStartArray();
			generator.writeString("Unit - 231");
			generator.writeString("Sofia Stream");
			generator.writeString("Mumbai");
			generator.writeEndArray();
			
			generator.writeEndObject();
			
			generator.close();
			
			System.out.println("JSON file constructed successfully");
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	/*
	 * This method parses JSON String by using Jackson Streaming API example.
	 */
	public static void parseJSON(String filename) {
		JsonFactory jsonfactory = new JsonFactory();
		File source = new File(filename);
		
		try {
			JsonParser parser = jsonfactory.createJsonParser(source);
			
			// starting parsing of JSON String
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String token = parser.getCurrentName();
				
				if ("firstname".equals(token)) {
					parser.nextToken(); // next token contains value
					String fname = parser.getText(); // getting text field
					System.out.println("firstname : " + fname);
				}
				
				if ("lastname".equals(token)) {
					parser.nextToken();
					String lname = parser.getText();
					System.out.println("lastname : " + lname);
				}
				
				if ("phone".equals(token)) {
					parser.nextToken();
					int phone = parser.getIntValue(); // getting numeric field
					System.out.println("phone : " + phone);
				}
				
				if ("address".equals(token)) {
					System.out.println("address : ");
					parser.nextToken(); // next token will be '[' which means JSON array
					
					// parse tokens until you find ']'
					while (parser.nextToken() != JsonToken.END_ARRAY) {
						System.out.println(parser.getText());
					}
				}
			}
			parser.close();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
