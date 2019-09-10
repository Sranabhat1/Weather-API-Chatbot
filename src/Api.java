//============================================================================
// Name        : Api.java
// Author      : Sabal Ranabhat
// Course      : UTDallas CS 2336.501 S19
// Version     : 1.0
// Copyright   : 2019
//
// Description :
//
//    This program defines the Api class that helps construct an instance of 
//	  the class with the url and token provided. It makes a http request to 
//	  a API source through ApiRequest() funtion and reads in a json response. 
//	  This response is parsed through two different function. The 
//	  parseWeatherJsonFunction parses the json response from the OpenWeatherMap
//	  Api and returns the necessary weather details. The parseGoogleJsonFunction
//	  parses the json response from the Geocoder Api from google that returns the  
//	  lat and long value of a certain place.
//
//============================================================================


//name of the package
package myBot;

//imported essential packages
import java.io.*;
import java.net.*;

import com.google.gson.*;

//Api class definition
public class Api {
	
	private String myAPIurl;				//stores the url of the API source
	private String myApiToken;				//stores the token for the API source
	private static String formAddress;		//stores the address of the user input location
	
	//constructor taking in the url and token value
	public Api(String url, String token) {
		myAPIurl = url;
		myApiToken = token;

	}
	
	//definition of ApiRequest function that connects to the API source through HTTP request
	public String ApiRequest(String userInput) throws Exception{
		
		//completes the url with token and user input and stores in URL
		String URL = this.myAPIurl + userInput + this.myApiToken; 
		
		//creates an instance of url and tries to connect to the source through the http request
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		//The bufferedReader reads in the json response from the stream and stores it in response in a single line
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String response = "";
		StringBuffer content = new StringBuffer();
		while((result=rd.readLine())!=null) {
			content.append(result);
			response += result;
		}
		
		//if the Api source is google, call the parseGoogleJsonFunction()
		if(this.myAPIurl.contains("maps")) {
			String temp = parseGoogleJsonFunction(response);
			return temp;
		}
		
		//else, call the parseWeatherJsonFunction()
		else {
			String temp = parseWeatherJsonFunction(response);  //this function will return 
			return temp;
		}

}
 
		//parseWeatherJsonFunction() definition that will parse the json response from the weather Api source
		public static String parseWeatherJsonFunction(String json) 
		{
			
		//Temperature value extraction and stores it at response and returns it
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		JsonObject main = object.getAsJsonObject("main");
		double temp = main.get("temp").getAsDouble();
		double temp_min = main.get("temp_min").getAsDouble();
		double temp_max = main.get("temp_max").getAsDouble();
		String response = formAddress + " is "+ round(toFarenheit(temp),2) + " with a high of "+round(toFarenheit(temp_max),2)+ " and a low of "+round(toFarenheit(temp_min),2) +".";
		return response;
		}
		
		//parseGoogleJsonFunction() definition that will parse the json response from the google Api source
		public static String parseGoogleJsonFunction(String json) 
		{
			
		//Long and Lat value extraction and store it at temp and return
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		JsonArray main = object.getAsJsonArray("results");
		JsonObject address = main.get(0).getAsJsonObject();
		formAddress = address.get("formatted_address").getAsString();
		JsonObject components = address.getAsJsonObject("geometry");
		JsonObject location  = components.getAsJsonObject("location");
		Double lat = location.get("lat").getAsDouble();
		Double lon = location.get("lng").getAsDouble();
		String temp = "lat="+lat+"&lon="+lon;
		return temp; 
		}
		
		//convert kelvin to farenheit
		public static double toFarenheit(double temp) {
			return (temp*1.8)-459.67;
		}
		
		//round the decimal values to 2 places
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
}