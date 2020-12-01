package com.example.finalplanitapp.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;

import com.example.finalplanitapp.dao.Config;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.HashSet;
import java.util.Set;

public class UserDAO {

	private static UserDAO instance;
	
	private Driver driver;
	
	public UserDAO(Config config) {
		
		// Check if we have the neo4j driver
        try {
        	Class.forName("org.neo4j.driver.v1.GraphDatabase");
        }
        catch (ClassNotFoundException e) 
        {
        	System.out.println("Missing Neo4j driver.");
        }
		
		// Connect to the DB
        // TODO: change this
		driver = GraphDatabase.driver(
				config.neo4jURI,
				AuthTokens.basic(config.neo4jUsername, config.neo4jPassword));
	}
	
	public static UserDAO getInstance() {
	
		if (instance == null) {
			instance = new UserDAO(new Config());
		}
		return instance;
	}
	
	/*
	 * Returns a set of place IDs like by the specified user.
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public Set<String> getLikedPlaceIds(String userId) {
		
		Set<String> likedPlaces = new HashSet<String>();
		
		// Query the DB for liked places
		try (Session session = driver.session()) {
			
			String statement = "MATCH (p:place) <-[:LIKED]- (u:user {id :{userId}}) RETURN p";
            StatementResult result = session.run(statement, parameters("userId", userId));
			
            // Extract the place IDs from the result
			for (Record record : result.list()) {
                String placeId = record.get("p").get("id").asString();
                likedPlaces.add(placeId);
            }
		}
		
		return likedPlaces;
	}
	
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public Set<String> getDislikedPlaceIds(String userId) {
		
		Set<String> dislikedPlaces = new HashSet<String>();
		
		// Query the DB for places disliked by the user
		try (Session session = driver.session()) {
			
			String statement = "MATCH (p:place) <-[:DISLIKED]- (u:user {id :{userId}}) RETURN p";
            StatementResult result = session.run(statement, parameters("userId", userId));
			
            // Extract the place IDs from the result
			for (Record record : result.list()) {
                String placeId = record.get("p").get("id").asString();
                dislikedPlaces.add(placeId);
            }
		}
		
		return dislikedPlaces;
	}
	
	public void setLike(String userId, String placeId) {
		
		try (Session session = driver.session()) {	
			
			// Create the user if they don't exist
            session.run("MERGE (u:user{id:{userId}})", parameters("userId", userId));
            // Create he place if it doesn't exist
            session.run("MERGE (p:place{id:{placeId}})", parameters("placeId", placeId));
			
            // Create the like relationship between the two
            String statement = "MATCH (u:user {id:{userId}}), (p:place {id:{placeId}}) MERGE (u)-[:LIKED]->(p)";
            session.run(statement, parameters("userId", userId, "placeId", placeId));
		}
	}
	
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void setDislike(String userId, String placeId) {
		
		try (Session session = driver.session()) {	
			
			// Create the user if they don't exist
            session.run("MERGE (u:user{id:{userId}})", parameters("userId", userId));
            // Create he place if it doesn't exist
            session.run("MERGE (p:place{id:{placeId}})", parameters("placeId", placeId));
			
            // Create the dislike relationship between the two
            String statement = "MATCH (u:user {id:{userId}}), (p:place {id:{placeId}}) MERGE (u)-[:DISLIKED]->(p)";
            session.run(statement, parameters("userId", userId, "placeId", placeId));
		}
	}
}
