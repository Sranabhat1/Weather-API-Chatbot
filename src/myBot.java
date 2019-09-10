//============================================================================
// Name        : myBot.java
// Author      : Sabal Ranabhat
// Course      : UTDallas CS 2336.501 S19
// Version     : 1.0
// Copyright   : 2019
//
// Description :
//
//    This program defines the myBot class that extends to the PircBot class
//	  and handles all the messages by the user
//
//============================================================================


//name of the package
package myBot;

//imported essential packages
import org.jibble.pircbot.*;

//myBot class definition
public class myBot extends PircBot {
    
	//constructor
    public myBot() {
    	
    	//sets the name of the bot
        this.setName("NinjaBot");
    }
    
    //onMessage() function definition that handles the messages through sendMessage() function
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
    	
    	//convert message to lowercase and ignore spaces
    	message = message.toLowerCase().replaceAll("\\s+","");
    	
    	//only respond if the message contains ninjabot
    	if(message.contains("ninjabot")) {
    	
    	//if message contains "time", return time
        if (message.contains("time")) {
        	
        	//stores the current time from the java library and responds
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
        
        //If the message contains "how are you", respond 
        else if(message.contains("howareyou")) {
        	String response = "Amazing! How can I help you?";
        	sendMessage(channel, sender + ": " + response);
        }
        
        //if the message contains greeting
        else if(message.contains("hi") || message.contains("hello") || message.contains("hey")) {
        	String response = " Hey! How can I help you?";
        	sendMessage(channel, sender + response);
        }
        
        //if the message equals "weather", respond
        else if(message.equals("ninjabotweather") || message.equals("ninjabottemperature")) {
        	sendMessage(channel, sender + ", Where would you like to know the weather of?");
        }
        
        //else, connect to the api source and respond 
        else{
        	
        //try to connect to the api source or get valid response
        try {
        	
        	//google api url and key
        	String googleApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        	String googleApiKey = "&key=AIzaSyAi6HaH8DPpvjUWs2jBD7EriieNDBYyQJo";
        	
        	//openweathermap api url and key
        	String weatherApiUrl = "http://api.openweathermap.org/data/2.5/weather?";
        	String weatherApiKey = "&APPID=784a37f6d23aee69660cf47e67a4e087";
        	
        	//create 2 instances of the api and connect them
        	Api newWeatherApi = new Api(weatherApiUrl, weatherApiKey);
        	Api newGoogleApi = new Api(googleApiUrl,googleApiKey);
        	
        	//get the lat and long value of the location provided by the user by the google api
        	String location = newGoogleApi.ApiRequest(message);
        	
        	//get the weather with the lat and long value by the onweathermap api
        	String response = newWeatherApi.ApiRequest(location);
        	
        	//respond
        	sendMessage(channel, sender + ", The temperature of "+ response + " in degree Farenheit");
        
        }catch(Exception e) {
        	
        	//if cannot connect or invalid response, respond
        	sendMessage(channel, sender + " Sorry, I could not find the information!");
        }
    }
    }
    }
}