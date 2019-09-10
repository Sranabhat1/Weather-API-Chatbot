//============================================================================
// Name        : main.java
// Author      : Sabal Ranabhat
// Course      : UTDallas CS 2336.501 S19
// Version     : 1.0
// Copyright   : 2019
//
// Description :
//
//    This program defines the main class which connects the bot instance to 
//	  the IRC server and the specific channel and reads in the messages from 
//	  the user.
//
//============================================================================

//name of the package
package myBot;

//imported essential packages
import org.jibble.pircbot.*;

//main class definition
public class main {
    
	//main function
    public static void main(String[] args) throws Exception {
        
        // Create an instance of the myBot function
        myBot bot = new myBot();
        
        // Enabling bebugging output
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        // Join the #pircbot channel.
        bot.joinChannel("#pircbot");
        
        //Introduce a message
        bot.sendMessage("#pircbot", "Welcome to NinjaBot! How may I help you today?");
 
    }
    
}