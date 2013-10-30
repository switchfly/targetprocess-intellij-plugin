package com.switchfly.targetprocess;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.switchfly.targetprocess.model.Assignable;
import com.switchfly.targetprocess.model.Comment;
import com.switchfly.targetprocess.model.User;
import org.apache.commons.lang.StringUtils;

public class TargetProcessParser {

    private final Type assignableType;
    private final Type commentType;
    private final Type userType;
    private final Gson gson;

    public TargetProcessParser() {
        super();
        assignableType = new TypeToken<GenericList<Assignable>>() {}.getType();
        commentType = new TypeToken<GenericList<Comment>>() {}.getType();
        userType = new TypeToken<GenericList<User>>() {}.getType();
        gson = buildGson();
    }

    private Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String stringDate = json.getAsString(); //  /Date(1337176800000-0500)
                String date = StringUtils.substringBetween(stringDate, "/Date(", "-"); // 1337176800000
                //TODO String timeZone = StringUtils.substringBetween(stringDate, "-", ")");  // 0500
                return new Date(Long.valueOf(date));
            }
        });
        return gsonBuilder.create();
    }

    public List<Assignable> parseAssignables(InputStream inputStream) {
        GenericList<Assignable> assignableList = gson.fromJson(new InputStreamReader(inputStream), assignableType);
        return assignableList.items;
    }

    public Assignable parseAssignable(InputStream inputStream) {
        GenericList<Assignable> assignableList = gson.fromJson(new InputStreamReader(inputStream), assignableType);
        List<Assignable> assignables = assignableList.items;
        return assignables.isEmpty() ? null : assignables.get(0);
    }

    public List<Comment> parseComments(InputStream inputStream) {
        GenericList<Comment> commentList = gson.fromJson(new InputStreamReader(inputStream), commentType);
        return commentList.items;
    }

    public User parseUser(InputStream inputStream) {
        GenericList<User> userList = gson.fromJson(new InputStreamReader(inputStream), userType);
        List<User> users = userList.items;
        return users.isEmpty() ? null : users.get(0);
    }

    private class GenericList<T> {
        @SerializedName("Items")
        private List<T> items;
    }
}

