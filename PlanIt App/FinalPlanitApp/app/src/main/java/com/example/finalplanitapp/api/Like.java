package com.example.finalplanitapp.api;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.example.finalplanitapp.api.Utils;

import com.example.finalplanitapp.dao.UserDAO;

public class Like implements HttpHandler {

    public Like() {}

    public void handle(HttpExchange r) throws IOException {
        try {
        	if (r.getRequestMethod().equals("GET")) {
        		handleGet(r);
        	}
            if (r.getRequestMethod().equals("PUT")) {
                handlePut(r);
            }
            else {
            	// Method not allowed
                r.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            r.sendResponseHeaders(500, -1);
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange r) throws IOException, JSONException {

        // Attempt to parse the request body as a JSON
        String body;
        JSONObject json;
        try {
            body = Utils.convert(r.getRequestBody());
            json = new JSONObject(body);
        }
        catch (IOException | JSONException e) {
            r.sendResponseHeaders(400, -1);
            return;
        }

        // Ensure the JSON has the appropriate keys
        if (!json.has("userId")) {
            r.sendResponseHeaders(400, -1);
            return;
        }

        // Extract the values
        String userId = json.getString("userId");

        // Get liked places
        // TODO: use dependency injection
        UserDAO user = UserDAO.getInstance();
        Set<String> placeIds = user.getLikedPlaceIds(userId);
        
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("placeIds", placeIds);
        
        // SUCCESS - send result
        String response = responseJSON.toString();
        r.sendResponseHeaders(200, response.length());
        OutputStream stream = r.getResponseBody();
        stream.write(response.getBytes());
        stream.close();
    }
    
    public void handlePut(HttpExchange r) throws IOException, JSONException {

        // Attempt to parse the request body as a JSON
        String body;
        JSONObject json;
        try {
            body = Utils.convert(r.getRequestBody());
            json = new JSONObject(body);
        }
        catch (IOException | JSONException e) {
            r.sendResponseHeaders(400, -1);
            return;
        }

        // Ensure the JSON has the appropriate keys
        if (!json.has("userId") || !json.has("placeId")) {
            r.sendResponseHeaders(400, -1);
            return;
        }

        // Extract the values
        String userId = json.getString("userId");
        String placeId = json.getString("placeId");

        // Insert into the database
        // TODO: use dependency injection
        UserDAO user = UserDAO.getInstance();
        user.setLike(userId, placeId);
        	
        // SUCCESS
        r.sendResponseHeaders(200, -1);
    }
}
