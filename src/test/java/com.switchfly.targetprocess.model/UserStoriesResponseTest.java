package com.switchfly.targetprocess.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import static org.junit.Assert.assertNotNull;

public class UserStoriesResponseTest {

	//@Test
	public void testParseAsignables() throws Exception {
		//read file
		InputStream resourceAsStream = getClass().getResourceAsStream("/Assignables.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));

		//build Gson parser
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new JsonDeserializer() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				String stringDate = json.getAsJsonPrimitive().getAsString();
				stringDate = stringDate.replace("/Date(", "");//TODO replace this \\/Date\((-?\d+)\)\\/
				stringDate =
					stringDate.substring(0, stringDate.indexOf("-")); // use a dateparse function: http://md5.tpondemand.com/api/v1/UserStories/meta
				return new Date(Long.valueOf(stringDate));
			}
		});
		Gson gson = gb.create();

		//parse json
		Type type = new TypeToken<GenericListResponse<Assignable>>() {
		}.getType();
		GenericListResponse<Assignable> targetProcessTask = gson.fromJson(br, type);

		assertNotNull(targetProcessTask);
		assertNotNull(targetProcessTask.getNext());
	}
}
