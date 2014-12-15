package com.special_friend.parsers;

import jsimple.io.StringReader;
import jsimple.json.objectmodel.JsonArray;
import jsimple.json.objectmodel.JsonObject;
import jsimple.json.objectmodel.JsonObjectOrArray;
import jsimple.json.objectmodel.ObjectModelParser;
import jsimple.util.ArrayList;
import jsimple.util.List;

import com.special_friends.model.*;
/**
 * Parses jsons received from facebook and constructs model objects
 */
public class FacebookParser {

	/**
	 * Create friends data object from the friend json received from facebook
	 */
	public List<FacebookFriend> parserFriendList(String json) {
		List<FacebookFriend> ret = new ArrayList<FacebookFriend>();
		ObjectModelParser parser = new ObjectModelParser(new StringReader(json));
		JsonObjectOrArray root = parser.parseRoot();
		JsonObject rootObj = (JsonObject) root;
		JsonArray friendList = rootObj.getJsonArray("data");

		for (int i = 0; i < friendList.size(); i++) {
			JsonObject strFriend = (JsonObject) friendList.get(i);
			String profilePic = strFriend.getJsonObject("picture").getJsonObject("data").getString("url");
			String name = strFriend.getString("name");
			String newName = replaceNonUTF(name);
			FacebookFriend f = new FacebookFriend(newName, profilePic);
			ret.add(f);
		}

		return ret;
	}

	/**
	 * From the raw json wall, extract the posts that are posted by the friends from the list given as parameter
	 */
	public String filterWall(String wallJson, List<String> acceptPostsFrom) {
		ObjectModelParser parser = new ObjectModelParser(new StringReader(wallJson));
		JsonObjectOrArray root = parser.parseRoot();
		JsonObject rootObj = (JsonObject) root;
		JsonArray feeds = rootObj.getJsonArray("data");

		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < feeds.size(); i++) {
			JsonObject post = (JsonObject) feeds.get(i);

			String posterName = post.getJsonObject("from").getString("name");
			String newPosterName = replaceNonUTF(posterName);
			if (acceptPostsFrom.contains(newPosterName)) {
				ret.append(post.toString());
				continue;
			}
		}
		return ret.toString();
	}

	/**
	 * Creates a list of facebook posts from the json of the user's wall
	 * Post's attributes are nullable , except for the poster's name
	 */
	public List<FacebookPost> parseWall(String wallJson) {
		List<FacebookPost> ret = new ArrayList<FacebookPost>();
		ObjectModelParser parser = new ObjectModelParser(new StringReader(wallJson));
		JsonObjectOrArray root = parser.parseRoot();
		JsonObject rootObj = (JsonObject) root;
		JsonArray feeds = rootObj.getJsonArray("data");

		for (int i = 0; i < feeds.size(); i++) {
			JsonObject post = (JsonObject) feeds.get(i);
			String from = post.getJsonObject("from").getString("name");
			String fromUTF = replaceNonUTF(from);

			String story = null;
			String picture = null;
			String link = null;
			String description = null;
			String name = null;
			String message = null;

			if (post.containsKey("story")) {
				story = post.getString("story");
			}
			if (post.containsKey("message")) {
				message = post.getString("message");
			}
			if (post.containsKey("picture")) {
				picture = post.getString("picture");
			}
			if (post.containsKey("link")) {
				picture = post.getString("link");
			}
			if (post.containsKey("name")) {
				name = post.getString("name");
			}
			if (post.containsKey("description")) {
				description = post.getString("description");
			}

			FacebookPost postPOJO = new FacebookPost(fromUTF, story, picture, link, name, description,message);
			ret.add(postPOJO);

		}
		return ret;
	}

	/**
	 * Parses current user  information(json) and extracts the user's unique facebook ID
	 */
	public String extractMyId(String myDataJson) {
		ObjectModelParser parser = new ObjectModelParser(new StringReader(myDataJson));
		JsonObjectOrArray root = parser.parseRoot();
		JsonObject rootObj = (JsonObject) root;
		String myID = rootObj.getString("id");
		return myID;
	}

	/**
	 * Parses current user  information(json) and extracts the user's name
	 */
	public String extractMyName(String myDataJson) {
		ObjectModelParser parser = new ObjectModelParser(new StringReader(myDataJson));
		JsonObjectOrArray root = parser.parseRoot();
		JsonObject rootObj = (JsonObject) root;
		String myFirstName = rootObj.getString("first_name");
		String myLastName = rootObj.getString("last_name");
		return myFirstName + " " + myLastName;
	}

	/**
	 * Replace character that can not be read (special characters , etc with _)
	 */
	public String replaceNonUTF(String original) {
		return original.replaceAll("[^A-Za-z0-9()\\[\\]]", "_");
	}
}