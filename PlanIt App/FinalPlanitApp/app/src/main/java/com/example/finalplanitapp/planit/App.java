package com.example.finalplanitapp.planit;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import com.example.finalplanitapp.api.Like;
import com.example.finalplanitapp.dao.UserDAO;
import com.example.finalplanitapp.itinerary.Itinerary;

public class App {

	static int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
		
		 HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
		 server.createContext("/api/v1/like", new Like());
		 //server.createContext("/api/v1/dislike", new Dislike());
	     server.start();
	     System.out.printf("Server started on port %d...\n", PORT);
	     
	     //UserDAO account = UserDAO.getInstance();
	     //account.setLike("1", "B");
	     //account.setLike("1", "C");
	     //account.setLike("2", "B");
	}

}
